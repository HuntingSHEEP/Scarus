package Components;

import Colliders.Collider;
import ScarMath.SMath;

public class Rigidbody extends Collider {
    public String opis = "RIGID";
    public PhysicMaterial physicMaterial;
    public Transform transform;
    public double invertedMass, invertedInertia;


    public Rigidbody(double invertedMass,double invertedInertia, PhysicMaterial physicMaterial){
        this.invertedMass   = invertedMass;
        this.invertedInertia= invertedInertia;
        this.physicMaterial = physicMaterial;
    }

    @Override
    public void awake() {
        this.transform  = gameObject.getComponent(Transform.class);
        this.mesh = gameObject.getComponent(MeshFilter.class).mesh;
        this.sphereRadius = mesh.getMaxVertexLen();
        this.resolveCollision = true;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
    }

    @Override
    public double getSphereRadius() {
        return sphereRadius;
    }

    public boolean isFixed() {
        return SMath.compare(invertedMass, 0, 0.000000001) && SMath.compare(invertedInertia, 0, 0.000000001);
    }
}
