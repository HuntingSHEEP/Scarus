import Colliders.MeshCollider;
import Colliders.Rigidbody;
import Components.*;
import EngineCore.*;
import Rendering.RenderEngine;
import Rendering.Scene;
import ScarMath.Vector3D;
import java.awt.*;

public class Test {
    public static void main(String[] args){
        GameObject statek = new GameObject("STATEK");
        statek.addComponent(new Transform(new Vector3D(550,400), 0,5, false, false, new Vector3D(1,1.1,1)));
        statek.addComponent(new MeshFilter(new Mesh(MeshManager.SHIP)));
        statek.addComponent(new MeshRenderer(Color.CYAN, 1f));

        statek.addComponent(new AngularDynamics(0, 0));
        statek.addComponent(new PlayerControl());


        Scene scene = new Scene();
        scene.addGameObject(statek);

        ScarusEngine scarusEngine = new ScarusEngine(scene);
        RenderEngine renderEngine = new RenderEngine(scene);

        scarusEngine.start();
        renderEngine.start();
    }
}
