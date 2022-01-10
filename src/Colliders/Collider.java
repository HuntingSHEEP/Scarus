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
    public List<Collision> collisionList;
    public boolean resolveCollision = false;
    protected double sphereRadius;

    @Override
    public void awake(){
        setUpSphereRadius();
        this.collisionList = new ArrayList<>();
    }

    @Override
    public void update(double dt) {
        //collision = null;
    }

    public void clear(){
        this.collisionList = new ArrayList<>();
    }

    public double getSphereRadius(){
        return sphereRadius;
    }

    public List<Vector3D> getVertices() {
        Vector3D position = gameObject.getComponent(Transform.class).position;
        double   rotation = gameObject.getComponent(Transform.class).rotation;

        //SCALE THE MESH
        Vector3D scale = gameObject.getComponent(Transform.class).scale;
        List<Vector3D> scaledVertices = new ArrayList<>();

        for(Vector3D vertice : mesh.vertices)
            scaledVertices.add(Vector3D.multiply(vertice, scale));

        //ROTATE THE MESH
        List<Vector3D> rotatedVertices = new ArrayList<>();
        for(Vector3D vertice : scaledVertices)
            rotatedVertices.add(SMath.rotatePoint(vertice, new Vector3D(0,0, 1), rotation));

        //THEN PLACE THE MESH IN THE CORRECT POSITION
        for(Vector3D vertex : rotatedVertices)
            vertex.add(position);

        return rotatedVertices;
    }



    private void setUpSphereRadius() {
        Vector3D scale = gameObject.getComponent(Transform.class).scale;
        this.sphereRadius = mesh.getMaxVertexLen(scale);
    }
}
