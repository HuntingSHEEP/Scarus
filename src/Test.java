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
        GameObject go1 = new GameObject("skała");
        go1.addComponent(new Transform(new Vector3D(400,400), 0.06,5, true, true, new Vector3D(5,0.5,1)));
        go1.addComponent(new LinearDynamics());
        go1.addComponent(new AngularDynamics(0, 0));
        go1.addComponent(new MeshFilter(Mesh.QUAD));
        go1.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        go1.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.6,0.5,0.3)));


        GameObject go = new GameObject("kilof");
        go.addComponent(new Rigidbody(0.1, 0.0001,new PhysicMaterial(0.6,0.5,0.7)));
        go.addComponent(new PlayerControl());
        go.addComponent(new Transform(new Vector3D(400,200), 0,5, false, false, new Vector3D(1,1,1)));
        go.addComponent(new MeshRenderer(Color.CYAN, 1f));
        go.addComponent(new MeshFilter(Mesh.QUAD));
        go.addComponent(new LinearDynamics(new Vector3D(), new Vector3D(0,1)));
        go.addComponent(new AngularDynamics(0, 0));





        /*
        GameObject go12 = new GameObject("pochodnia");
        go12.addComponent(new Transform(new Vector3D(100,311), 0,5));
        go12.addComponent(new LinearDynamics());
        go12.addComponent(new AngularDynamics(0, 0));
        go12.addComponent(new MeshFilter(Mesh.QUAD));
        go12.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        go12.addComponent(new Rigidbody(new PhysicMaterial(0,0,0)));

        GameObject go123 = new GameObject("pochodnia");
        go123.addComponent(new Transform(new Vector3D(500,311), 0,5));
        go123.addComponent(new LinearDynamics());
        go123.addComponent(new AngularDynamics(0, 0));
        go123.addComponent(new MeshFilter(Mesh.QUAD));
        go123.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        go123.addComponent(new Rigidbody(new PhysicMaterial(0,0,0)));

        scene.addGameObject(go12);
        scene.addGameObject(go123);
 */

        Scene scene = new Scene();
        scene.addGameObject(go);
        scene.addGameObject(go1);

        ScarusEngine scarusEngine = new ScarusEngine(scene);
        RenderEngine renderEngine = new RenderEngine(scene);

        scarusEngine.start();
        renderEngine.start();
    }
}
