package EngineCore;

import ScarMath.Vector3D;

public class LinearDynamics extends Component{
    public Vector3D velocity;
    public Vector3D acceleration;

    public LinearDynamics(){
        this.velocity       = new Vector3D();
        this.acceleration   = new Vector3D();
    }

    public LinearDynamics(Vector3D velocity, Vector3D acceleration){
        this.velocity       = velocity;
        this.acceleration   = acceleration;
    }

    @Override
    public void awake() {

    }

    @Override
    public void update(double dt) {

    }
}
