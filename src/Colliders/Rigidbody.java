package Colliders;

import Colliders.Collider;
import Components.MeshFilter;
import Components.PhysicMaterial;
import Components.Transform;
import ScarMath.SMath;

public class Rigidbody extends Collider {
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
        this.resolveCollision = true;
        super.awake();
    }

    @Override
    public void update(double dt) {
        super.update(dt);
    }

    //@Override
    //public double getSphereRadius() {
    //    return sphereRadius;
    //}

    public boolean positionFixed(){
        return SMath.compare(invertedMass, 0, 0.000000001);
    }

    public boolean rotationFixed(){
        return SMath.compare(invertedInertia, 0, 0.000000001);
    }
}
