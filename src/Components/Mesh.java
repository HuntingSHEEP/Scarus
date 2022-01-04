package Components;

import ScarMath.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Mesh{
    public List<Vector3D> vertices;

    public Mesh(){
        this.vertices = new ArrayList<Vector3D>();
    }

    public Mesh(List<Vector3D> vertices){
        this.vertices = vertices;
    }


    public double getMaxVertexLen(Vector3D scale) {
        //SCALE THE MESH
        List<Vector3D> scaledVertices = new ArrayList<>();
        for(Vector3D vertice : vertices)
            scaledVertices.add(Vector3D.multiply(vertice, scale));

        double distance = 0;

        for(Vector3D vertex : scaledVertices){
            double dist = vertex.length();
            if(distance < dist)
                distance = dist;
        }

        return distance;
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private static List<Vector3D> vQUAD = new ArrayList<Vector3D>(List.of(new Vector3D[]{
            new Vector3D(-50,-50),
            new Vector3D(50,-50),
            new Vector3D(50,50),
            new Vector3D(-50,50)}));

    public static Mesh QUAD = new Mesh(vQUAD);
}
