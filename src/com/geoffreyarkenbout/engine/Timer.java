package com.geoffreyarkenbout.engine;

public class Timer {
    private double lastLoopTime;

    public void init() {
        lastLoopTime = getTime();
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public float getElapsedTime(){
        double current = System.currentTimeMillis();
        float elapsed = (float)(current - lastLoopTime);
        lastLoopTime = current;
        return elapsed;

    }

    public void reset(){
        lastLoopTime = System.currentTimeMillis();
    }

    public double getLastLoopTime(){
        return lastLoopTime;
    }
}
