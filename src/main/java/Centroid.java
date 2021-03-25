import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class Centroid extends Point implements WritableComparable <Centroid> {

    // TODO check if this field has to be IntWritable
    protected int id;

    public Centroid(int id, float x, float y, float z){
        super(x, y, z);
        this.id = id;
        numPoints = 1;
    }


    public Centroid(){
        super(0,0,0);
        id = 0;
        numPoints = 1;
    }


    @Override
    public int compareTo(Centroid o) {
        if (this.id == o.getId()){
            return 0;
        }
        return this.id < o.getId() ? 1 : -1;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        super.write(out);
        out.writeInt(id);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        super.readFields(in);
        this.id = in.readInt();
    }

    @Override
    public String toString() {
        String id = Integer.toString(this.id);
        String x = Float.toString(this.x);
        String y = Float.toString(this.y);
        String z = Float.toString(this.z);
        String numPoints = Integer.toString(this.numPoints);
        return id + "," + x + "," + y + "," + z + "," + numPoints;
    }

    public int getId() {
        return id;
    }

    public void incrementNumPoints(int nP) {
        numPoints += nP;
    }

    //returns true if x,y,z are different from actual
    public boolean setCoords(float x, float y, float z){
        if (this.x != x || this.y != y || this.z != z ){
            this.x = x;
            this.y = y;
            this.z = z;
            return true;
        }
        return false;
    }

}
