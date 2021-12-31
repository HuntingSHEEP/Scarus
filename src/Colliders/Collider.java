package Colliders;

import Components.Component;
import Components.Mesh;

public abstract class Collider extends Component {
    public Mesh mesh;
    protected double sphereRadius;

    public abstract double getSphereRadius();
}
