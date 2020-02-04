package com.example.gs;

public class PowerUsageCalculator {
    private int time;
    private float cost;
    private float total_cost;
    private static final float RATE_PER_MIN = (float) 1.50;

    public PowerUsageCalculator(int tm){

    }
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        this.getTotal_cost ();
    }

    public void getTotal_cost() {
        float cst;
        this.total_cost = time * this.RATE_PER_MIN;
    }

}
