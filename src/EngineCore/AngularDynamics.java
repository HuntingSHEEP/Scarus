package EngineCore;

import ScarMath.Vector3D;

public class AngularDynamics extends Component {
    public double angularVelocity;
    public double angularAcceleration;

    public AngularDynamics(){
        this.angularVelocity        = 0f;
        this.angularAcceleration    = 0f;
    }

    public AngularDynamics(double angularVelocity, double angularAcceleration){
        this.angularVelocity        = angularVelocity;
        this.angularAcceleration    = angularAcceleration;
    }

    @Override
    public void awake() {

    }

    @Override
    public void update(double dt) {

    }
}
