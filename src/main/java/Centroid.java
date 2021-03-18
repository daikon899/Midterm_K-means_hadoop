import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class Centroid extends Point implements Comparable<Centroid> {  //same thing as WritableComparable (Point implements writable)

    private int id;

    public Centroid(int id, float x, float y, float z){
        super(x, y, z);
        this.id = id;
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
        out.write(id);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        super.readFields(in);
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
