import Asteroidy.MeshManager;
import Colliders.Rigidbody;
import Components.*;
import Components.Component;
import EngineCore.GameObject;
import Rendering.Scene;
import ScarMath.Vector3D;

import java.awt.*;
import java.util.Random;

public class ScriptRock extends Component {
    public Scene scene;
    long time0;
    long time1;


    @Override
    public void awake() {
        System.out.println("Hallo World, tu się zgłasza ROCK_SCRIPT!");
        time0 = System.nanoTime()/100000000;
        time1 = time0;
    }

    @Override
    public void update(double dt) {
        long check = System.nanoTime()/100000000;

        if(check - time0 > 40){
            time0 = check;
            createRock(200);
        }

        if(check - time1 > 55){
            time1 = check;
            createRock(1000);
        }

    }

    private void createRock(int X) {
        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(new Vector3D(X,-50), 0.3, 5, false, false, new Vector3D(1,1,1)));
        skala.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala.addComponent(new AngularDynamics(0, 0));
        skala.addComponent(new LinearDynamics(new Vector3D(0,80), new Vector3D(0,4)));
        skala.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.3,0.3,Math.random() * 0.4f + 0.3)));

        scene.bufferGameObject(skala);
    }
}
