package EngineCore;

import Rendering.Scene;

public class ScarusEngine extends Thread{
    Scene scene;
    double deltaTime;

    public ScarusEngine(Scene scene) {
        this.scene = scene;
        deltaTime = 1;
    }

    public void run(){
        while(true){
            startTime();
            updateStep();

            stopTime();
        }

    }

    private void updateStep() {
        for(GameObject gameObject : scene.getGameObjectList())
            for(Component component : gameObject.getComponentList())
                component.update(deltaTime);
    }

    ///////////////  TIME SECTION  ////////////////////////////////////
    private long start;
    private void stopTime() {
        deltaTime = System.nanoTime() - start;
        deltaTime /= 1000000000;
    }

    private void startTime() {
        start = System.nanoTime();
    }
}
