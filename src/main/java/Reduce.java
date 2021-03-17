import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//calculate sum and update centroids
public class Reduce extends Reducer<Centroid, Point, Point, IntWritable> {

    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException{


        // TODO missing something

        for (Point p: points){
            int clusterId = c.getId();
            context.write(p, new IntWritable(clusterId));
        }
    }

    //execution at the end of the task
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        //maybe we need it
    }
}