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

    public List<String> getVocab() {
        return vocab;
    }

    public void setVocab(List<String> vocab) {
        this.vocab = vocab;
    }

    private List<String> vocab ;

    public String getMyGame() {
        return myGame;
    }

    public void setMyGame(String myGame) {
        this.myGame = myGame;
    }
    public Person(String username, String password, String question, String answer,
                  String myGame, String listen , List<String> vocab) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.myGame = myGame;
        this.vocab = vocab;
        this.listen = listen;
    }
    public Person(Person person) {
        this.username = person.username;
        this.password = person.password;
        this.question = person.question;
        this.answer = person.answer;
        this.myGame = person.myGame;
        this.vocab = person.vocab ;
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

