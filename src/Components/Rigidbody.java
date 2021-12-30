package Components;

public class Rigidbody extends Component{
    public PhysicMaterial physicMaterial;

    private Transform   transform;
    private Mesh        mesh;

    public Rigidbody(PhysicMaterial physicMaterial){
        this.physicMaterial = physicMaterial;
    }

    @Override
    public void awake() {
        this.transform  = gameObject.getComponent(Transform.class);
        this.mesh = gameObject.getComponent(MeshFilter.class).mesh;
    }

    @Override
    public void update(double dt) {
    }
}
