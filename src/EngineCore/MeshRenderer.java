package EngineCore;

import ScarMath.Vector3D;

import java.awt.*;

public class MeshRenderer extends Component{
    public Mesh mesh;
    private Color color;
    private Stroke stroke;

    public MeshRenderer(Mesh mesh, Color color, float lineWidth){
        this.mesh    = mesh;
        this.color   = color;
        this.stroke  = new BasicStroke(lineWidth);
    }

    @Override
    public void awake() {
    }

    @Override
    public void update(double dt) {
    }

    public Polygon getPolygon() {
        int[] xPoly = new int[mesh.vertices.size()];
        int[] yPoly = new int[mesh.vertices.size()];

        Vector3D position = gameObject.getComponent(Transform.class).position;
        //System.out.println("POSITION >> "+position);

        for(int i=0; i<mesh.vertices.size(); i++){
            Vector3D v = mesh.vertices.get(i);
            xPoly[i] = (int) (v.x + position.x);
            yPoly[i] = (int) (v.y + position.y);
        }

        return new Polygon(xPoly, yPoly, xPoly.length);
    }

    public Color getColor() {
        return color;
    }
    public Stroke getStroke() { return stroke;}
}
