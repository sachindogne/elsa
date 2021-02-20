package com.androdev.assistantspeechrecognizer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import static android.speech.SpeechRecognizer.ERROR_AUDIO;
import static android.speech.SpeechRecognizer.ERROR_CLIENT;
import static android.speech.SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS;
import static android.speech.SpeechRecognizer.ERROR_NETWORK;
import static android.speech.SpeechRecognizer.ERROR_NETWORK_TIMEOUT;
import static android.speech.SpeechRecognizer.ERROR_NO_MATCH;
import static android.speech.SpeechRecognizer.ERROR_RECOGNIZER_BUSY;
import static android.speech.SpeechRecognizer.ERROR_SERVER;
import static android.speech.SpeechRecognizer.ERROR_SPEECH_TIMEOUT;


public class MainActivity extends Activity {

    public TextView speechText;
    private LottieAnimationView animationView;

    private SpeechRecognizer speech;

    Context context;

    private OperationsOnResult operationsOnResult = new OperationsOnResult();
    private String speechResult;
    private List<String> resultList = new ArrayList<>();

    private List<String> cameraCommands = new ArrayList<>();
    private VoiceModel voiceModel = new VoiceModel();

    final int PERMISSION_REQUEST_CODE = 100;
    final int BARCODE_REQUEST_CODE = 200;
//    final int PERMISSION_REQUEST_CODE_RECORD_AUDIO = 100;
//    final int PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION = 200;
//    final int PERMISSION_REQUEST_CODE_ACCESS_COARSE_LOCATION = 300;
//    final int PERMISSION_REQUEST_CODE_READ_CONTACTS = 400;
//    final int PERMISSION_REQUEST_CODE_READ_CALENDAR = 500;
//    final int PERMISSION_REQUEST_CODE_READ_SMS = 600;
//    final int PERMISSION_REQUEST_CODE_SET_ALARM = 700;

    private String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.READ_SMS,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        speechText = findViewById(R.id.speechTextView);

        animationView = findViewById(R.id.animation_view);
        animationView.setRepeatCount(0);
        animationView.setSpeed(0.85f);
        animationView.setMinAndMaxProgress(0.45f, 0.9f);

//        for (int i = 0 ; i < permissionsLength ; i++) {
//            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[] {permissions[i]}, PERMISSION_REQUEST_CODE);
//            }
//        }

        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[2]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[3]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[4]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[5]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[6]) != PackageManager.PERMISSION_GRANTED
                /*& ActivityCompat.checkSelfPermission(this, permissions[7]) != PackageManager.PERMISSION_GRANTED*/) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
                } else {
                    speechText.setText("");
                    animationView.playAnimation();
                    speechPrompt();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                speechText.post(new Runnable() {
                    @Override
                    public void run() {
                        speechText.setText(barcode.displayValue);
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[2]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[3]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[4]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[5]) != PackageManager.PERMISSION_GRANTED
                & ActivityCompat.checkSelfPermission(this, permissions[6]) != PackageManager.PERMISSION_GRANTED
                /*& ActivityCompat.checkSelfPermission(this, permissions[7]) != PackageManager.PERMISSION_GRANTED*/) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void speechPrompt() {
        /*final SpeechRecognizer */speech = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("Assistant", "onBeginningOfSpeech()");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                Log.d("Assistant", "onEndOfSpeech()");
            }

            @Override
            public void onError(int i) {
                switch (i) {
                    case ERROR_AUDIO:
                        speechText.setText(R.string.speech_error_audio);

                    case ERROR_CLIENT:
                        speechText.setText(R.string.speech_error_client);

                    case ERROR_INSUFFICIENT_PERMISSIONS:
                        speechText.setText(R.string.speech_error_insufficient_permissions);

                    case ERROR_NETWORK:
                        speechText.setText(R.string.speech_error_network);

                    case ERROR_NETWORK_TIMEOUT:
                        speechText.setText(R.string.speech_error_network_timeout);

                    case ERROR_NO_MATCH:
                        speechText.setText(R.string.speech_error_no_match);

                    case ERROR_RECOGNIZER_BUSY:
                        speechText.setText(R.string.speech_error_recognizer_busy);

                    case ERROR_SERVER:
                        speechText.setText(R.string.speech_error_server);

                    case ERROR_SPEECH_TIMEOUT:
                        speechText.setText(R.string.speech_error_speech_timeout);
                }
            }

            @Override
            public void onResults(Bundle bundle) {
                resultList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (resultList != null && !resultList.isEmpty() && resultList.get(0) != null) {
                    speechResult = resultList.get(0);
                    speechText.setText(speechResult);

                    cameraCommands = voiceModel.getCameraRelatedCommands();
                    int cameraCommandsSize = cameraCommands.size();
                    boolean flag = false;

                    for (int i = 0 ; i < cameraCommandsSize ; i++) {
                        if (cameraCommands.get(i).equalsIgnoreCase(speechResult)) {
                            new TextToSpeechProcessor(context, "Looking for QR code");
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                        startActivityForResult(intent, BARCODE_REQUEST_CODE);
                    } else if (speechResult.equalsIgnoreCase("activate vision")) {
                        new TextToSpeechProcessor(context, "Activating vision");
                        Intent intent = new Intent(MainActivity.this, VisionActivity.class);
                        startActivity(intent);
                    } else if (speechResult.equalsIgnoreCase("read this text")) {
                        Intent intent = new Intent(MainActivity.this, OcrActivity.class);
                        startActivity(intent);
                    } else
                        operationsOnResult.processResults(speechResult, context, speechText);
                } else {
                    speechText.setText(R.string.try_again);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };
        speech.setRecognitionListener(listener);
        speech.startListening(intent);
    }
}
