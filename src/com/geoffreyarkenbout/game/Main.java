package com.geoffreyarkenbout.game;

import com.geoffreyarkenbout.engine.GameEngine;
import com.geoffreyarkenbout.engine.IGameLogic;

public class Main {

    private static final int width = 800;
    private static final int height = 600;
    private static final String title = "Hello World!";
    private static boolean vSync = true;

    public static void main(String[] args){
        try{
            IGameLogic logic = new DummyGame();
            GameEngine engine = new GameEngine(title, width, height, vSync, logic);
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
