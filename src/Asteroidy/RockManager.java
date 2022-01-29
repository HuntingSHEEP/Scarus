package Asteroidy;

import Colliders.Rigidbody;
import Components.*;
import Components.Component;
import EngineCore.GameObject;
import Rendering.Scene;
import ScarMath.Vector3D;
import java.util.Random;


import java.awt.*;

public class RockManager extends Component {
    Scene scene;
    PlayerControl playerControl;
    int rockQuantity = 1;
    @Override
    public void awake() {

    }

    @Override
    public void update(double dt) {
        if(rockQuantity != 1)
            System.out.println("ilosc kamieni " + rockQuantity);
        if(rockQuantity == 0)
        {
            rockQuantity++;
            System.out.println("dodaje skale");
            GameObject skala;
            skala = createRock();
            scene.bufferGameObject(skala);
        }

    }
    public GameObject createRock()
    {
        Random rand = new Random();
        int x = rand.nextInt(1100);
        int y = rand.nextInt(900);
        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(new Vector3D(x, y), 0, 5, false, false, new Vector3D(1.5,1.5,1)));
        skala.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala.addComponent(new AngularDynamics(0.1, 0));
        skala.addComponent(new LinearDynamics(new Vector3D(-0.5,7), new Vector3D()));
        skala.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));
        Shooted shooted = new Shooted();
        shooted.setPlayerControl(playerControl);
        skala.addComponent(shooted);
        shooted.scene = scene;
        shooted.rockManager = this;
        return skala;
    }
    public void removeRock()
    {
        this.rockQuantity -= 1;
        System.out.println("ilosc " + rockQuantity);
    }

}
