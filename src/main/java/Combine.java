import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.logging.Logger;

//write partial sum for each cluster
public class Combine extends Reducer<Centroid, Point, Centroid, Point> {
    private final Logger logger = Logger.getLogger("loggerCombiner");


    @Override
    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException {
        logger.info("Combiner started");

        Point sumPoints = new Point();
        sumPoints.setNumberOfPoints(0);

        for (Point p : points) {
            sumPoints.setX(sumPoints.getX() + p.getX());
            sumPoints.setY(sumPoints.getY() + p.getY());
            sumPoints.setZ(sumPoints.getZ() + p.getZ());
            sumPoints.incrementNumPoints(p.getNumberOfPoints());
        }

        context.write(c, sumPoints);

    }

}
