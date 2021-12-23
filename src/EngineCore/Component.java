package EngineCore;

public abstract class Component {
    public GameObject gameObject;
    public abstract void update(double dt);
}
