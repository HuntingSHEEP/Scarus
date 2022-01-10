package Colliders;

import Components.Mesh;

public class MeshCollider extends Collider {

    public MeshCollider(Mesh mesh){
        this.mesh = mesh;
    }

    @Override
    public void awake(){
        super.awake();
    }

    @Override
    public void update(double dt){
        super.update(dt);
    }
}
