package Rendering;

import EngineCore.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Scene{
    private List<GameObject> gameObjectList;

    public Scene(){
        gameObjectList = new ArrayList<GameObject>();
    }

    public void addGameObject(GameObject gameObject){
        gameObjectList.add(gameObject);
    }

    public void removeGameObject(int index){
        gameObjectList.remove(index);
    }

    public List<GameObject> getGameObjectList(){
        return gameObjectList;
    }
}
