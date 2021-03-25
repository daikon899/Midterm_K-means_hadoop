import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Point implements Writable {
    // TODO check if thees fields have to be FloatWritable
    protected float x, y, z;
    protected int numPoints;

    Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        numPoints = 1;
    }

    Point() {
        numPoints = 1;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeInt(numPoints);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.z = in.readFloat();
        this.numPoints = in.readInt();
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

    public int getNumberOfPoints() {
        return numPoints;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setNumberOfPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    public void incrementNumPoints(int add) {
        numPoints += add;
    }

    @Override
    public String toString() {
        String x = Float.toString(this.x);
        String y = Float.toString(this.y);
        String z = Float.toString(this.z);
        return x + "," + y + "," + z;
    }

}
