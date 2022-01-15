package Components;

import EngineCore.GameObject;

public abstract class Component {
    public GameObject gameObject; //DO PRZIPISYWANIA TYLKO I WYLACZNIE RODZICA
    public abstract void awake();
    public abstract void update(double dt);
}
