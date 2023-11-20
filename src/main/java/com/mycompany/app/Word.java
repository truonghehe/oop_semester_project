package com.mycompany.app;

/**
 * The Word class represents a word in a dictionary with its target (English) and explanation (Vietnamese).
 */
public class Word {

    private String word_target;
    private String word_explain;

    /**
     * Default constructor for the Word class.
     */
    public Word() {
        // Default constructor
    }

    /**
     * Parameterized constructor for the Word class.
     *
     * @param word_target  The target word (English).
     * @param word_explain The explanation of the word (Vietnamese).
     */
    public Word(String word_target, String word_explain) {
        this.word_explain = word_explain;
        this.word_target = word_target;
    }

    /**
     * Gets the target word (English).
     *
     * @return The target word.
     */
    public String getWord_target() {
        return word_target;
    }

    /**
     * Sets the target word (English).
     *
     * @param word_target The target word to set.
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**
     * Gets the explanation of the word (Vietnamese).
     *
     * @return The explanation of the word.
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * Sets the explanation of the word (Vietnamese).
     *
     * @param word_explain The explanation of the word to set.
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}
