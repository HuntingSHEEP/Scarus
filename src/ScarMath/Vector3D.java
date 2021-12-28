package ScarMath;

public class Vector3D {
    public double x;
    public double y;
    double z;

    public Vector3D(){
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    public Vector3D(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0f;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double get(int index){
        if(index == 0)
            return x;
        if(index == 1)
            return y;
        if(index == 2)
            return z;

        return -1;
    }

    public String toString(){
        return "V3D: ["+x+", "+y+", "+z+"]";
    }

    public void add(Vector3D vector3D) {
        this.x += vector3D.x;
        this.y += vector3D.y;
        this.z += vector3D.z;
    }

    ///////////////////////////  STATIC OPERATIONS  ////////////////////////////////

    public static Vector3D add(Vector3D v1, Vector3D v2){
        return new Vector3D(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
    }
}
