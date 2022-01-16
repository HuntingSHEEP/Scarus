import Colliders.Rigidbody;
import Components.Component;
import Colliders.Collision;
import Colliders.Collider;
import Components.Transform;
import EngineCore.GameObject;
import ScarMath.Vector3D;


public class TeleportRock extends Component{

    Collider collider;

    @Override
    public void awake() {
        collider = gameObject.getComponent(Rigidbody.class);
    }

    @Override
    public void update(double dt) {
        collision();
    }

    public void collision(){
        for(Collision collission: collider.collisionList){
            if(collission.collided){
                GameObject obiektKolizji = collission.A; //POBIERA PIERWSZY KOMPONENT
                if(obiektKolizji == gameObject){ //JEŚLI POBRANY OBIEKT KOLIZJI JEST RODZICEM NASZEGO SKRYPTU TO ZAMIENIAMY NA TEN DRUGI -> SKAŁA/STATEK
                    obiektKolizji = collission.B;
                }
                //ściana
                System.out.println("Sciana: Detected collision with object [" + obiektKolizji.name +"]");
                if(obiektKolizji.name.equals("SKALA")){ //JESLI ZDERZENIE JEST ZE SKALA TO TELEPORTUJEMY SKALE
                    Transform transform = obiektKolizji.getComponent(Transform.class); //obiekt transform przechowuje dane o pozycji obiektu, więc go pobieramy
                    //transform.position = new Vector3D(100, 100); //

                }
            }
        }
    }
}
