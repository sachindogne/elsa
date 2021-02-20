package com.androdev.assistantspeechrecognizer;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

class TextToSpeechProcessor {

    private TextToSpeech tts;

    TextToSpeechProcessor (Context context, final String speechString) {
        TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.d("TTS", "onInit: This language is not supported");
                    } else {
                        speakOut(speechString);
                    }
                } else {
                    Log.d("TTS", "onInit: Initialization failed");
                }
            }
        };
        tts = new TextToSpeech(context, listener);
    }

    private void speakOut(String speechString) {
        if (TextToSpeech.ERROR == tts.speak(speechString, TextToSpeech.QUEUE_FLUSH, null, null)) {
            Log.d("TTS", "speakOut: Error");
        }
    }
}
