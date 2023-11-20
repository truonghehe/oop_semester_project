package com.mycompany.app.TranslateApi;

import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

/**
 * A class for text-to-speech conversion using the FreeTTS library.
 */
public class TextToSpeech {

    private SynthesizerModeDesc synthesizerModeDesc;
    private Synthesizer synthesizer;
    private Voice voice;

    /**
     * Initializes the TextToSpeech engine with the specified voice.
     *
     * @param voiceName The name of the voice to be used.
     * @throws EngineException       if an error occurs while initializing the engine.
     * @throws AudioException        if there is an audio-related error.
     * @throws EngineStateError     if there is a state-related error with the engine.
     * @throws PropertyVetoException if a property veto occurs.
     */
    public void initialize(String voiceName) throws EngineException, AudioException, EngineStateError, PropertyVetoException {
        if (synthesizerModeDesc == null) {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            synthesizerModeDesc = new SynthesizerModeDesc(Locale.US);
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(synthesizerModeDesc);
            synthesizer.allocate();
            synthesizer.resume();
            SynthesizerModeDesc engineModeDesc = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
            Voice[] availableVoices = engineModeDesc.getVoices();
            for (Voice voice1 : availableVoices) {
                if (voice1.getName().equals(voiceName)) {
                    voice = voice1;
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
        }
    }

    /**
     * Converts text to speech and speaks it.
     *
     * @param text The text to be spoken.
     * @throws EngineException       if an error occurs while speaking the text.
     * @throws AudioException        if there is an audio-related error.
     * @throws IllegalArgumentException if the input text is invalid.
     * @throws InterruptedException   if the speaking process is interrupted.
     */
    public void speak(String text) throws EngineException, AudioException, IllegalArgumentException, InterruptedException {
        synthesizer.speakPlainText(text, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    }

    /**
     * Terminates the TextToSpeech engine.
     *
     * @throws EngineException    if an error occurs while terminating the engine.
     * @throws EngineStateError if there is a state-related error with the engine.
     */
    public void terminate() throws EngineException, EngineStateError {
        synthesizer.deallocate();
    }
}
