package Rendering;

import EngineCore.GameObject;
import Components.MeshRenderer;
import InputInterface.Input;
import Asteroidy.Shooted;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class RenderPanel extends JPanel implements KeyListener, MouseMotionListener, MouseInputListener {
    private Scene scene;
    public RenderPanel(Scene scene){
        super();

        setBackground(new Color(34, 42, 42));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);


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
        Graphics2D g2d = (Graphics2D) g;
        String score = "Score: " + Shooted.zbiteSkaly;
        g2d.setFont(new Font(null, Font.BOLD, 26));
        g2d.setColor(Color.white);
        g2d.drawString(score, 10, 20);
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
    public void keyPressed(KeyEvent e) {Input.setKeyPressed(e.getKeyCode());
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Input.setKeyPressed(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Input.setKeyReleased(e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
