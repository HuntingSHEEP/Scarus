package Rendering;

import Components.Component;
import EngineCore.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Scene{
    private List<GameObject> gameObjectList;
    private List<GameObject> bufferGameObjects;
    private List<GameObject> bufferRemovedGameObjects;

    public Scene(){
        gameObjectList = new ArrayList<GameObject>();
        bufferGameObjects = new ArrayList<GameObject>();
        bufferRemovedGameObjects = new ArrayList<GameObject>();
    }

    public void addGameObject(GameObject gameObject){
        gameObjectList.add(gameObject);
    }

    public void bufferGameObject(GameObject gameObject) {
        bufferGameObjects.add(gameObject);
    }

    public void bufferRemovedGameObject(GameObject gameObject){
        bufferRemovedGameObjects.add(gameObject);
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
        if(bufferRemovedGameObjects.size() > 0){
            System.out.println("usuwam gameobject");
            gameObjectList.removeAll(bufferRemovedGameObjects);
            bufferRemovedGameObjects.clear();
        }
    }
}
