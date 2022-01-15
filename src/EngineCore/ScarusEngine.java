package EngineCore;
import Colliders.Collider;
import Colliders.Collision;
import Colliders.Rigidbody;
import Components.*;
import Rendering.Scene;
import ScarMath.Vec2;
import ScarMath.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class ScarusEngine extends Thread{
    Scene scene;
    double deltaTime;
    private List<Collision> collisionList;

    public ScarusEngine(Scene scene) {
        this.scene = scene;
        deltaTime = 1;
    }

    public void run(){
        AWAKE();

        while(true){
            startTime();

            STEP_UPDATE();
            STEP_LINEAR_DYNAMICS();
            STEP_ANGULAR_DYNAMICS();
            STEP_CLEAR_COLLIDERS();
            STEP_COLLISION_DETECTION();
            STEP_COLLISION_RESOLUTION();

            stopTime();
        }
    }

    private void STEP_CLEAR_COLLIDERS() {
        for(GameObject gameObject : scene.getGameObjectList())
            for(Collider collider : gameObject.getComponentList(Collider.class))
                collider.clear();
    }

    private void STEP_COLLISION_RESOLUTION() {
        if(collisionList == null)
            return;

        //System.out.println("START COLLISION RESOLUTION");
        for(Collision collision : collisionList) {
            //System.out.println(collision.A.name +"  VS  "+collision.B.name);

            if (collision.aCollider.resolveCollision && collision.bCollider.resolveCollision) {
                moveObjectsApart(collision);
                collision = findContactPoint(collision);

                if (collision.P == null)
                    continue;

                resolveDynamics(collision);
            }
        }
    }

    private void resolveDynamics(Collision collision) {
        Rigidbody A = (Rigidbody) collision.aCollider;
        Rigidbody B = (Rigidbody) collision.bCollider;

        Vector3D n = collision.collisionNormal;
        n.normalize();

        Vec2 normal = new Vec2(n.x.floatValue(), n.y.floatValue());

        if(n.x.isNaN() || n.y.isNaN() || n.z.isNaN())
            return;

        float e = (float) Math.min(A.physicMaterial.bounciness, B.physicMaterial.bounciness);
        Vec2 P = new Vec2(collision.P.x.floatValue(), collision.P.y.floatValue());

        Vec2 ra = P.sub( new Vec2(A.transform.position.x.floatValue(),A.transform.position.y.floatValue()));
        Vec2 rb = P.sub( new Vec2(B.transform.position.x.floatValue(), B.transform.position.y.floatValue()));

        Vector3D bV = B.gameObject.getComponent(LinearDynamics.class).velocity;
        Vector3D aV = A.gameObject.getComponent(LinearDynamics.class).velocity;

        Vec2 Bvelocity = new Vec2(bV.x.floatValue(), bV.y.floatValue());
        Vec2 Avelocity = new Vec2(aV.x.floatValue(), aV.y.floatValue());

        double bOmega = B.gameObject.getComponent(AngularDynamics.class).angularVelocity;
        double aOmega = A.gameObject.getComponent(AngularDynamics.class).angularVelocity;

        Vec2 rv = Bvelocity.add( Vec2.cross((float) bOmega, rb, new Vec2() ) ).subi( Avelocity ).subi( Vec2.cross((float) aOmega, ra, new Vec2() ) );
        float contactVel = Vec2.dot( rv, normal );

        if (contactVel > 0)
        {
            return;
        }

        float raCrossN = Vec2.cross( ra, normal );
        float rbCrossN = Vec2.cross( rb, normal );

        float AinvInertia = (float) (A.invertedInertia);
        float BinvInertia = (float) (B.invertedInertia);

        float invMassSum = (float) (A.invertedMass + B.invertedMass + (raCrossN * raCrossN) * AinvInertia + (rbCrossN * rbCrossN) * BinvInertia);
        float j = -(1.0f + e) * contactVel;
        j /= invMassSum;

        Vec2 impulse = normal.mul( j );

        Vector3D AimpulsV = new Vector3D(impulse.neg().x * A.invertedMass, impulse.neg().y * A.invertedMass);
        Vector3D BimpulsV = new Vector3D(impulse.x * B.invertedMass, impulse.y * B.invertedMass);

        if(!A.positionFixed())
            A.gameObject.getComponent(LinearDynamics.class).velocity.add(AimpulsV);
        if(!A.rotationFixed())
            A.gameObject.getComponent(AngularDynamics.class).angularVelocity += A.invertedInertia * Vec2.cross(ra, impulse.neg());

        if(!B.positionFixed())
            B.gameObject.getComponent(LinearDynamics.class).velocity.add(BimpulsV);
        if(!B.rotationFixed())
            B.gameObject.getComponent(AngularDynamics.class).angularVelocity += B.invertedInertia * Vec2.cross(rb, impulse);


        //if(!A.isFixed()){
        //    A.gameObject.getComponent(LinearDynamics.class).velocity.add(AimpulsV);
       //     A.gameObject.getComponent(AngularDynamics.class).angularVelocity += A.invertedInertia * Vec2.cross(ra, impulse.neg());
       // }

        //if(!B.isFixed()){
         //   B.gameObject.getComponent(LinearDynamics.class).velocity.add(BimpulsV);
          //  B.gameObject.getComponent(AngularDynamics.class).angularVelocity += B.invertedInertia * Vec2.cross(rb, impulse);
        //}

        Bvelocity = new Vec2(bV.x.floatValue(), bV.y.floatValue());
        Avelocity = new Vec2(aV.x.floatValue(), aV.y.floatValue());
        rv = Bvelocity.add( Vec2.cross((float) B.gameObject.getComponent(AngularDynamics.class).angularVelocity, rb, new Vec2() ) ).subi( Avelocity ).subi( Vec2.cross((float) A.gameObject.getComponent(AngularDynamics.class).angularVelocity, ra, new Vec2() ) );

        Vec2 t = new Vec2( rv );
        t.addsi( normal, -Vec2.dot( rv, normal ) );
        t.normalize();

        float jt = -Vec2.dot( rv, t );
        jt /= invMassSum;
        //jt /= contactCount;


        if(Math.abs(jt) < 0.00000001){
            return;
        }

        float sf = (float)StrictMath.sqrt( A.physicMaterial.staticFriction * A.physicMaterial.staticFriction + B.physicMaterial.staticFriction * B.physicMaterial.staticFriction);
        float df = (float)StrictMath.sqrt( A.physicMaterial.dynamicFriction * A.physicMaterial.dynamicFriction + B.physicMaterial.dynamicFriction * B.physicMaterial.dynamicFriction);

        // Coulumb's law
        Vec2 tangentImpulse;
        // if(std::abs( jt ) < j * sf)
        if (StrictMath.abs( jt ) < j * sf)
        {
            // tangentImpulse = t * jt;
            tangentImpulse = t.mul( jt );
        }
        else
        {
            // tangentImpulse = t * -j * df;
            tangentImpulse = t.mul( j ).muli( -df );
        }

        AimpulsV = new Vector3D(tangentImpulse.neg().x * A.invertedMass, tangentImpulse.neg().y * A.invertedMass);
        BimpulsV = new Vector3D(tangentImpulse.x * B.invertedMass, tangentImpulse.y * B.invertedMass);

        if(!A.positionFixed())
            A.gameObject.getComponent(LinearDynamics.class).velocity.add(AimpulsV);
        if(!A.rotationFixed())
            A.gameObject.getComponent(AngularDynamics.class).angularVelocity += A.invertedInertia * Vec2.cross(ra, tangentImpulse.neg());

        //if(!A.isFixed()){
         //   A.gameObject.getComponent(LinearDynamics.class).velocity.add(AimpulsV);
          //  A.gameObject.getComponent(AngularDynamics.class).angularVelocity += A.invertedInertia * Vec2.cross(ra, tangentImpulse.neg());
        //}

        if(!B.positionFixed())
            B.gameObject.getComponent(LinearDynamics.class).velocity.add(BimpulsV);
        if(!B.rotationFixed())
            B.gameObject.getComponent(AngularDynamics.class).angularVelocity += B.invertedInertia * Vec2.cross(rb, tangentImpulse);


       // if(!B.isFixed()){
        //    B.gameObject.getComponent(LinearDynamics.class).velocity.add(BimpulsV);
         //   B.gameObject.getComponent(AngularDynamics.class).angularVelocity += B.invertedInertia * Vec2.cross(rb, tangentImpulse);
        //}
    }

    private Collision findContactPoint(Collision collision) {
        List<Vector3D> verticesA = collision.aCollider.getVertices();
        List<Vector3D> verticesB = collision.bCollider.getVertices();

        List<Vector3D> contactPoints = getContactPoints(verticesA, verticesB, collision.collisionNormal, collision.depth);
        if(contactPoints.size() >0)
            collision.P = contactPoints.get(0);

        return collision;
    }

    private void moveObjectsApart(Collision collision) {
        if(collision.depth > 0){

            if(collision.A.getComponent(Transform.class).fixedPosition){
                Vector3D moveB = Vector3D.multiply(collision.collisionNormal, collision.depth);
                collision.bCollider.gameObject.getComponent(Transform.class).position.add(moveB);
            }
            else if(collision.B.getComponent(Transform.class).fixedPosition){
                Vector3D moveA = Vector3D.multiply(collision.collisionNormal, -collision.depth);
                collision.aCollider.gameObject.getComponent(Transform.class).position.add(moveA);
            }
            else
            {
                Vector3D moveA = Vector3D.multiply(collision.collisionNormal, -collision.depth/2.0);
                Vector3D moveB = Vector3D.multiply(collision.collisionNormal, collision.depth/2.0);

                collision.aCollider.gameObject.getComponent(Transform.class).position.add(moveA);
                collision.bCollider.gameObject.getComponent(Transform.class).position.add(moveB);
            }
        }
    }


    //////////////////////////////  STEPS SECTION  //////////////////////////////////
    private void AWAKE() {
        for(GameObject gameObject : scene.getGameObjectList())
            for(Component component : gameObject.getComponentList())
                component.awake();
    }

    private void STEP_UPDATE() {
        for(GameObject gameObject : scene.getGameObjectList())
            for(Component component : gameObject.getComponentList())
                component.update(deltaTime);
    }

    private void STEP_LINEAR_DYNAMICS() {
        for(GameObject gameObject : scene.getGameObjectList()){
            Transform transform = gameObject.getComponent(Transform.class);
            LinearDynamics linearDynamics = gameObject.getComponent(LinearDynamics.class);

            if(linearDynamics != null){
                Vector3D a = linearDynamics.acceleration;
                Vector3D v = linearDynamics.velocity;

                //zmiana położenia
                double dx = v.x*deltaTime + (a.x/2)*deltaTime*deltaTime;
                double dy = v.y*deltaTime + (a.y/2)*deltaTime*deltaTime;
                Vector3D dPosition = new Vector3D(dx, dy);

                //zmiana prędkości
                double dvx = a.x*deltaTime;
                double dvy = a.y*deltaTime;
                Vector3D dVelocity = new Vector3D(dvx, dvy);

                //aktualizacja położenia
                transform.position.add(dPosition);

                //aktualizacja prędkości
                linearDynamics.velocity.add(dVelocity);
            }
        }
    }

    private void STEP_ANGULAR_DYNAMICS() {
        for(GameObject gameObject : scene.getGameObjectList()){
            Transform transform = gameObject.getComponent(Transform.class);
            AngularDynamics angularDynamics = gameObject.getComponent(AngularDynamics.class);

            if(angularDynamics != null){
                double alpha = angularDynamics.angularAcceleration;
                double omega = angularDynamics.angularVelocity;

                //ZMIANA KĄTA FI
                double dFI = omega*deltaTime + (alpha/2)*deltaTime*deltaTime;

                //ZMIANA PRĘDKOŚCI KĄTOWEJ
                double dOmega = alpha*deltaTime;

                //AKTUALIZACJA KĄTA FI
                transform.rotation += dFI;

                //AKTUALIZACJA PRĘKOŚCI KĄTOWEJ
                angularDynamics.angularVelocity += dOmega;
            }
        }
    }

    private void STEP_COLLISION_DETECTION() {
        //WYZNACZENIE UNIKALNYCH PAR OBIEKTÓW
        collisionList = new ArrayList<>();

        for(int g=0; g<scene.getGameObjectList().size(); g++)
            for(int h=g+1; h<scene.getGameObjectList().size(); h++){
                GameObject A = scene.getGameObjectList().get(g);
                GameObject B = scene.getGameObjectList().get(h);

                collisionTest(A, B);
            }
    }

    //////////////////////////////  TIME SECTION  ////////////////////////////////////
    private long start;
    private void stopTime() {
        deltaTime = System.nanoTime() - start;
        deltaTime /= 100000000; //UWAGA - ODJĄŁEM JEDNO ZERO
    }

    private void startTime() {
        start = System.nanoTime();
    }

    ///////////////////////////////  COLLISIONS  /////////////////////////////////////

    private void collisionTest(GameObject A, GameObject B) {
        List<Collider> aList = A.getComponentList(Collider.class);
        List<Collider> bList = B.getComponentList(Collider.class);

        for(Collider aCollider : aList)
            for(Collider bCollider : bList){

                //Collider aCollider = A.getComponent(Collider.class);
                //Collider bCollider = B.getComponent(Collider.class);

                if((aCollider != null) && (bCollider != null)) {
                    if (TEST_COLLISION_CIRCLE(aCollider, bCollider)) {
                        Collision collision = TEST_COLLISION_MESH(aCollider, bCollider);
                        if (collision.collided)
                            collisionList.add(collision);


                    }
                }


            }



    }

    private boolean TEST_COLLISION_CIRCLE(Collider aCollider, Collider bCollider) {
        double aRadius = aCollider.getSphereRadius();
        double bRadius = bCollider.getSphereRadius();

        Vector3D aPosition = aCollider.gameObject.getComponent(Transform.class).position;
        Vector3D bPosition = bCollider.gameObject.getComponent(Transform.class).position;

        double centersDistance = Vector3D.pointsDistance(aPosition, bPosition);

        return (centersDistance <= (aRadius + bRadius));
    }

    private Collision TEST_COLLISION_MESH(Collider aCollider, Collider bCollider) {
        return SAT(aCollider, bCollider);
    }

    public Collision SAT(Collider aCollider, Collider bCollider) {
        Vector3D normal = new Vector3D();
        double depth    = Double.MAX_VALUE;
        boolean onEdge  = false;

        List<Vector3D> verticesA, verticesB;
        verticesA = aCollider.getVertices();
        verticesB = bCollider.getVertices();

        for(int i=0; i<verticesA.size(); i++){
            Vector3D va = verticesA.get(i).copy();
            Vector3D vb = verticesA.get((i+1) % verticesA.size()).copy();

            Vector3D edge = Vector3D.minus(vb, va);
            Vector3D axis = new Vector3D(edge.y, -edge.x);

            double[] minMaxA = ProjectVertices(verticesA, axis);
            double minA = minMaxA[0];
            double maxA = minMaxA[1];

            double[] minMaxB = ProjectVertices(verticesB, axis);
            double minB = minMaxB[0];
            double maxB = minMaxB[1];

            //wyraźnie się rozchodzą
            if(minA > maxB || minB > maxA){
                return new Collision();
            }

            //ocho, mamy sygnał, że być MOŻE się stykają
            if(minA == maxB || minB == maxA) onEdge = true;

            double axisDepth = Double.min(maxB - minA, maxA - minB);

            if(axisDepth < depth){
                depth = axisDepth;
                normal = axis;
            }

        }

        for(int i=0; i<verticesB.size(); i++){
            Vector3D va = verticesB.get(i).copy();
            Vector3D vb = verticesB.get((i+1) % verticesB.size()).copy();

            Vector3D edge = Vector3D.minus(vb, va);
            Vector3D axis = new Vector3D(edge.y, -edge.x);

            double[] minMaxA = ProjectVertices(verticesA, axis);
            double minA = minMaxA[0];
            double maxA = minMaxA[1];

            double[] minMaxB = ProjectVertices(verticesB, axis);
            double minB = minMaxB[0];
            double maxB = minMaxB[1];

            //wyraźnie się rozchodzą
            if(minA > maxB || minB > maxA){
                return new Collision();
            }

            //ocho, mamy sygnał, że być MOŻE się stykają
            if(minA == maxB || minB == maxA) onEdge = true;

            double axisDepth = Double.min(maxB - minA, maxA - minB);

            if(axisDepth < depth){
                depth = axisDepth;
                normal = axis;
            }
        }

        depth /= normal.length();
        normal.normalize();

        Vector3D direction = Vector3D.minus(bCollider.gameObject.getComponent(Transform.class).position, aCollider.gameObject.getComponent(Transform.class).position);

        if(Vector3D.dot(direction, normal) < 0f){
            normal.multiply(-1);
        }

        if(Double.compare(depth, 0) == 0)
            onEdge = true;

        Collision collision = new Collision();
        collision.A = aCollider.gameObject;
        collision.B = bCollider.gameObject;
        collision.aCollider = aCollider;
        collision.bCollider = bCollider;
        collision.collisionNormal = normal;
        collision.onEdge = onEdge;
        collision.collided = true;
        collision.depth = depth;

        aCollider.collisionList.add(collision);
        bCollider.collisionList.add(collision);

        return collision;
    }

    private double[] ProjectVertices(List<Vector3D> vetices, Vector3D axis){
        Vector3D v = vetices.get(0);
        double proj = Vector3D.dot(v, axis);

        double min = proj;
        double max = proj;

        for(int i=1; i<vetices.size(); i++){
            v = vetices.get(i);
            proj = Vector3D.dot(v, axis);

            if(proj < min) { min = proj; }
            if(proj > max) { max = proj; }
        }

        return new double[]{min, max};
    }

    private List<Vector3D> getContactPoints(List<Vector3D> verticesA, List<Vector3D> verticesB, Vector3D normal, double depth) {
        double EPSILON = 1;
        Vector3D a1 = verticesA.get(0);
        //System.out.println("\nVETICE A: "+a1);
        double distA = Vector3D.dot(normal, a1);
        Vector3D a2 = null;

        for(int i=1; i<verticesA.size(); i++){

            Vector3D vertice = verticesA.get(i);
            //System.out.println("VETICE A: "+vertice);
            double distance = Vector3D.dot(normal, vertice);


            if(Math.abs(distance - distA) < EPSILON){
                a2 = vertice;
            }else if(distance>distA){
                distA = distance;
                a1 = vertice;
                a2 = null;
            }
        }

        Vector3D b1 = verticesB.get(0);
        //System.out.println("\nVETICE B: "+b1);
        double distB = Vector3D.dot(Vector3D.multiply(normal, -1), b1);
        Vector3D b2 = null;

        for(int i=1; i<verticesB.size(); i++){
            Vector3D vertice = verticesB.get(i);
            //System.out.println("VETICE B: "+vertice);
            double distance = Vector3D.dot(Vector3D.multiply(normal, -1), vertice);

            if(Math.abs(distance - distB) < EPSILON){
                b2 = vertice;
            }else if(distance>distB){
                distB = distance;
                b1 = vertice;
                b2 = null;
            }
        }

        Vector3D[] pointsAlongFace = new Vector3D[]{a1, a2, b1, b2};
        //System.out.println("\n-----------------------------\nPOINTS ALONG FACE :\n"+pointsAlongFace[0]+"\n"+pointsAlongFace[1]+"\n"+pointsAlongFace[2]+"\n"+pointsAlongFace[3]);

        Vector3D faceVec = new Vector3D(-normal.y, normal.x);
        Vector3D minVertice = pointsAlongFace[0];
        Vector3D maxVertice = pointsAlongFace[0];
        double minDist = Vector3D.dot(faceVec, minVertice);
        double maxDist = Vector3D.dot(faceVec, maxVertice);
        int minIndex   = 0;
        int maxIndex   = 0;

        for(int i=1; i<pointsAlongFace.length; i++){
            Vector3D vertex = pointsAlongFace[i];
            if(vertex == null) continue;

            double distance = Vector3D.dot(faceVec, vertex);
            if(distance < minDist){
                minDist     = distance;
                minVertice  = vertex;
                minIndex    = i;
            }else if(maxDist < distance){
                maxDist     = distance;
                maxVertice  = vertex;
                maxIndex    = i;
            }
        }

        pointsAlongFace[minIndex] = null;
        pointsAlongFace[maxIndex] = null;

        List<Vector3D> contactPoints = new ArrayList<Vector3D>();

        for(Vector3D point : pointsAlongFace){
            if(point == null) continue;;
            //if(Vector3D.equall(point, minVertice)) continue;
            //if(Vector3D.equall(point, maxVertice)) continue;
            contactPoints.add(point);
            //System.out.println("Hi, I'm the contact point!: "+point);
        }

        return contactPoints;
    }
}
