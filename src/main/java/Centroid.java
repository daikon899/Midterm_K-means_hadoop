import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.Math;


public class Centroid implements WritableComparable <Centroid>{
    private float x,y,z;
    private boolean isChanged;
    private int id;

    float xTotal = 0, yTotal = 0, zTotal = 0;


    public Centroid(int id, float x, float y, float z){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public int compareTo(Centroid o) {
        return 0;         //TODO implement method
        //return -1;
        //return 1;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        //TODO implement method
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        //TODO implement method
    }

    public int getId() {
        return id;
    }

    public float getDistance(Point p){
        float distanceX = p.getX() - x;
        float distanceY = p.getY() - y;
        float distanceZ = p.getZ() - z;
        return (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2) + Math.pow(distanceZ, 2));
    }
}
