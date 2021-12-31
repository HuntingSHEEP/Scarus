import Colliders.Collider;
import Components.*;
import EngineCore.*;
import Rendering.RenderEngine;
import Rendering.Scene;
import ScarMath.SMath;
import ScarMath.Vector3D;
import java.awt.*;

public class Test {
    public static void main(String[] args){
        GameObject go1 = new GameObject("pochodnia");
        go1.addComponent(new Transform(new Vector3D(400,311), 0,5));
        go1.addComponent(new LinearDynamics());
        go1.addComponent(new AngularDynamics(0, 0));
        go1.addComponent(new MeshFilter(Mesh.QUAD));
        go1.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        go1.addComponent(new Rigidbody(new PhysicMaterial(0,0,0)));

        GameObject go = new GameObject("kilof");
        go.addComponent(new PlayerControl());
        go.addComponent(new Transform(new Vector3D(400,200), 0,5));
        go.addComponent(new MeshRenderer(Color.CYAN, 1f));
        go.addComponent(new MeshFilter(Mesh.QUAD));
        go.addComponent(new LinearDynamics(new Vector3D(), new Vector3D(0,0)));
        go.addComponent(new AngularDynamics(0, 0));
        go.addComponent(new Rigidbody(new PhysicMaterial(0,0,0)));

        Scene scene = new Scene();
        scene.addGameObject(go);
        scene.addGameObject(go1);

        RenderEngine renderEngine = new RenderEngine(scene);
        ScarusEngine scarusEngine = new ScarusEngine(scene);



        renderEngine.start();
        scarusEngine.start();
    }
}
