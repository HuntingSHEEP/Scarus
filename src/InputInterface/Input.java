package InputInterface;

import ScarMath.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Input {
    private static List<Integer> pressedList    = new ArrayList<>();
    private static List<Integer> downList       = new ArrayList<>();
    private static List<Integer> releasedList   = new ArrayList<>();

    //TODO: zabezpieczyć pętle przed zmianami w trakcie wykonywania obrotu;
    private static boolean  block_pressed   = false;
    private static boolean  block_down      = false;
    private static boolean  block_released  = false;

    private static double AXIS_HORIZONTAL   = 0f;
    private static double AXIS_VERTICAL     = 0f;

    private static int MOUSE_X;
    private static int MOUSE_Y;

    public static void setKeyPressed(int keyPressed){
        if((!downList.contains((Integer) keyPressed)) && (!pressedList.contains((Integer) keyPressed))){
            downList.add(keyPressed);
            pressedList.add(keyPressed);
            
            setUpAxis(KeyCode.AXIS_HORIZONTAL   , keyPressed);
            setUpAxis(KeyCode.AXIS_VERTICAL     , keyPressed);
        }else{
            downList.remove((Integer) keyPressed);
        }
    }

    public static void setMouseCords(int x, int y)
    {
        MOUSE_X = x;
        MOUSE_Y = y;
    }
    public static Vector3D getMouseCords()
    {
        return new Vector3D(MOUSE_X, MOUSE_Y);
    }

    public static boolean getKeyPressed(int key){
        for(int k : pressedList)
            if(k == key)
                return true;

        return false;
    }

    public static boolean getKeyDown(int key){
        try{
            for(int k : downList)
                if(k == key){
                    downList.remove((Integer) key);
                    return true;
                }
        }catch (Exception e){
            System.out.println("JUST CACHED AN INPUT ERROR!");
            e.printStackTrace();
        }

        return false;
    }

    public static void setKeyReleased(int keyReleased){
        releasedList.add(keyReleased);
        pressedList.remove((Integer) keyReleased);
        downList.remove((Integer) keyReleased);

        clearAxis(KeyCode.AXIS_HORIZONTAL,  keyReleased);
        clearAxis(KeyCode.AXIS_VERTICAL,    keyReleased);
    }

    public static boolean getKeyReleased(int key){
        for(int k : releasedList)
            if(k == key){
                releasedList.remove((Integer) key);
                //downList.remove((Integer) key);
                return true;
            }

        return false;
    }

    ////////////////////////////////////////  AXIS SECTION ///////////////////////////////////////
    public static double getAxis(int axis){
        if(axis == KeyCode.AXIS_HORIZONTAL){
            return AXIS_HORIZONTAL;
        }else if(axis == KeyCode.AXIS_VERTICAL){
            return AXIS_VERTICAL;
        }

        return 0f;
    }

    private static void clearAxis(int axis, int key) {
        switch (axis){
            case KeyCode.AXIS_HORIZONTAL:{
                clearHorizontalAxis(key);
                break;
            }
            case KeyCode.AXIS_VERTICAL:{
                clearVerticalAxis(key);
                break;
            }
        }
    }


    private static void clearHorizontalAxis(int key) {
        switch (key){
            case KeyCode.ARROW_RIGHT:
            case KeyCode.D:
            case KeyCode.ARROW_LEFT:
            case KeyCode.A:{
                AXIS_HORIZONTAL = 0;
                break;
            }
        }
    }

    private static void clearVerticalAxis(int key) {
        switch (key){
            case KeyCode.ARROW_UP:
            case KeyCode.W:
            case KeyCode.ARROW_DOWN:
            case KeyCode.S:{
                AXIS_VERTICAL = 0;
                break;
            }
        }
    }


    private static void setUpAxis(int axis, int keyPressed) {
        switch (axis){
            case KeyCode.AXIS_HORIZONTAL:{
                setupHorizontalAxis(keyPressed);
                break;
            }
            case KeyCode.AXIS_VERTICAL:{
                setUpVerticalAxis(keyPressed);
                break;
            }
        }
    }

    private static void setupHorizontalAxis(int keyPressed) {
        switch (keyPressed){
            case KeyCode.ARROW_RIGHT:
            case KeyCode.D:{
                AXIS_HORIZONTAL = 1;
                break;
            }
            case KeyCode.ARROW_LEFT:
            case KeyCode.A:{
                AXIS_HORIZONTAL = -1;
                break;
            }
        }
    }

    private static void setUpVerticalAxis(int keyPressed) {
        switch (keyPressed){
            case KeyCode.ARROW_UP:
            case KeyCode.W:{
                AXIS_VERTICAL = 1;
                break;
            }
            case KeyCode.ARROW_DOWN:
            case KeyCode.S:{
                AXIS_VERTICAL = -1;
                break;
            }
        }
    }

}
