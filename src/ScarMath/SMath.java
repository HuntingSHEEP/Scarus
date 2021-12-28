package ScarMath;

public class SMath {
    public static final double pi = 3.14159265359;

    public static Vector3D rotatePoint(Vector3D point, Vector3D rotationAxis, double FI){
        Vector3D pointDelta  = Matrix.multiply(Matrix.rotationMatrix(rotationAxis, FI), point);
        Vector3D pointUpdate = Vector3D.add(point, pointDelta);;

        return  pointUpdate;
    }

}
