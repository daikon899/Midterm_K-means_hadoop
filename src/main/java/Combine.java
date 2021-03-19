import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.logging.Logger;

//write partial sum for each cluster
//TODO try to implement a better version or remove it
public class Combine extends Reducer<Centroid, Point, Centroid, Point> {
    private final Logger logger = Logger.getLogger("loggerCombiner");


    @Override
    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException {
        logger.info("Combiner started");

        SumPoints sum = new SumPoints();

        for (Point p : points) {
            sum.sumCoords(p.getX(), p.getY(), p.getZ());
            context.write(c, p);
        }

        context.write(c, sum);

    }

}
