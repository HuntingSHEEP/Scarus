package ScarMath;

public class Vector3D {
    public Double x;
    public Double y;
    public Double z;

    public Vector3D(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Vector3D(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0.0;
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

    public Vector3D multiply(double scale){
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        //TODO tak nie może być - trzeba jasno oddzielić działanie na konkretnym obiekcie od zwykłego przeliczaniatłu
        return new Vector3D(this.x, this.y, this.z);
    }

    public void multiply(Vector3D v){
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
    }

    public double length(){
        return Math.sqrt(x*x + y*y + z*z);
    }

    public void normalize() {
        this.multiply(1.0/this.length());
    }

    public Vector3D copy() {
        return new Vector3D(x, y, z);
    }

    ///////////////////////////  STATIC OPERATIONS  ////////////////////////////////
    public static Vector3D add(Vector3D v1, Vector3D v2){
        return new Vector3D(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
    }
    public static Vector3D minus(Vector3D a, Vector3D b){
        return new Vector3D(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static Vector3D multiply(Vector3D v, double scale){
        return new Vector3D(v.x*scale, v.y*scale, v.z*scale);
    }
    public static Vector3D multiply(Vector3D v, Vector3D scaleVector){
        return new Vector3D(v.x*scaleVector.x, v.y*scaleVector.y, v.z*scaleVector.z);
    }
    public static double dot(Vector3D v1, Vector3D v2){
        return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
    }

    public static double pointsDistance(Vector3D A, Vector3D B){
        Vector3D BA = Vector3D.difference(A, B);
        return Vector3D.length(BA);
    }

    private static Vector3D difference(Vector3D a, Vector3D b) {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    private static double length(Vector3D a) {
        return Math.sqrt(a.x*a.x + a.y*a.y + a.z*a.z);
    }

    public static boolean equall(Vector3D a, Vector3D b) {
        double epsilon = 0.001;

        boolean test0 = Math.abs(a.x - b.x) < epsilon;
        boolean test1 = Math.abs(a.y - b.y) < epsilon;
        boolean test2 = Math.abs(a.z - b.z) < epsilon;

        return test0 && test1 && test2;
    }
}
