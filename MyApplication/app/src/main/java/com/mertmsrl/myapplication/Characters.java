package com.mertmsrl.myapplication;

public class Characters {
    int weight;
    int moveCount = 20;
    int fightForce;
    String name;

    public String eatFood(){
        if (moveCount > 0) {
            weight++;
            moveCount--;
        }
        return "Not enough move count ";
    }
    public String sleep(){
        if (moveCount >= 0) {
            moveCount++;
            return "Karakter Uyuyor";
        }
        return "Not enough move count ";
    }
    public String fight(){
        if (moveCount > 0 && weight > 0) {
            moveCount--;
            weight--;
            return "Karakter uyuyor";
        }
        return "Not enough move count ";
    }

    @Override
    public String toString() {
        return
                "weight=" + weight +
                ", moveCount=" + moveCount +
                ", fightForce=" + fightForce +
                ", name='" + name ;
    }
}
