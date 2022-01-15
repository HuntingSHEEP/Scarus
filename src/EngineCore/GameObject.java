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

    public void addComponent(Component component){
        components.add(component);
        component.gameObject = this; //PRZYPISUJEMY DO NASZEGO DZIECKA SIEBIE (TEN OBIEKT)
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

    public List<Component> getComponentList() {
        return components;
    }

    public <T extends Component> List<T> getComponentList(Class<T> componentClass){
        List<T> matchingComponents = new ArrayList<>();

        for(Component c : components)
            if(componentClass.isAssignableFrom(c.getClass()))
                try{
                    matchingComponents.add(componentClass.cast(c));
                }catch (ClassCastException e){
                    e.printStackTrace();
                    assert false: "COMPONENT CASTING ERROR";
                }

        return matchingComponents;
    }
}
