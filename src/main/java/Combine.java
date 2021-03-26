import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.logging.Logger;

//calculate partial sum for each cluster
public class Combine extends Reducer<Centroid, Point, Centroid, Point> {

    @Override
    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException {

        float sumX = 0, sumY = 0, sumZ = 0;
        int numPoints = 0;

        for (Point p : points) {
            sumX += p.getX();
            sumY += p.getY();
            sumZ += p.getZ();
            numPoints += p.getNumberOfPoints();
        }

        Point sumPoints = new Point(sumX, sumY, sumZ);
        sumPoints.setNumberOfPoints(numPoints);

        context.write(c, sumPoints);

    }

}
