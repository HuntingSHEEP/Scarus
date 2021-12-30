package Components;

import ScarMath.SMath;
import ScarMath.Vector3D;

import java.awt.*;
import java.util.ArrayList;

import java.util.List;

public class MeshRenderer extends Component {
    public Mesh mesh;
    private Color color;
    private Stroke stroke;

    public MeshRenderer(Color color, float lineWidth){
        this.color   = color;
        this.stroke  = new BasicStroke(lineWidth);
    }

    @Override
    public void awake() {
        this.mesh = gameObject.getComponent(MeshFilter.class).mesh;
    }

    @Override
    public void update(double dt) {
    }

    public Polygon getPolygon() {
        int[] xPoly = new int[mesh.vertices.size()];
        int[] yPoly = new int[mesh.vertices.size()];

        Vector3D position = gameObject.getComponent(Transform.class).position;
        double   rotation = gameObject.getComponent(Transform.class).rotation;

        //FIRST ROTATE THE MESH
        List<Vector3D> rotatedVertices = new ArrayList<>();
        for(Vector3D vertice : mesh.vertices)
            rotatedVertices.add(SMath.rotatePoint(vertice, new Vector3D(0,0, 1), rotation));

        //THEN PLACE THE MESH IN THE CORRECT POSITION
        for(int i=0; i<rotatedVertices.size(); i++){
            Vector3D v = rotatedVertices.get(i);
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
