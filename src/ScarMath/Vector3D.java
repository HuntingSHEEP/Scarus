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
}
