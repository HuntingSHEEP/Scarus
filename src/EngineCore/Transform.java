package EngineCore;

import ScarMath.Vector3D;

public class Transform extends Component {
    public Vector3D position;
    public double rotation;
    public int layer;

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

    public Transform(Vector3D position, double rotation, int layer){
        this(position, layer);
        this.rotation = rotation;
    }

    @Override
    public void awake() {

    }

    @Override
    public void update(double dt) {

    }
}
