import EngineCore.Component;
import EngineCore.Transform;
import InputInterface.Input;
import InputInterface.KeyCode;
import ScarMath.Vector3D;

public class PlayerControl extends Component {

    @Override
    public void update(double dt) {
        if(     Input.getKeyDown(KeyCode.W) ||
                Input.getKeyDown(KeyCode.S) ||
                Input.getKeyDown(KeyCode.A) ||
                Input.getKeyDown(KeyCode.D) ||
                Input.getKeyDown(KeyCode.ARROW_DOWN)    ||
                Input.getKeyDown(KeyCode.ARROW_UP)      ||
                Input.getKeyDown(KeyCode.ARROW_RIGHT)   ||
                Input.getKeyDown(KeyCode.ARROW_LEFT)
        ){
            double horizontal   = Input.getAxis(KeyCode.AXIS_HORIZONTAL);
            double vertical     = Input.getAxis(KeyCode.AXIS_VERTICAL);
            double scale        = 5;
            gameObject.getComponent(Transform.class).position.add(new Vector3D(horizontal*scale, -vertical*scale));
            //System.out.println("MOVE TO RIGHT! "+gameObject.getComponent(Transform.class).position);
        }
        //System.out.println("POCHODNIA POSITION: " + gameObject.getComponent(Transform.class).position);



    }
}
