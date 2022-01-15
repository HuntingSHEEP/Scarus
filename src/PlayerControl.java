import Colliders.Collision;
import Components.AngularDynamics;
import Components.Component;
import Components.LinearDynamics;
import Colliders.Rigidbody;
import EngineCore.GameObject;
import InputInterface.Input;
import InputInterface.KeyCode;
import ScarMath.Vector3D;

public class PlayerControl extends Component {
    double angularVelocityValue;
    AngularDynamics angularDynamics;

    @Override
    public void awake() {
        angularVelocityValue    = 0.5f;
        angularDynamics = gameObject.getComponent(AngularDynamics.class);
    }

    @Override
    public void update(double dt) {
        manageRotation();
    }
/*
    private void collision() {
        for(Collision collision : rigidBody.collisionList)
            if (collision != null)
                if (collision.collided) {
                    GameObject obiektKolizji = collision.A;
                    if (obiektKolizji == gameObject)
                        obiektKolizji = collision.B;

                    System.out.println("Detected collision with object [" + obiektKolizji.name +"]");
                }
    }

 */

    private void manageRotation() {
        if(Input.getKeyDown(KeyCode.Q))
            angularDynamics.angularVelocity -= angularVelocityValue;
        if(Input.getKeyReleased(KeyCode.Q))
            angularDynamics.angularVelocity += angularVelocityValue;

        if(Input.getKeyDown(KeyCode.E))
            angularDynamics.angularVelocity += angularVelocityValue;
        if(Input.getKeyReleased(KeyCode.E))
            angularDynamics.angularVelocity -= angularVelocityValue;
    }

}
