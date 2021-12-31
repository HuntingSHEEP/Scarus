package Components;

import Colliders.Collider;

public class Rigidbody extends Collider {
    public String opis = "RIGID";
    public PhysicMaterial physicMaterial;
    private Transform   transform;


    public Rigidbody(PhysicMaterial physicMaterial){
        this.physicMaterial = physicMaterial;
    }

    @Override
    public void awake() {
        this.transform  = gameObject.getComponent(Transform.class);
        this.mesh = gameObject.getComponent(MeshFilter.class).mesh;
        this.sphereRadius = mesh.getMaxVertexLen();
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public double getSphereRadius() {
        return sphereRadius;
    }
}
