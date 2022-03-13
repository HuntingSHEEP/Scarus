package Asteroidy;

import Colliders.Rigidbody;
import Components.*;
import Components.Component;
import EngineCore.GameObject;
import Rendering.Scene;
import ScarMath.SMath;
import ScarMath.Vector3D;
import java.util.Random;


import java.awt.*;

public class RockManager extends Component {
    Scene scene;
    PlayerControl playerControl;
    public static int rockQuantity = 0;
    @Override
    public void awake() {
    }

    @Override
    public void update(double dt) {
    }


    public void createRock()
    {


        Vector3D centerOfScreen = new Vector3D(500, 400);
        double rotation = (new Random()).nextInt(62) / 10.0;
        Vector3D direction = SMath.rotatePoint(new Vector3D(1, 0), new Vector3D(0,0,1), rotation);
        direction.normalize();
        direction.multiply(200);

        Vector3D rockPosition = Vector3D.add(centerOfScreen, direction);



        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(rockPosition, 0, 5, false, false, new Vector3D(1,1,1)));
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
        scene.bufferGameObject(skala);
        rockQuantity++;
    }

    public void removeRock()
    {
        rockQuantity -= 1;

        if(rockQuantity < 3)
            createRock();
    }

}
