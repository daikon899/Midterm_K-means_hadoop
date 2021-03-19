import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//calculate sum and update centroids
public class Reduce extends Reducer<Centroid, Point, Point, IntWritable> {
    boolean clusterChanged = false; //TODO

    @Override
    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException{
        int clusterId = c.getId();
        float sumX = 0, sumY = 0, sumZ = 0;
        int numPoints = 0;

        Configuration conf = context.getConfiguration();

        for (Point p: points){
            if (p instanceof SumPoints){
                sumX += p.getX();
                sumY += p.getY();
                sumZ += p.getZ();
                numPoints += ((SumPoints) p).getNumPoints();
            }
            else {
                context.write(p, new IntWritable(clusterId));
            }
        }

        //calculate means and update centroids
        boolean changed = c.setCoords( sumX / numPoints, sumY / numPoints, sumZ / numPoints);
        if (changed){
            conf.setBoolean("clusterChanged", true);
        }

        //serialize new centroids and write them in Configuration
        Gson gson = new Gson();
        String cSerialized = gson.toJson(c);
        conf.set(Integer.toString(clusterId), cSerialized);
    }

    //execution at the end of the task
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        //maybe we don't need it
    }
}