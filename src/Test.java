import Components.*;
import EngineCore.*;
import Rendering.RenderEngine;
import Rendering.Scene;
import ScarMath.SMath;
import ScarMath.Vector3D;
import java.awt.*;

public class Test {
    public static void main(String[] args){
        GameObject go = new GameObject("Pochodnia");
        go.addComponent(new PlayerControl());
        go.addComponent(new Transform(new Vector3D(400,200), 2*SMath.pi/8,5));
        go.addComponent(new MeshRenderer(Color.CYAN, 3f));
        go.addComponent(new MeshFilter(Mesh.QUAD));
        go.addComponent(new LinearDynamics(new Vector3D(), new Vector3D(0,0)));
        go.addComponent(new AngularDynamics(0, 0));
        go.addComponent(new Rigidbody(new PhysicMaterial(0,0,0)));

        Scene scene = new Scene();
        scene.addGameObject(go);

        RenderEngine renderEngine = new RenderEngine(scene);
        ScarusEngine scarusEngine = new ScarusEngine(scene);

        renderEngine.start();
        scarusEngine.start();
    }
}
