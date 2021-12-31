package EngineCore;

import Colliders.Collider;
import Colliders.Collision;
import Components.AngularDynamics;
import Components.Component;
import Components.LinearDynamics;
import Components.Transform;
import Rendering.Scene;
import ScarMath.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class ScarusEngine extends Thread{
    Scene scene;
    double deltaTime;

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
            STEP_COLLISIONS();

            stopTime();
        }
    }

    private void STEP_COLLISIONS() {
        //WYZNACZENIE UNIKALNYCH PAR OBIEKTÓW

        for(int g=0; g<scene.getGameObjectList().size(); g++)
            for(int h=g+1; h<scene.getGameObjectList().size(); h++){
                GameObject A = scene.getGameObjectList().get(g);
                GameObject B = scene.getGameObjectList().get(h);

                collisionTest(A, B);
            }


    }

    private void collisionTest(GameObject A, GameObject B) {
        Collider aCollider = A.getComponent(Collider.class);
        Collider bCollider = B.getComponent(Collider.class);

        if((aCollider != null) && (bCollider != null)){
            //System.out.println("LETS CHECK COLLISION BETWEEN "+A.name + " and " +B.name);

            if(TEST_COLLISION_CIRCLE(aCollider, bCollider)){
                //System.out.println("HEY, WE HAVE A CIRCLE COLLISION!");
                Collision collision = TEST_COLLISION_MESH(aCollider, bCollider);
                if(collision.collided){
                    System.out.println("WE MESH-COLLIDED!");
                }
            }
        }
    }

    private Collision TEST_COLLISION_MESH(Collider aCollider, Collider bCollider) {
        return SAT(aCollider, bCollider);
    }

    private boolean TEST_COLLISION_CIRCLE(Collider aCollider, Collider bCollider) {
        double aRadius = aCollider.getSphereRadius();
        double bRadius = bCollider.getSphereRadius();
        //System.out.println("A_RADIUS: "+ aRadius);
        //System.out.println("B_RADIUS: "+ bRadius);

        Vector3D aPosition = aCollider.gameObject.getComponent(Transform.class).position;
        Vector3D bPosition = bCollider.gameObject.getComponent(Transform.class).position;

        double centersDistance = Vector3D.pointsDistance(aPosition, bPosition);

        return (centersDistance <= (aRadius + bRadius));
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

    //////////////////////////////  TIME SECTION  ////////////////////////////////////
    private long start;
    private void stopTime() {
        deltaTime = System.nanoTime() - start;
        deltaTime /= 100000000; //UWAGA - ODJĄŁEM JEDNO ZERO
    }

    private void startTime() {
        start = System.nanoTime();
    }

    //////////////////////////////////////////////////////////////////////////////////
    public Collision SAT(Collider rect0, Collider rect1) {
        Vector3D normal = new Vector3D();
        double depth    = Double.MAX_VALUE;
        boolean onEdge  = false;

        List<Vector3D> verticesA = new ArrayList<Vector3D>();
        for(int i=0; i<rect0.mesh.vertices.size(); i++){
            Vector3D vertex = rect0.mesh.vertices.get(i).copy();
            vertex = Vector3D.add(vertex, rect0.gameObject.getComponent(Transform.class).position);

            verticesA.add(vertex);
        }

        List<Vector3D> verticesB = new ArrayList<Vector3D>();
        for(int i=0; i<rect1.mesh.vertices.size(); i++){
            Vector3D vertex = rect1.mesh.vertices.get(i).copy();
            vertex = Vector3D.add(vertex, rect1.gameObject.getComponent(Transform.class).position);

            verticesB.add(vertex);
        }

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

        Vector3D direction = Vector3D.minus(rect1.gameObject.getComponent(Transform.class).position, rect0.gameObject.getComponent(Transform.class).position);

        if(Vector3D.dot(direction, normal) < 0f){
            normal.multiply(-1);
        }

        if(Double.compare(depth, 0) == 0)
            onEdge = true;

        if(depth > 0){
            /*
            if(rect0.isFixed){
                rect1.location.position.add(Vector3D.multiply(normal, depth));
            }
            else if(rect1.isFixed){
                rect0.location.position.add(Vector3D.multiply(normal, depth * (-1)));
            }
            else
            {
             */
                Vector3D moveA = Vector3D.multiply(normal, -depth/2.0);
                rect0.gameObject.getComponent(Transform.class).position.add(moveA);

                Vector3D moveB = Vector3D.multiply(normal, depth/2.0);
                rect1.gameObject.getComponent(Transform.class).position.add(moveB);
            //}
        }

        //TODO: 2) przesłać do funkcji zaktualizowane obiekty
        verticesA = new ArrayList<Vector3D>();
        for(int i=0; i<rect0.mesh.vertices.size(); i++){
            Vector3D vertex = rect0.mesh.vertices.get(i).copy();
            vertex = Vector3D.add(vertex, rect0.gameObject.getComponent(Transform.class).position);

            verticesA.add(vertex);
        }

        verticesB = new ArrayList<Vector3D>();
        for(int i=0; i<rect1.mesh.vertices.size(); i++){
            Vector3D vertex = rect1.mesh.vertices.get(i).copy();
            vertex = Vector3D.add(vertex, rect1.gameObject.getComponent(Transform.class).position);

            verticesB.add(vertex);
        }





        Collision collision = new Collision();
        collision.A = rect0.gameObject;
        collision.B = rect1.gameObject;
        collision.collisionNormal = normal;
        collision.onEdge = onEdge;
        collision.collided = true;

        System.out.println("=================================\nNORMAL: " + normal);
        System.out.println("DEPTH : "+depth);

        List<Vector3D> contactPoints = getContactPoints(verticesA, verticesB, normal, depth);
        if(contactPoints.size() >0)
            collision.P = contactPoints.get(0);


        //System.out.println(contactPoints[0] + "\n" + contactPoints[1] + "\n" + contactPoints[2] + "\n" + contactPoints[3]);

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
        System.out.println("\nVETICE A: "+a1);
        double distA = Vector3D.dot(normal, a1);
        Vector3D a2 = null;

        for(int i=1; i<verticesA.size(); i++){

            Vector3D vertice = verticesA.get(i);
            System.out.println("VETICE A: "+vertice);
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
        System.out.println("\nVETICE B: "+b1);
        double distB = Vector3D.dot(Vector3D.multiply(normal, -1), b1);
        Vector3D b2 = null;

        for(int i=1; i<verticesB.size(); i++){
            Vector3D vertice = verticesB.get(i);
            System.out.println("VETICE B: "+vertice);
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
        System.out.println("POINTS ALONG FACE :\n"+pointsAlongFace[0]+"\n"+pointsAlongFace[1]+"\n"+pointsAlongFace[2]+"\n"+pointsAlongFace[3]);

        Vector3D faceVec = new Vector3D(-normal.y, normal.x);
        Vector3D minVertice = pointsAlongFace[0];
        Vector3D maxVertice = pointsAlongFace[0];
        double minDist = Vector3D.dot(faceVec, minVertice);
        double maxDist = Vector3D.dot(faceVec, maxVertice);

        for(int i=1; i<pointsAlongFace.length; i++){
            Vector3D vertex = pointsAlongFace[i];
            if(vertex == null) continue;

            double distance = Vector3D.dot(faceVec, vertex);
            if(distance < minDist){
                minDist = distance;
                minVertice = vertex;
            }else if(maxDist < distance){
                maxDist = distance;
                maxVertice = vertex;
            }
        }

        List<Vector3D> contactPoints = new ArrayList<Vector3D>();

        for(Vector3D point : pointsAlongFace){
            if(point == null) continue;;
            if(Vector3D.equall(point, minVertice)) continue;
            if(Vector3D.equall(point, maxVertice)) continue;
            contactPoints.add(point);
            System.out.println("Hi, I'm the contact point!: "+point);
        }




        return contactPoints;
    }
}
