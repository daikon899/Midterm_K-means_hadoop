import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//write partial sum for each cluster
public class Combine extends Reducer<Centroid, Point, Centroid, Point> {

    public void reduce(Centroid c, Iterable<Point> points, Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();

        SumPoints sum = new SumPoints();

        for (Point p : points) {
            sum.sumCoords(p.getX(), p.getY(), p.getZ());
            context.write(c, p);
        }

        context.write(c, sum);

    }

}
