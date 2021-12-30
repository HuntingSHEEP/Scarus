package EngineCore;
import Components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    public String name;
    private List<Component> components;

    private GameObject(){
        this.components = new ArrayList<Component>();
        this.name       = "Empty name string";
    }

    public GameObject(String name){
        this();
        this.name = name;
    }

    public List<Component> getComponentList() {
        return components;
    }

    public void addComponent(Component component){
        components.add(component);
        component.gameObject = this;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for(int i=0; i< components.size(); i++){
            Component c = components.get(i);
            if(componentClass.isAssignableFrom(c.getClass())){
                components.remove(i);
                return;
            }
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass){
        for(Component c : components)
            if(componentClass.isAssignableFrom(c.getClass()))
                try{
                    return componentClass.cast(c);
                }catch (ClassCastException e){
                    e.printStackTrace();
                    assert false: "COMPONENT CASTING ERROR";
                }

        return null;
    }



}
