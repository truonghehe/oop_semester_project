package com.mycompany.app;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final String username;

    private String password;
    private final String question;
    private final String answer;

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    private String listen;
    private String myGame ;
    private String vocab1;

    public String getMyGame() {
        return myGame;
    }

    public void setMyGame(String myGame) {
        this.myGame = myGame;
    }

    public String getVocab1() {
        return vocab1;
    }

    public void setVocab1(String vocab1) {
        this.vocab1 = vocab1;
    }

    public String getVocab2() {
        return vocab2;
    }

    public void setVocab2(String vocab2) {
        this.vocab2 = vocab2;
    }

    public String getVocab3() {
        return vocab3;
    }

    public void setVocab3(String vocab3) {
        this.vocab3 = vocab3;
    }

    public String getVocab4() {
        return vocab4;
    }

    public void setVocab4(String vocab4) {
        this.vocab4 = vocab4;
    }

    private String vocab2;

    public Person(String username, String password, String question, String answer,
                  String myGame, String vocab1, String vocab2, String vocab3, String vocab4 , String listen) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.myGame = myGame;
        this.vocab1 = vocab1;
        this.vocab2 = vocab2;
        this.vocab3 = vocab3;
        this.vocab4 = vocab4;
        this.listen = listen;
    }

    private String vocab3;
    private String vocab4;


    public Person(Person person) {
        this.username = person.username;
        this.password = person.password;
        this.question = person.question;
        this.answer = person.answer;
        this.myGame = person.myGame;
        this.vocab1 = person.vocab1;
        this.vocab2 = person.vocab2;
        this.vocab3 = person.vocab3;
        this.vocab4  = person.vocab4;
        this.listen = person.listen;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }


    public String getAnswer() {
        return answer;
    }


}

