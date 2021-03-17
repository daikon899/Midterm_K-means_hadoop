import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//write partial sum for each cluster
public class Combine extends Reducer<Centroid, Point, Centroid, Point> {

    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException{

        for (Point p: points){
            int clusterId = c.getId();
            context.write(p, new IntWritable(clusterId));
        }
        //TODO implement

        // context.write(something);
    }

}
