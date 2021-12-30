package Components;

public class PhysicMaterial {
    public final double staticFriction;
    public final double dynamicFriction;
    public final double bounciness;

    public PhysicMaterial(double staticFriction, double dynamicFriction, double bounciness){
        this.staticFriction     = staticFriction;
        this.dynamicFriction    = dynamicFriction;
        this.bounciness         = bounciness;
    }
}
