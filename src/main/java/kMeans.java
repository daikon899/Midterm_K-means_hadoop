import com.google.gson.Gson;
import org.apache.commons.math.stat.clustering.Cluster;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.ArrayList;

public class kMeans {
    // args[0] -> input dir, args[1] -> output dir
    public static void main(String[] args) throws Exception {

        int k = 5;

        Path inputDir = new Path(args[0]);
        Path outputDirIntermediate = new Path(args[1] + "_int"); // where to save intermediate iterations
        Path outputDir = new Path(args[1]);

        //pass parameters to Configuration
        Configuration conf = new Configuration();
        conf.setInt("k", k);
        conf.setBoolean("clusterChanged", false);

        //create centroids and pass them to Configuration
        Gson gson = new Gson();
        for (int i = 0; i < k; i++) {
            Centroid c = new Centroid(i,0, 0, 0); // TODO choose better centroids
            String cSerialized = gson.toJson(c);   //serialize
            conf.set(Integer.toString(i), cSerialized);
        }                        // ...can also be used java serializer


        // create a job until no changed are detected
        int code;
        do {
            Job job = Job.getInstance(conf, "kMeans");
            job.setJarByClass(kMeans.class);
            // specify Mapper Reducer
            job.setMapperClass(Map.class);
            job.setCombinerClass(Combine.class);
            job.setReducerClass(Reduce.class);
            // specify output format
            job.setOutputKeyClass(Point.class);
            job.setOutputValueClass(IntWritable.class);
            //set input format
            job.setInputFormatClass(FileInputFormat.class); //is it correct? We have a csv...
            // I don't know
            FileInputFormat.addInputPath(job, inputDir);
            FileOutputFormat.setOutputPath(job, outputDirIntermediate);

            code = job.waitForCompletion(true) ? 0 : 1;

        } while(Boolean.parseBoolean(conf.get("clusterChanged")) && code == 0);

        //TODO write results ?
        //clean up intermediate output
        FileSystem.get(conf).delete(outputDirIntermediate, true);

        System.exit(code);

    }
}