import EngineCore.Component;
import EngineCore.GameObject;
import EngineCore.LinearDynamics;
import EngineCore.Transform;
import InputInterface.Input;
import InputInterface.KeyCode;
import ScarMath.Vector3D;

public class PlayerControl extends Component {
    int accValue;
    Vector3D objectAcceleration;

    @Override
    public void awake() {
        accValue = 3;
        objectAcceleration = gameObject.getComponent(LinearDynamics.class).acceleration;
    }

    @Override
    public void update(double dt) {
        manageWSAD();
    }

    private void manageWSAD() {
        if(Input.getKeyDown(KeyCode.W))
            objectAcceleration.add(new Vector3D(0, -accValue));
        if(Input.getKeyReleased(KeyCode.W))
            objectAcceleration.add(new Vector3D(0, accValue));

        if(Input.getKeyDown(KeyCode.S))
            objectAcceleration.add(new Vector3D(0, accValue));
        if(Input.getKeyReleased(KeyCode.S))
            objectAcceleration.add(new Vector3D(0, -accValue));

        if(Input.getKeyDown(KeyCode.A))
            objectAcceleration.add(new Vector3D(-accValue, 0));
        if(Input.getKeyReleased(KeyCode.A))
            objectAcceleration.add(new Vector3D(accValue, 0));

        if(Input.getKeyDown(KeyCode.D))
            objectAcceleration.add(new Vector3D(accValue, 0));
        if(Input.getKeyReleased(KeyCode.D))
            objectAcceleration.add(new Vector3D(-accValue, 0));
    }
}
