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
        GameObject manager = new GameObject("MANAGER");
        ScriptRock scriptRock = new ScriptRock();
        manager.addComponent(scriptRock);

        GameObject strop = new GameObject("STROP");
        strop.addComponent(new Transform(new Vector3D(600,900), 0, 5, true, true, new Vector3D(11,0.5,1)));
        strop.addComponent(new MeshFilter(Mesh.QUAD));
        strop.addComponent(new MeshRenderer(new Color(49, 219, 239,200), 1f));
        strop.addComponent(new AngularDynamics(0, 0));
        strop.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        strop.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.3,0.3,0.1)));


        GameObject sciana0 = new GameObject("SCIANA0");
        sciana0.addComponent(new Transform(new Vector3D(200,200), 0.2, 5, true, true, new Vector3D(3,0.5,1)));
        sciana0.addComponent(new MeshFilter(Mesh.QUAD));
        sciana0.addComponent(new MeshRenderer(new Color(49, 219, 239,200), 2f));
        sciana0.addComponent(new AngularDynamics(0, 0));
        sciana0.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana0.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.1,0.1,1)));

        GameObject sciana3 = new GameObject("SCIANA3");
        sciana3.addComponent(new Transform(new Vector3D(600,700), -0.4, 5, true, true, new Vector3D(3,0.2,1)));
        sciana3.addComponent(new MeshFilter(Mesh.QUAD));
        sciana3.addComponent(new MeshRenderer(new Color(49, 219, 239,200), 2f));
        sciana3.addComponent(new AngularDynamics(0, 0));
        sciana3.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana3.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.1,0.1,1)));

        GameObject sciana4 = new GameObject("SCIANA4");
        sciana4.addComponent(new Transform(new Vector3D(1000,300), -0.6, 5, true, true, new Vector3D(4,0.2,1)));
        sciana4.addComponent(new MeshFilter(Mesh.QUAD));
        sciana4.addComponent(new MeshRenderer(new Color(49, 219, 239,200), 2f));
        sciana4.addComponent(new AngularDynamics(0, 0));
        sciana4.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana4.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.1,0.1,1)));


        GameObject sciana1 = new GameObject("SCIANA OBROTOWA1");
        sciana1.addComponent(new Transform(new Vector3D(600,250), -0.2, 5, true, true, new Vector3D(3,0.3,1)));
        sciana1.addComponent(new MeshFilter(Mesh.QUAD));
        sciana1.addComponent(new MeshRenderer(new Color(243, 23, 255,200), 2f));
        sciana1.addComponent(new AngularDynamics(0, 0));
        sciana1.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana1.addComponent(new Rigidbody(0, 0.000001, new PhysicMaterial(0.3,0.3,0.3)));


        GameObject sciana2 = new GameObject("SCIANA OBROTOWA2");
        sciana2.addComponent(new Transform(new Vector3D(400,450), -0.2, 5, true, true, new Vector3D(2,0.3,1)));
        sciana2.addComponent(new MeshFilter(Mesh.QUAD));
        sciana2.addComponent(new MeshRenderer(new Color(253, 43, 0,200), 2f));
        sciana2.addComponent(new AngularDynamics(0, 0));
        sciana2.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana2.addComponent(new Rigidbody(0, 0.0005, new PhysicMaterial(0.3,0.3,0.3)));

        GameObject sciana5 = new GameObject("SCIANA OBROTOWA2");
        sciana5.addComponent(new Transform(new Vector3D(900,580), 0, 5, true, true, new Vector3D(2,0.3,1)));
        sciana5.addComponent(new MeshFilter(Mesh.QUAD));
        sciana5.addComponent(new MeshRenderer(new Color(34, 255, 0,200), 2f));
        sciana5.addComponent(new AngularDynamics(0, 0));
        sciana5.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana5.addComponent(new Rigidbody(0, 0.0005, new PhysicMaterial(0.3,0.3,0.3)));


        Scene scene = new Scene();
        scriptRock.scene = scene;

        scene.addGameObject(manager);
        scene.addGameObject(strop);
        scene.addGameObject(sciana0);
        scene.addGameObject(sciana1);
        scene.addGameObject(sciana2);
        scene.addGameObject(sciana3);
        scene.addGameObject(sciana4);
        scene.addGameObject(sciana5);


        ScarusEngine scarusEngine = new ScarusEngine(scene);
        RenderEngine renderEngine = new RenderEngine(scene);

        scarusEngine.start();
        renderEngine.start();
    }
}
