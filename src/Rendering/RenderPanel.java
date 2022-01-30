package Rendering;

import EngineCore.GameObject;
import Components.MeshRenderer;
import InputInterface.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RenderPanel extends JPanel implements KeyListener, MouseMotionListener {
    private Scene scene;
    public RenderPanel(Scene scene){
        super();

        setBackground(new Color(44, 52, 52));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);


        this.scene = scene;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        for(GameObject gameObject : scene.getGameObjectList()){
            MeshRenderer meshRenderer = gameObject.getComponent(MeshRenderer.class);

            if(meshRenderer != null)
                renderMesh(g, meshRenderer);

        }
    }

    private void renderMesh(Graphics g, MeshRenderer meshRenderer) {
        ((Graphics2D) g).setStroke(meshRenderer.getStroke());
        g.setColor(meshRenderer.getColor());
        g.drawPolygon(meshRenderer.getPolygon());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Input.setKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Input.setKeyReleased(e.getKeyCode());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) { Input.setMouseCords(e.getX(), e.getY()); }
}
