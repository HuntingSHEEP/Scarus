package Asteroidy;

import Asteroidy.PlayerControl;
import Colliders.Collider;
import Colliders.Collision;
import Colliders.Rigidbody;
import Components.Component;
import EngineCore.GameObject;
import Rendering.Scene;

public class Shooted extends Component {

    Collider collider;
    PlayerControl playerControl;
    Scene scene;
    RockManager rockManager;
    public static int zbiteSkaly = 0;

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

                if(object.name.equals("POCISK")){
                    zderzenieZPociskiem();
                }
            }
        }
    }

    public void zderzenieZPociskiem(){
        //System.out.println("Rock is shooted");
        zbiteSkaly++;
        scene.bufferRemovedGameObject(gameObject);
        rockManager.removeRock();

    }
}
