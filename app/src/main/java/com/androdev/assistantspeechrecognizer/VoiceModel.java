package com.androdev.assistantspeechrecognizer;

import java.util.ArrayList;
import java.util.List;

class VoiceModel {
    private List<String> appCommands = new ArrayList<>();
    private List<String> callCommands = new ArrayList<>();
    private List<String> weatherCommands = new ArrayList<>();
    private List<String> clockAlarmCommands = new ArrayList<>();
    private List<String> clockTimerCommands = new ArrayList<>();
    private List<String> musicRelatedCommands = new ArrayList<>();
    private List<String> cameraRelatedCommands = new ArrayList<>();

    List<String> getCameraRelatedCommands() {
        final String CAMERA_COMMAND_1 = "scan this qr code";
        final String CAMERA_COMMAND_2 = "scan this barcode";
        final String CAMERA_COMMAND_3 = "scan qr code";
        final String CAMERA_COMMAND_4 = "scan barcode";

        cameraRelatedCommands.add(CAMERA_COMMAND_1);
        cameraRelatedCommands.add(CAMERA_COMMAND_2);
        cameraRelatedCommands.add(CAMERA_COMMAND_3);
        cameraRelatedCommands.add(CAMERA_COMMAND_4);

        return cameraRelatedCommands;
    }

    List<String> getMusicRelatedCommands() {
        final String MUSIC_COMMAND_1 = "check out this tune";
        final String MUSIC_COMMAND_2 = "what's this tune";
        final String MUSIC_COMMAND_3 = "what is this tune";
        final String MUSIC_COMMAND_4 = "which song is this";
        final String MUSIC_COMMAND_5 = "what's this song called";
        final String MUSIC_COMMAND_6 = "what is this song called";
        final String MUSIC_COMMAND_7 = "check out the music";
        final String MUSIC_COMMAND_8 = "what is this music";
        final String MUSIC_COMMAND_9 = "what's this music";
        final String MUSIC_COMMAND_10 = "can you recognize this music";
        final String MUSIC_COMMAND_11 = "do you recognize this music";
        final String MUSIC_COMMAND_12 = "can you recognize this song";
        final String MUSIC_COMMAND_13 = "do you recognize this song";

        musicRelatedCommands.add(MUSIC_COMMAND_1);
        musicRelatedCommands.add(MUSIC_COMMAND_2);
        musicRelatedCommands.add(MUSIC_COMMAND_3);
        musicRelatedCommands.add(MUSIC_COMMAND_4);
        musicRelatedCommands.add(MUSIC_COMMAND_5);
        musicRelatedCommands.add(MUSIC_COMMAND_6);
        musicRelatedCommands.add(MUSIC_COMMAND_7);
        musicRelatedCommands.add(MUSIC_COMMAND_8);
        musicRelatedCommands.add(MUSIC_COMMAND_9);
        musicRelatedCommands.add(MUSIC_COMMAND_10);
        musicRelatedCommands.add(MUSIC_COMMAND_11);
        musicRelatedCommands.add(MUSIC_COMMAND_12);
        musicRelatedCommands.add(MUSIC_COMMAND_13);

        return musicRelatedCommands;
    }

    List<String> getAppCommands() {
        final String LAUNCH_APP_COMMAND_1 = "open";
        final String LAUNCH_APP_COMMAND_2 = "launch";

        appCommands.add(LAUNCH_APP_COMMAND_1);
        appCommands.add(LAUNCH_APP_COMMAND_2);

        return appCommands;
    }

    List<String> getCallCommands() {
        final String CALL_COMMAND_1 = "call";

        callCommands.add(CALL_COMMAND_1);

        return callCommands;
    }

    List<String> getWeatherCommands() {
        final String WEATHER_COMMAND_1 = "do i need an umbrella today";
        final String WEATHER_COMMAND_2 = "how's the weather today";
        final String WEATHER_COMMAND_3 = "what's the weather today";
        final String WEATHER_COMMAND_4 = "how's the weather";
        final String WEATHER_COMMAND_5 = "what's the weather";

        weatherCommands.add(WEATHER_COMMAND_1);
        weatherCommands.add(WEATHER_COMMAND_2);
        weatherCommands.add(WEATHER_COMMAND_3);
        weatherCommands.add(WEATHER_COMMAND_4);
        weatherCommands.add(WEATHER_COMMAND_5);

        return weatherCommands;
    }

    List<String> getClockAlarmCommands() {
        final String CLOCK_COMMAND_1 = "set an alarm for";
        final String CLOCK_COMMAND_2 = "wake me up at";

        clockAlarmCommands.add(CLOCK_COMMAND_1);
        clockAlarmCommands.add(CLOCK_COMMAND_2);

        return clockAlarmCommands;
    }

    List<String> getClockTimerCommands() {
        final String CLOCK_COMMAND_1 = "set a timer for";

        clockAlarmCommands.add(CLOCK_COMMAND_1);

        return clockTimerCommands;
    }
}
