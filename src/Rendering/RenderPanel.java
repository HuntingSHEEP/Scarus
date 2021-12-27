package Rendering;

import EngineCore.GameObject;
import EngineCore.MeshRenderer;
import InputInterface.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RenderPanel extends JPanel implements KeyListener {
    private Scene scene;
    public RenderPanel(Scene scene){
        super();

        setBackground(new Color(44, 52, 52));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);

        this.scene = scene;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1f));
        g2d.setPaint(new Color(164, 102, 123));

        g.drawLine(200, 200, 400, 200);
        g.drawLine(200, 200, 200, 400);

        for(GameObject gameObject : scene.getGameObjectList()){
            MeshRenderer meshRenderer = gameObject.getComponent(MeshRenderer.class);

            if(meshRenderer != null)
                renderMesh(g, meshRenderer);

        }
    }

    private void renderMesh(Graphics g, MeshRenderer meshRenderer) {
        g.setColor(meshRenderer.getColor());
        g.drawPolygon(meshRenderer.getPolygon());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("DOWN: " + e.getKeyChar()+"  //KOD>> "+e.getKeyCode());

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("pressed: " + e.getKeyChar()+"  //KOD>> "+e.getKeyCode());
        Input.setKeyPressed(e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("released: "+e.getKeyChar()+"  //KOD>> "+e.getKeyCode()+"\n-------------------");
        Input.setKeyReleased(e.getKeyCode());
    }
}
