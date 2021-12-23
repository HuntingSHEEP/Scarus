package EngineCore;

import ScarMath.Vector3D;

import java.awt.*;

public class MeshRenderer extends Component{
    public Mesh mesh;
    private Color color;

    public MeshRenderer(Mesh mesh, Color color){
        this.mesh   = mesh;
        this.color  = color;
    }


    @Override
    public void update(double dt) {

    }

    public Polygon getPolygon() {
        int[] xPoly = new int[mesh.vertices.size()];
        int[] yPoly = new int[mesh.vertices.size()];

        for(int i=0; i<mesh.vertices.size(); i++){
            Vector3D v = mesh.vertices.get(i);
            xPoly[i] = (int) (v.x + gameObject.getComponent(Transform.class).position.x);
            yPoly[i] = (int) (v.y + gameObject.getComponent(Transform.class).position.y);
        }

        return new Polygon(xPoly, yPoly, xPoly.length);
    }

    public Color getColor() {
        return color;
    }
}
