import EngineCore.*;
import InputInterface.Input;
import Rendering.RenderEngine;
import Rendering.RenderPanel;
import Rendering.Scene;
import ScarMath.SMath;
import ScarMath.Vector3D;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Scanner;


public class Test {
    public static void main(String[] args){
        GameObject go = new GameObject("Pochodnia");

        go.addComponent(new PlayerControl());
        go.addComponent(new Transform(new Vector3D(400,200), 2*SMath.pi/8,5));
        go.addComponent(new MeshRenderer(Mesh.QUAD, Color.CYAN, 3f));
        go.addComponent(new LinearDynamics());
        go.addComponent(new AngularDynamics(0, 0));

        Scene scene = new Scene();
        scene.addGameObject(go);

        RenderEngine renderEngine = new RenderEngine(scene);
        ScarusEngine scarusEngine = new ScarusEngine(scene);

        renderEngine.start();
        scarusEngine.start();
    }
}
