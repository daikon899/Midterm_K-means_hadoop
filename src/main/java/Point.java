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

    //TODO check if all write and readFields methods are correct
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.z = in.readFloat();
    }

    public float getDistance(Point p){
        float distanceX = p.getX() - x;
        float distanceY = p.getY() - y;
        float distanceZ = p.getZ() - z;
        return (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2) + Math.pow(distanceZ, 2));
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
