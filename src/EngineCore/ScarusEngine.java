package EngineCore;

import Rendering.Scene;
import ScarMath.Vector3D;

public class ScarusEngine extends Thread{
    Scene scene;
    double deltaTime;

    public ScarusEngine(Scene scene) {
        this.scene = scene;
        deltaTime = 1;
    }

    public void run(){
        awake();

        while(true){
            startTime();

            updateStep();
            linearDynamicsStep();

            stopTime();
        }
    }



    ////////////////  STEPS SECTION  //////////////////////////////////
    private void awake() {
        for(GameObject gameObject : scene.getGameObjectList())
            for(Component component : gameObject.getComponentList())
                component.awake();
    }

    private void updateStep() {
        for(GameObject gameObject : scene.getGameObjectList())
            for(Component component : gameObject.getComponentList())
                component.update(deltaTime);
    }

    private void linearDynamicsStep() {
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

    ///////////////  TIME SECTION  ////////////////////////////////////
    private long start;
    private void stopTime() {
        deltaTime = System.nanoTime() - start;
        deltaTime /= 100000000; //UWAGA - ODJĄŁEM JEDNO ZERO
    }

    private void startTime() {
        start = System.nanoTime();
    }
}
