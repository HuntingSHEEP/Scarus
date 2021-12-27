import EngineCore.Component;
import InputInterface.Input;
import InputInterface.Key;

public class PlayerControl extends Component {


    @Override
    public void update(double dt) {
        if(Input.getKeyPressed(Key.W)){
           // System.out.println("PRESSING:W");
        }

        if(Input.getKeyReleased(Key.W)){
            System.out.println("RELEASE: W");
        }

        if(Input.getKeyDown(Key.W)){
            System.out.println("DAŁN:> W");
        }




        if(Input.getKeyPressed(Key.Q)){
            //System.out.println("PRESSING:Q");
        }

        if(Input.getKeyReleased(Key.Q)){
            System.out.println("RELEASE: Q");
        }

        if(Input.getKeyDown(Key.Q)){
            System.out.println("DAŁN:> Q");
        }
    }
}
