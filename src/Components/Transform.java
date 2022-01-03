package Components;

import Components.Component;
import ScarMath.Vector3D;

public class Transform extends Component {
    public Vector3D position;
    public double rotation;
    public Vector3D scale;
    public int layer;

    public boolean fixedPosition;
    public boolean fixedRotation;

    public Transform(){
        this.position = new Vector3D();
        this.rotation = 0f;
        this.layer    = 0;
    }

    public Transform(int layer){
        this();
        this.layer = layer;
    }

    public Transform(Vector3D position, int layer){
        this(layer);
        this.position = position;
    }

    public Transform(Vector3D position, double rotation, int layer, boolean fixedPosition, boolean fixedRotation, Vector3D scale){
        this(position, layer);
        this.rotation = rotation;
        this.fixedPosition = fixedPosition;
        this.fixedRotation = fixedRotation;
        this.scale = scale;
    }

    @Override
    public void awake() {

    }

    @Override
    public void update(double dt) {

    }
}
