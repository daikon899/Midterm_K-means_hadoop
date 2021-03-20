import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class Centroid extends Point implements WritableComparable <Centroid> {  //same thing as WritableComparable (Point implements writable)

    // TODO check if this field has to be IntWritable
    protected int id;

    public Centroid(int id, float x, float y, float z){
        super(x, y, z);
        this.id = id;
    }

    //TODO you don't need it. it is used to fix an error
    public Centroid(){
        super(0,0,0);
        id = 0;
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
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeInt(id);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
        this.id = in.readInt();
    }

    public int getId() {
        return id;
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
