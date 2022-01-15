package Rendering;

import javax.swing.*;

public class RenderEngine extends Thread{
    RenderPanel renderPanel;
    JFrame frame;

    public RenderEngine(Scene scene){
        renderPanel = new RenderPanel(scene);
        frame = new JFrame();

        frame.setSize(1100, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(renderPanel);
    }


    public void run(){
        System.out.println("Started the rendering engine!");
        while(true){
            renderPanel.repaint();
            waitSomeTime();
        }

    }

    private void waitSomeTime() {
        final long INTERVAL = (int) (100000); //odjąłem jedno zero
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + INTERVAL >= end);
    }
}
