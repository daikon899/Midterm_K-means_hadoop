import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.Random;

public class kMeans {

    public static void main(String[] args) throws Exception {

        int k = 5;
        Path inputDir = new Path("input");
        Path outputDir = new Path("output");


        //pass k and centroids to Configuration
        Configuration conf = new Configuration();
        conf.setInt("k", k);
        //create centroids and pass them to Configuration
        Gson gson = new Gson();
        for (int i = 0; i < k; i++) {
            Centroid c = new Centroid(i, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
            String cSerialized = gson.toJson(c);   //serialize
            conf.set(Integer.toString(i), cSerialized);
        }                        // ...can also be used java serializer


        // create a job until no changes are detected

        int code;
        do {
            conf.setBoolean("clusterChanged", false);
            FileSystem.get(conf).delete(outputDir, true);

            Job job = Job.getInstance(conf, "kMeans");
            job.setJarByClass(kMeans.class);
            // specify Mapper Reducer
            job.setMapperClass(Map.class);
            //job.setCombinerClass(Combine.class); // TODO remove comment when and if a better version of the combiner is implemented
            job.setReducerClass(Reduce.class);
            // specify output formats
            job.setMapOutputKeyClass(Centroid.class);
            job.setMapOutputValueClass(Point.class);
            job.setOutputKeyClass(Point.class);
            job.setOutputValueClass(IntWritable.class);
            // set input and output folders
            FileInputFormat.addInputPath(job, inputDir);
            FileOutputFormat.setOutputPath(job, outputDir);
            // wait for job completion
            System.out.println("Starting the job...");

            code = job.waitForCompletion(true) ? 0 : 1;

            System.out.println("Job ended with code " + code + " and clusterChanged is " + Boolean.parseBoolean(conf.get("clusterChanged")));

        } while(Boolean.parseBoolean(conf.get("clusterChanged")) && code == 0);


        System.exit(code);




    }

}