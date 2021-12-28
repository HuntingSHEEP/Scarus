package EngineCore;

public abstract class Component {
    public GameObject gameObject;
    public abstract void awake();
    public abstract void update(double dt);
}
