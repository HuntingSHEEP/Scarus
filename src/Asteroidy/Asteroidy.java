package Asteroidy;

import Colliders.Rigidbody;
import Components.*;
import EngineCore.*;
import Rendering.RenderEngine;
import Rendering.Scene;
import ScarMath.Vector3D;

import java.awt.*;

public class Asteroidy {
    public static void main(String[] args){

        GameObject GoRockManager = new GameObject("GoRockManager");
        RockManager rockManager = new RockManager();
        GoRockManager.addComponent(rockManager);


        GameObject statek = new GameObject("STATEK");
        statek.addComponent(new Transform(new Vector3D(550,500), 0,5, false, false, new Vector3D(1,1.2,1)));
        statek.addComponent(new MeshFilter(new Mesh(MeshManager.SHIP)));
        statek.addComponent(new MeshRenderer(Color.CYAN, 1f));
        statek.addComponent(new AngularDynamics(0, 0));
        statek.addComponent(new LinearDynamics(new Vector3D(), new Vector3D()));
        statek.addComponent(new Rigidbody(0.1, 0.0001,new PhysicMaterial(0.6,0.5,0.7)));
        PlayerControl playerControl = new PlayerControl();
        statek.addComponent(playerControl);

        GameObject sciana = new GameObject("SCIANA");
        sciana.addComponent(new Transform(new Vector3D(550,0), 0, 5, true, true, new Vector3D(10.5,0.01,1)));
        sciana.addComponent(new MeshFilter(Mesh.QUAD));
        sciana.addComponent(new MeshRenderer(Color.RED, 1f));
        sciana.addComponent(new AngularDynamics(0, 0));
        sciana.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.6,0.5,0.3)));
        sciana.addComponent(new TeleportRock());

        GameObject sciana2 = new GameObject("SCIANA");
        sciana2.addComponent(new Transform(new Vector3D(550,865), 0, 5, true, true, new Vector3D(10.5,0.1,1)));
        sciana2.addComponent(new MeshFilter(Mesh.QUAD));
        sciana2.addComponent(new MeshRenderer(Color.RED, 1f));
        sciana2.addComponent(new AngularDynamics(0, 0));
        sciana2.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana2.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.6,0.5,0.3)));
        sciana2.addComponent(new TeleportRock());

        GameObject sciana3 = new GameObject("SCIANA");
        sciana3.addComponent(new Transform(new Vector3D(1090,435), 0, 5, true, true, new Vector3D(0.1,8.5,1)));
        sciana3.addComponent(new MeshFilter(Mesh.QUAD));
        sciana3.addComponent(new MeshRenderer(Color.RED, 1f));
        sciana3.addComponent(new AngularDynamics(0, 0));
        sciana3.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana3.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.6,0.5,0.3)));
        sciana3.addComponent(new TeleportRock());

        GameObject sciana4 = new GameObject("SCIANA");
        sciana4.addComponent(new Transform(new Vector3D(0,435), 0, 5, true, true, new Vector3D(0.01,8.5,1)));
        sciana4.addComponent(new MeshFilter(Mesh.QUAD));
        sciana4.addComponent(new MeshRenderer(Color.RED, 1f));
        sciana4.addComponent(new AngularDynamics(0, 0));
        sciana4.addComponent(new LinearDynamics(new Vector3D(0,0), new Vector3D()));
        sciana4.addComponent(new Rigidbody(0, 0, new PhysicMaterial(0.6,0.5,0.3)));
        sciana4.addComponent(new TeleportRock());

        GameObject skala = new GameObject("SKALA");
        skala.addComponent(new Transform(new Vector3D(600,754), 0, 5, false, false, new Vector3D(1.5,1.5,1)));
        skala.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala.addComponent(new AngularDynamics(0.1, 0));
        skala.addComponent(new LinearDynamics(new Vector3D(-0.5,7), new Vector3D()));
        skala.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));
        //skala.addComponent(new RockManager());
        //skala.addComponent(new Asteroidy.Shooted());
        Shooted shooted = new Shooted();
        shooted.setPlayerControl(playerControl);
        skala.addComponent(shooted);

        GameObject skala2 = new GameObject("SKALA");
        skala2.addComponent(new Transform(new Vector3D(100,250), 0, 5, false, false, new Vector3D(1.5,1.5,1)));
        skala2.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala2.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala2.addComponent(new AngularDynamics(0.1, 0));
        skala2.addComponent(new LinearDynamics(new Vector3D(-0.5,7), new Vector3D()));
        skala2.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));

        GameObject skala3 = new GameObject("SKALA");
        skala3.addComponent(new Transform(new Vector3D(970,400), 0, 5, false, false, new Vector3D(1.5,1.5,1)));
        skala3.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala3.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala3.addComponent(new AngularDynamics(0.1, 0));
        skala3.addComponent(new LinearDynamics(new Vector3D(-0.5,7), new Vector3D()));
        skala3.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));

        GameObject skala4 = new GameObject("SKALA");
        skala4.addComponent(new Transform(new Vector3D(400,150), 0, 5, false, false, new Vector3D(1.5,1.5,1)));
        skala4.addComponent(new MeshFilter(new Mesh(MeshManager.SKALA_1)));
        skala4.addComponent(new MeshRenderer(Color.ORANGE, 1f));
        skala4.addComponent(new AngularDynamics(0.1, 0));
        skala4.addComponent(new LinearDynamics(new Vector3D(-0.5,7), new Vector3D()));
        skala4.addComponent(new Rigidbody(0.1, 0.0001, new PhysicMaterial(0.6,0.5,0.3)));

        Scene scene = new Scene();

        scene.addGameObject(sciana);
        scene.addGameObject(sciana2);
        scene.addGameObject(sciana3);
        scene.addGameObject(sciana4);
        scene.addGameObject(statek);

        //scene.addGameObject(skala);
        /*scene.addGameObject(skala2);
        scene.addGameObject(skala3);
        scene.addGameObject(skala4);*/


        scene.addGameObject(GoRockManager);

        playerControl.scene = scene;
        shooted.scene = scene;
        rockManager.playerControl = playerControl;
        rockManager.scene = scene;
        shooted.rockManager = rockManager;
        playerControl.rockManager = rockManager;

        rockManager.createRock();
        rockManager.createRock();
        rockManager.createRock();
        rockManager.createRock();


        ScarusEngine scarusEngine = new ScarusEngine(scene);
        RenderEngine renderEngine = new RenderEngine(scene);

        scarusEngine.start();
        renderEngine.start();
    }
}
