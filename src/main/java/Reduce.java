import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.logging.Logger;

//calculate sum and update centroids
public class Reduce extends Reducer<Centroid, Point, NullWritable, Centroid> {
    private final Logger logger = Logger.getLogger("loggerReducer");

    public static enum CHECK {
        CONVERGENCE
    };

    @Override
    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException{

        float sumX = 0, sumY = 0, sumZ = 0;
        int numPoints = 0;

        for (Point p : points) {
            sumX += p.getX();
            sumY += p.getY();
            sumZ += p.getZ();
            numPoints += p.getNumberOfPoints();
        }

        boolean changed = c.setCoords( sumX / numPoints, sumY / numPoints, sumZ / numPoints);

        if (changed)
            context.getCounter(CHECK.CONVERGENCE).increment(1);

        context.write(NullWritable.get(), c);
    }

    //execution at the end of the task
    @Override
    protected void cleanup(Context context) {
        //maybe we don't need it
    }
}