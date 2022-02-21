import Asteroidy.MeshManager;
import Colliders.Rigidbody;
import Components.*;
import EngineCore.GameObject;
import EngineCore.ScarusEngine;
import Rendering.RenderEngine;
import Rendering.Scene;
import ScarMath.Vector3D;

import java.awt.*;

public class Test {
    public static void main(String[] args){

        GameObject strop = new GameObject("SCIANA");
        strop.addComponent(new Transform(new Vector3D(550,700), 0, 5, true, true, new Vector3D(11,0.5,1)));
        strop.addComponent(new MeshFilter(Mesh.QUAD));
        strop.addComponent(new MeshRenderer(new Color(49, 219, 239,200), 1f));
        strop.addComponent(new AngularDynamics(0, 0));
        strop.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        strop.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.3,0.3,0.3)));

        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(new Vector3D(600,300), 0.1, 5, false, false, new Vector3D(1.5,1.5,1)));
        skala.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala.addComponent(new AngularDynamics(0.1, 0));
        skala.addComponent(new LinearDynamics(new Vector3D(), new Vector3D(0,3)));
        skala.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0,0,0.3)));

        Scene scene = new Scene();
        scene.addGameObject(strop);
        scene.addGameObject(skala);

        ScarusEngine scarusEngine = new ScarusEngine(scene);
        RenderEngine renderEngine = new RenderEngine(scene);

        scarusEngine.start();
        renderEngine.start();
    }
}
