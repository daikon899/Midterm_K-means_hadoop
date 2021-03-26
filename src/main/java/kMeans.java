import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class kMeans {

    //create centroids and pass them to Configuration
    public static void generateCentroids(Configuration c) throws IOException {
        String pathToCsv = "input/dataset.csv";
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        Gson gson = new Gson();

        ArrayList<String> points = new ArrayList<>();
        while ((row = csvReader.readLine()) != null) {
            points.add(row);
        }
        csvReader.close();

        Random rand = new Random();
        int k = Integer.parseInt(c.get("k"));
        for (int i = 0; i < k; i++) {
            int index = rand.nextInt(1000);
            String[] pointStr = points.get(index).split(",");
            float x = Float.parseFloat(pointStr[0]);
            float y = Float.parseFloat(pointStr[1]);
            float z = Float.parseFloat(pointStr[2]);
            int numPoints = 0;
            Centroid centroid = new Centroid(i, x, y, z);
            centroid.setNumberOfPoints(numPoints);
            String cSerialized = gson.toJson(centroid);
            c.set(Integer.toString(i), cSerialized);
        }

    }

    // parse centroids from output and pass them to Configuration
    public static void updateCentroids(Configuration c) throws IOException{
        String pathToCsv = "output/part-r-00000";
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        Gson gson = new Gson();

        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            int id = Integer.parseInt(data[0]);
            float x = Float.parseFloat(data[1]);
            float y = Float.parseFloat(data[2]);
            float z = Float.parseFloat(data[3]);
            int numPoints = Integer.parseInt(data[4]);
            Centroid centroid = new Centroid(id, x, y, z);
            centroid.setNumberOfPoints(numPoints);
            String cSerialized = gson.toJson(centroid);
            c.set(data[0], cSerialized);
        }
        csvReader.close();
    }


    public static void main(String[] args) throws Exception {

        int k = 5;
        Path inputDir = new Path("input");
        Path outputDir = new Path("output");

        Configuration conf = new Configuration();
        conf.setInt("k", k);
        generateCentroids(conf);


        int code;
        boolean clusterChanged;
        do {
            clusterChanged = false;
            FileSystem.get(conf).delete(outputDir, true);

            Job job = Job.getInstance(conf, "kMeans");
            job.setJarByClass(kMeans.class);
            // specify Mapper Reducer
            job.setMapperClass(Map.class);
            job.setCombinerClass(Combine.class);
            job.setReducerClass(Reduce.class);
            // specify output formats
            job.setMapOutputKeyClass(Centroid.class);
            job.setMapOutputValueClass(Point.class);
            job.setOutputKeyClass(NullWritable.class);
            job.setOutputValueClass(Centroid.class);
            // set input and output folders
            FileInputFormat.addInputPath(job, inputDir);
            FileOutputFormat.setOutputPath(job, outputDir);

            code = job.waitForCompletion(true) ? 0 : 1;

            //check if clusters are changed
            Counters counters = job.getCounters();
            Counter clCgd = counters.findCounter(Reduce.CHECK.CONVERGENCE);
            if (clCgd.getValue() != 0) {
                clusterChanged = true;
            }

            if(code == 0)
                updateCentroids(conf);

        } while(clusterChanged && code == 0);

        //job for output
        if(code == 0) {
            FileSystem.get(conf).delete(outputDir, true);

            Job job = Job.getInstance(conf, "assignCentroids");
            job.setJarByClass(kMeans.class);
            job.setMapperClass(Map.class);

            job.setMapOutputKeyClass(Centroid.class);
            job.setMapOutputValueClass(Point.class);

            FileInputFormat.addInputPath(job, inputDir);
            FileOutputFormat.setOutputPath(job, outputDir);

            code = job.waitForCompletion(true) ? 0 : 1;
        }
        System.exit(code);
    }
}