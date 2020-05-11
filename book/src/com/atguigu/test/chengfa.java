package com.atguigu.test;

public class chengfa {
    public double a = 2.863;
    public double b = 2.035;
    public double c = 110;

    public static void main(String[] args) {
        double cheng1 = cheng(2.863, 2.035, 110);
        System.out.println(cheng1);
    }

    public static double cheng(double a, double b, double c){
        return a * b * c;
    }
}
