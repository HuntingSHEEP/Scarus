package Asteroidy;

import Colliders.Rigidbody;
import Components.Component;
import Colliders.Collision;
import Colliders.Collider;
import Components.LinearDynamics;
import Components.Transform;
import EngineCore.GameObject;
import ScarMath.Vector3D;
import java.util.Random;


public class TeleportRock extends Component{

    Collider collider;
    //LinearDynamics linearDynamics;

    @Override
    public void awake() {
        collider = gameObject.getComponent(Rigidbody.class);
        //linearDynamics = gameObject.getComponent(LinearDynamics.class);
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
                //System.out.println("Sciana: Detected collision with object [" + obiektKolizji.name +"]");
                if(obiektKolizji.name.equals("SKALA")){ //JESLI ZDERZENIE JEST ZE SKALA TO TELEPORTUJEMY SKALE
                    Transform transform = obiektKolizji.getComponent(Transform.class); //obiekt transform przechowuje dane o pozycji obiektu, więc go pobieramy
                    //transform.position = new Vector3D(100, 100);
                    //1100, 900
                    //Vector3D srodek = new Vector3D(550, 450);

                    Random gen = new Random();
                    int x, y;

                    do{
                        x = gen.nextInt(1000);
                        y = gen.nextInt(800);
                    }while(x < 100 || y < 100);
                    transform.position = new Vector3D(x, y);

                    LinearDynamics linearDynamics = obiektKolizji.getComponent(LinearDynamics.class);
                    //linearDynamics.velocity;

                    x = gen.nextInt(700);
                    y = gen.nextInt(600);

                    if(x<400) x=400;
                    if(y<300) y=300;


                    Vector3D vector3D = new Vector3D(x, y);

//                    vector3D.normalize();
//                    vector3D.multiply(25);
//                    linearDynamics.velocity = vector3D;

                    Vector3D srodek = Vector3D.minus(vector3D, transform.position);
                    srodek.normalize();
                    linearDynamics.velocity = srodek.multiply(20);
                }
            }
        }
    }
}