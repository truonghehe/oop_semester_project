package com.mycompany.app;

import java.util.List;

/**
 * The Person class represents a user with attributes such as username, password, security question,
 * security answer, listening preference, favorite game, and vocabulary list.
 */
public class Person {

    private final String username;
    private String password;
    private final String question;
    private final String answer;
    private String listen;
    private String myGame;
    private List<String> vocab;

    /**
     * Constructs a new Person with the specified attributes.
     *
     * @param username The username of the person.
     * @param password The password of the person.
     * @param question The security question of the person.
     * @param answer   The security answer of the person.
     * @param myGame   The favorite game of the person.
     * @param listen   The listening preference of the person.
     * @param vocab    The vocabulary list of the person.
     */
    public Person(String username, String password, String question, String answer,
                  String myGame, String listen, List<String> vocab) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.myGame = myGame;
        this.vocab = vocab;
        this.listen = listen;
    }

    /**
     * Constructs a new Person by copying the attributes from another Person.
     *
     * @param person The person to copy attributes from.
     */
    public Person(Person person) {
        this.username = person.username;
        this.password = person.password;
        this.question = person.question;
        this.answer = person.answer;
        this.myGame = person.myGame;
        this.vocab = person.vocab;
        this.listen = person.listen;
    }

    /**
     * Gets the username of the person.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the person.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the person.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the security question of the person.
     *
     * @return The security question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the security answer of the person.
     *
     * @return The security answer.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Gets the listening preference of the person.
     *
     * @return The listening preference.
     */
    public String getListen() {
        return listen;
    }

    /**
     * Sets the listening preference of the person.
     *
     * @param listen The new listening preference to set.
     */
    public void setListen(String listen) {
        this.listen = listen;
    }

    /**
     * Gets the favorite game of the person.
     *
     * @return The favorite game.
     */
    public String getMyGame() {
        return myGame;
    }

    /**
     * Sets the favorite game of the person.
     *
     * @param myGame The new favorite game to set.
     */
    public void setMyGame(String myGame) {
        this.myGame = myGame;
    }

    /**
     * Gets the vocabulary list of the person.
     *
     * @return The vocabulary list.
     */
    public List<String> getVocab() {
        return vocab;
    }

    /**
     * Sets the vocabulary list of the person.
     *
     * @param vocab The new vocabulary list to set.
     */
    public void setVocab(List<String> vocab) {
        this.vocab = vocab;
    }
}
