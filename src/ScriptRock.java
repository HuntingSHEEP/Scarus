import Asteroidy.MeshManager;
import Colliders.Rigidbody;
import Components.*;
import Components.Component;
import EngineCore.GameObject;
import Rendering.Scene;
import ScarMath.Vector3D;

import java.awt.*;

public class ScriptRock extends Component {
    public Scene scene;
    long time0;

    @Override
    public void awake() {
        System.out.println("Hallo World, tu się zgłasza ROCK_SCRIPT!");
        time0 = System.nanoTime()/100000000;
    }

    @Override
    public void update(double dt) {
        long check = System.nanoTime()/100000000;

        if(check - time0 > 50){
            time0 = check;
            System.out.println("time");
            createRock();
        }

    }

    private void createRock() {
        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(new Vector3D(200,-50), 0.3, 5, false, false, new Vector3D(1,1,1)));
        skala.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala.addComponent(new AngularDynamics(0, 0));
        skala.addComponent(new LinearDynamics(new Vector3D(0,100), new Vector3D(0,4)));
        skala.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.3,0.3,0.4)));

        scene.bufferGameObject(skala);
    }
}
