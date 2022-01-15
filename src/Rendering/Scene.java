package Rendering;

import Components.Component;
import EngineCore.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Scene{
    private List<GameObject> gameObjectList;
    private List<GameObject> bufferGameObjects;

    public Scene(){
        gameObjectList = new ArrayList<GameObject>();
        bufferGameObjects = new ArrayList<GameObject>();
    }

    public void addGameObject(GameObject gameObject){
        gameObjectList.add(gameObject);
    }

    public void bufferGameObject(GameObject gameObject) {
        bufferGameObjects.add(gameObject);
    }

    public void removeGameObject(int index){
        gameObjectList.remove(index);
    }

    public List<GameObject> getGameObjectList(){
        return gameObjectList;
    }

    public void awakeBufferedObjects() {
        for(GameObject gameObject : bufferGameObjects)
            for(Component component : gameObject.getComponentList())
                component.awake();
    }

    public void update(){
        if(bufferGameObjects.size() > 0){
            gameObjectList.addAll(bufferGameObjects);
            bufferGameObjects.clear();
        }
    }



}
