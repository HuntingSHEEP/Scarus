import Colliders.MeshCollider;
import Colliders.Rigidbody;
import Components.*;
import EngineCore.*;
import Rendering.RenderEngine;
import Rendering.Scene;
import ScarMath.Vector3D;
import com.sun.jdi.ThreadReference;

import java.awt.*;

public class Test {
    public static void main(String[] args){
        GameObject statek = new GameObject("STATEK");
        statek.addComponent(new Transform(new Vector3D(750,200), 0,5, false, false, new Vector3D(1,1.2,1)));
        statek.addComponent(new MeshFilter(new Mesh(MeshManager.SHIP)));
        statek.addComponent(new MeshRenderer(Color.CYAN, 1f));
        statek.addComponent(new AngularDynamics(0, 0));
        statek.addComponent(new LinearDynamics(new Vector3D(), new Vector3D()));
        statek.addComponent(new Rigidbody(0.1, 0.0001,new PhysicMaterial(0.6,0.5,0.7)));
        statek.addComponent(new PlayerControl());

        GameObject sciana = new GameObject("SCIANA");
        sciana.addComponent(new Transform(new Vector3D(200,200), 0, 5, false, false, new Vector3D(2,0.5,1)));
        sciana.addComponent(new MeshFilter(Mesh.QUAD));
        sciana.addComponent(new MeshRenderer(Color.RED, 1f));
        sciana.addComponent(new AngularDynamics(0, 0));
        sciana.addComponent(new LinearDynamics(new Vector3D(7,0), new Vector3D()));
        sciana.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));

        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(new Vector3D(600,200), 0, 5, false, false, new Vector3D(1,1,1)));
        skala.addComponent(new MeshFilter(Mesh.QUAD));
        skala.addComponent(new MeshRenderer(Color.RED, 1f));
        skala.addComponent(new AngularDynamics(0, 0));
        skala.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        skala.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));

        Scene scene = new Scene();
        scene.addGameObject(statek);
        scene.addGameObject(sciana);
        scene.addGameObject(skala);

        ScarusEngine scarusEngine = new ScarusEngine(scene);
        RenderEngine renderEngine = new RenderEngine(scene);

        scarusEngine.start();
        renderEngine.start();
    }
}
