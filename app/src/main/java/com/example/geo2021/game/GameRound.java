package com.example.geo2021.game;

import java.util.List;

public class GameRound {
    public String questions;
    public List<String> answers;
    public String correctAnswer;

    public boolean provideAnswer(String value){
        return correctAnswer.equals(value);
    }
}
