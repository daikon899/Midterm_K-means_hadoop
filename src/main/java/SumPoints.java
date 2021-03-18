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


}
