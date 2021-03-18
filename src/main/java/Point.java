import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Point implements Writable {
    protected float x, y, z;


    Point(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        //TODO implement method
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        //TODO implement method
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }


}
