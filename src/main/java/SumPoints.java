import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SumPoints extends Point{

    private int numPoints = 0;

    SumPoints(){
        super(0,0,0);
    }

    public void sumCoords(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        numPoints++;
    }

    public int getNumPoints() {
        return numPoints;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        super.write(out);
        out.writeInt(numPoints);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        super.readFields(in);
        this.numPoints = in.readInt();
    }

}
