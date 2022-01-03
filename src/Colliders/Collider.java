package Colliders;

import Components.Component;
import Components.Mesh;
import Components.Transform;
import ScarMath.SMath;
import ScarMath.Vector3D;

import java.util.ArrayList;
import java.util.List;

public abstract class Collider extends Component {
    public Mesh mesh;
    public Collision collision;
    public boolean resolveCollision = false;
    protected double sphereRadius;

    public abstract double getSphereRadius();

    public List<Vector3D> getVertices() {
        Vector3D position = gameObject.getComponent(Transform.class).position;
        double   rotation = gameObject.getComponent(Transform.class).rotation;

        //FIRST ROTATE THE MESH
        List<Vector3D> rotatedVertices = new ArrayList<>();
        for(Vector3D vertice : mesh.vertices)
            rotatedVertices.add(SMath.rotatePoint(vertice, new Vector3D(0,0, 1), rotation));

        //THEN PLACE THE MESH IN THE CORRECT POSITION
        for(Vector3D vertex : rotatedVertices)
            vertex.add(position);

        return rotatedVertices;
    }

    @Override
    public void update(double dt) {

        collision = null;
    }

}
