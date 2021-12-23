package Rendering;

import EngineCore.GameObject;
import EngineCore.MeshRenderer;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {
    private Scene scene;
    public RenderPanel(Scene scene){
        super();

        setBackground(new Color(44, 52, 52));
        setLayout(null);
        setFocusable(true);

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
}
