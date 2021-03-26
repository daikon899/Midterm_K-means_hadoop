import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//calculate sum and update centroids with new coordinates
public class Reduce extends Reducer<Centroid, Point, NullWritable, Centroid> {

    public enum CHECK {
        CONVERGENCE
    }

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
}