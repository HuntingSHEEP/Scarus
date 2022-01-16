import Colliders.Collider;
import Colliders.Collision;
import Colliders.MeshCollider;
import Components.*;
import Colliders.Rigidbody;
import Components.Component;
import EngineCore.GameObject;
import InputInterface.Input;
import InputInterface.KeyCode;
import Rendering.Scene;
import ScarMath.SMath;
import ScarMath.Vector3D;

import java.awt.*;

public class PlayerControl extends Component {
    int linearAccelerationValue;
    double angularVelocityValue;

    Vector3D objectLinearAcceleration;
    AngularDynamics angularDynamics;
    Collider collider;
    Scene scene;
    Transform transform;


    @Override
    public void awake() {
        linearAccelerationValue = 3;
        angularVelocityValue    = 0.5f;

        objectLinearAcceleration = gameObject.getComponent(LinearDynamics.class).acceleration;
        angularDynamics = gameObject.getComponent(AngularDynamics.class);
        collider        = gameObject.getComponent(Rigidbody.class);
        transform       = gameObject.getComponent(Transform.class);
    }

    @Override
    public void update(double dt) {
        manageWSAD();
        manageRotation();
        manageShooting();
        collision();
    }

    private void manageShooting() {
        if(Input.getKeyDown(KeyCode.SPACE))
            shoot();
    }

    private void shoot() {
        System.out.println("SHOOT");
        GameObject pocisk = createPocisk();
        scene.bufferGameObject(pocisk);
    }

    private void collision() {
        for(Collision collision : collider.collisionList)
            if (collision != null)
                if (collision.collided) {
                    GameObject obiektKolizji = collision.A;
                    if (obiektKolizji == gameObject)
                        obiektKolizji = collision.B;

                    System.out.println("Detected collision with object [" + obiektKolizji.name +"]");
                }
    }

    private void manageWSAD() {
        if(Input.getKeyDown(KeyCode.W))
            objectLinearAcceleration.add(new Vector3D(0, -linearAccelerationValue));
        if(Input.getKeyReleased(KeyCode.W))
            objectLinearAcceleration.add(new Vector3D(0, linearAccelerationValue));

        if(Input.getKeyDown(KeyCode.S))
            objectLinearAcceleration.add(new Vector3D(0, linearAccelerationValue));
        if(Input.getKeyReleased(KeyCode.S))
            objectLinearAcceleration.add(new Vector3D(0, -linearAccelerationValue));

        if(Input.getKeyDown(KeyCode.A))
            objectLinearAcceleration.add(new Vector3D(-linearAccelerationValue, 0));
        if(Input.getKeyReleased(KeyCode.A))
            objectLinearAcceleration.add(new Vector3D(linearAccelerationValue, 0));

        if(Input.getKeyDown(KeyCode.D))
            objectLinearAcceleration.add(new Vector3D(linearAccelerationValue, 0));
        if(Input.getKeyReleased(KeyCode.D))
            objectLinearAcceleration.add(new Vector3D(-linearAccelerationValue, 0));
    }

    private void manageRotation() {
        if(Input.getKeyDown(KeyCode.Q))
            angularDynamics.angularVelocity -= angularVelocityValue;
        if(Input.getKeyReleased(KeyCode.Q))
            angularDynamics.angularVelocity += angularVelocityValue;

        if(Input.getKeyDown(KeyCode.E))
            angularDynamics.angularVelocity += angularVelocityValue;
        if(Input.getKeyReleased(KeyCode.E))
            angularDynamics.angularVelocity -= angularVelocityValue;
    }

    private GameObject createPocisk() {
        Vector3D speed = SMath.rotatePoint(new Vector3D(0,-1), new Vector3D(0,0,1), transform.rotation);


        GameObject pocisk = new GameObject("POCISK");
        pocisk.addComponent(new Transform(transform.position.copy(), 0, 5, false, false, new Vector3D(0.2,0.2,1)));
        pocisk.addComponent(new MeshFilter(Mesh.QUAD));
        pocisk.addComponent(new MeshRenderer(Color.GRAY, 1f));
        pocisk.addComponent(new AngularDynamics(0, 0));
        pocisk.addComponent(new LinearDynamics(speed.multiply(20), new Vector3D()));

        return pocisk;
    }

}
