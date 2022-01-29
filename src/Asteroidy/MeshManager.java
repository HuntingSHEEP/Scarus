package Asteroidy;

import ScarMath.Vector3D;
import java.util.ArrayList;
import java.util.List;

public class MeshManager {
    public static List<Vector3D> SHIP = new ArrayList<Vector3D>(List.of(new Vector3D[]{
            new Vector3D(-20,15),
            new Vector3D(0,-25),
            new Vector3D(20,15),
            new Vector3D(0, 20)}));

    public static List<Vector3D> SKALA_1 = new ArrayList<Vector3D>(List.of(new Vector3D[]{
            new Vector3D(0,20),
            new Vector3D(-20,10),
            new Vector3D(-20, -20),
            new Vector3D(10, -30),
            new Vector3D(30, -10),
            new Vector3D(10, 20)}));


}
