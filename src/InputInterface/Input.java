package InputInterface;

import java.util.ArrayList;
import java.util.List;

public class Input {
    private static List<Integer> pressedList    = new ArrayList<>();
    private static List<Integer> downList       = new ArrayList<>();
    private static List<Integer> releasedList   = new ArrayList<>();

    public static void setKeyPressed(int keyPressed){
        if((!downList.contains((Integer) keyPressed)) && (!pressedList.contains((Integer) keyPressed))){
            downList.add(keyPressed);
            pressedList.add(keyPressed);

        }else{
            downList.remove((Integer) keyPressed);
        }
    }

    public static boolean getKeyPressed(int key){
        for(int k : pressedList)
            if(k == key)
                return true;

        return false;
    }

    public static boolean getKeyDown(int key){
        for(int k : downList)
            if(k == key){
                downList.remove((Integer) key);
                return true;
            }


        return false;
    }

    public static void setKeyReleased(int keyReleased){
        releasedList.add(keyReleased);
        pressedList.remove((Integer) keyReleased);
        downList.remove((Integer) keyReleased);
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

}
