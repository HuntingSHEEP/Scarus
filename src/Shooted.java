import Colliders.Collider;
import Colliders.Collision;
import Colliders.Rigidbody;
import Components.Component;
import EngineCore.GameObject;

public class Shooted extends Component {

    Collider collider;
    PlayerControl playerControl;

    @Override
    public void awake() {
        collider = gameObject.getComponent(Rigidbody.class);
    }

    @Override
    public void update(double dt) {
        isShooted();
    }

    public void setPlayerControl(PlayerControl playerControl){
        this.playerControl=playerControl;
    }

    public void isShooted(){
        for(Collision collision: collider.collisionList){
            if(collision.collided){
                GameObject parent = collision.B;
                GameObject object = collision.A;
                if(object == gameObject){
                    object = collision.B;
                    parent = collision.A;
                }

                if(object.name.equals("STATEK")){
                    zderzenieZeStatkiem(object, parent);
                }else if(object.name.equals("POCISK")){
                    zderzenieZPociskiem();
                }
            }
        }
    }

    public void zderzenieZeStatkiem(GameObject statek, GameObject skala){
        System.out.println("Odjeto punkty zycia");
        //skala = null;
        playerControl.hitByRock();
    }

    public void zderzenieZPociskiem(){
        System.out.println("Rock is shooted");
    }
}
