package com.androdev.assistantspeechrecognizer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OperationsOnResult {

    private String appName;
    private String appLabel;

    private VoiceModel voiceModel = new VoiceModel();

    void processResults(String speechResult, Context context, final TextView speechText) {
        Log.d("Assistant", "processResults: " + speechResult);

        List<String> appCommands;
        List<String> callCommands;
        List<String> weatherCommands;
        List<String> clockAlarmCommands;
        List<String> clockTimerCommands;
        List<String> musicRelatedCommands;

        appCommands = voiceModel.getAppCommands();
        callCommands = voiceModel.getCallCommands();
        weatherCommands = voiceModel.getWeatherCommands();
        clockAlarmCommands = voiceModel.getClockAlarmCommands();
        clockTimerCommands = voiceModel.getClockTimerCommands();
        musicRelatedCommands = voiceModel.getMusicRelatedCommands();

        int opCode = 0;
        String contact = "";

        if (appCommands != null && callCommands != null && weatherCommands != null && clockAlarmCommands != null
                && clockTimerCommands != null && musicRelatedCommands != null) {

            int appCommandsSize = appCommands.size();
            int callCommandsSize = callCommands.size();
            int weatherCommandsSize = weatherCommands.size();
            int clockAlarmCommandsSize = clockAlarmCommands.size();
            int clockTimerCommandsSize = clockTimerCommands.size();
            int musicRelatedCommandsSize = musicRelatedCommands.size();

            for (int i = 0; i < appCommandsSize; i++) {
                if (speechResult.startsWith(appCommands.get(i))) {
                    String appCommand = appCommands.get(i);
                    appName = speechResult.substring(appCommand.length() + 1, speechResult.length());

                    appLabel = appName.substring(0, 1).toUpperCase() + appName.substring(1);

                    Log.d("processResults", "processResults: " + appName);
                    opCode = 1;
                    break;
                }
            }

            for (int i = 0; i < callCommandsSize; i++) {
                if (speechResult.startsWith(callCommands.get(i))) {
                    String callCommand = callCommands.get(i);
                    if (callCommand.length() != speechResult.length()) {
                        contact = speechResult.substring(callCommand.length() + 1, speechResult.length());
                        opCode = 2;
                        break;
                    }
                }
            }

            for (int i = 0; i < weatherCommandsSize; i++) {
                if (speechResult.equalsIgnoreCase(weatherCommands.get(i))) {
                    opCode = 3;
                    break;
                }
            }

            for (int i = 0; i < clockAlarmCommandsSize; i++) {
                if (speechResult.startsWith(clockAlarmCommands.get(i))) {
                    Pattern pattern = Pattern.compile("[\\d]{1,2}:[\\d]{1,2} | [\\d]{1,2}");
                    Matcher matcher = pattern.matcher(speechResult);

                    int hour;
                    int minutes;

                    while (matcher.find()) {
                        if (speechResult.contains(":")) {
                            hour = Integer.parseInt(speechResult.substring(matcher.start(), speechResult.indexOf(":")));
                            minutes = Integer.parseInt(speechResult.substring(speechResult.indexOf(":") + 1, speechResult.indexOf(":") + 3));
                        } else {
                            hour = Integer.parseInt(speechResult.substring(matcher.start(), matcher.end()));
                            minutes = 0;
                        }

                        if (speechResult.contains("p.m.") || speechResult.contains("pm") ||
                                speechResult.contains("p.m") || speechResult.contains("pm.")) {
                            hour = hour + 12;
                            if (hour >= 24) {
                                hour = 0;
                            }
                        }
                        Log.d("alarm", "processResults: " + hour);
                        Log.d("alarm", "processResults: " + minutes);
                        setAlarm(hour, minutes, context);
                    }
                    break;
                }
            }

            for (int i = 0; i < clockTimerCommandsSize; i++) {
                if (speechResult.startsWith(clockTimerCommands.get(i))) {
                    int seconds = 0;

                    setTimer(seconds, context);
                    break;
                }
            }

            for (int i = 0; i < musicRelatedCommandsSize; i++) {
                if (speechResult.equalsIgnoreCase(musicRelatedCommands.get(i))) {
                    opCode = 4;
                    break;
                }
            }

            /*if (speechResult.equalsIgnoreCase("turn off the phone")
                    || speechResult.equalsIgnoreCase("switch off the phone")) {
                BasicTasks.shutdownPhone(context);
                opCode = 5;
            } else if (speechResult.equalsIgnoreCase("reboot the phone")
                    || speechResult.equalsIgnoreCase("restart the phone")) {
                BasicTasks.rebootPhone(context);
                opCode = 5;
            } else */if (speechResult.equalsIgnoreCase("turn on wifi")
                    || speechResult.equalsIgnoreCase("turn on wi-fi")) {
                BasicTasks.changeWifiState(context, true);
                opCode = 5;
            } else if (speechResult.equalsIgnoreCase("turn off wifi")
                    || speechResult.equalsIgnoreCase("turn off wi-fi")) {
                BasicTasks.changeWifiState(context, false);
                opCode = 5;
            } else if (speechResult.equalsIgnoreCase("turn on bluetooth")) {
                BasicTasks.changeBluetoothState(context,true);
                opCode = 5;
            } else if (speechResult.equalsIgnoreCase("turn off bluetooth")) {
                BasicTasks.changeBluetoothState(context,false);
                opCode = 5;
            } else if (speechResult.contains("airplane mode")) {
                BasicTasks.changeAirplaneModeState(context);
                opCode = 5;
            } else if (speechResult.equalsIgnoreCase("open wifi settings")
                    || speechResult.equalsIgnoreCase("open wi-fi settings")
                    || speechResult.equalsIgnoreCase("show wifi settings")
                    || speechResult.equalsIgnoreCase("show wi-fi settings")) {
                BasicTasks.openWifiSettings(context);
                opCode = 5;
            } else if (speechResult.equalsIgnoreCase("open bluetooth settings")
                    || speechResult.equalsIgnoreCase("show bluetooth settings")) {
                BasicTasks.openBluetoothSettings(context);
                opCode = 5;
            }

            switch (opCode) {
                case 1:
                    launchApp(appLabel, context);
                    break;

                case 2:
                    callContact(contact, context);
                    break;

                case 3:
                    showWeather();
                    break;

                case 4:
                    showMusicDetails();
                    break;

                case 5:
                    break;

                default:
                    webSearch(speechResult, context);
                    Log.d("web search", "processResults: default case");
                    break;
            }
        }
    }

    private void launchApp(String appLabel, Context context) {
        // this keyword provides application context
        Intent intent;
        ApplicationInfo applicationInfo = null;
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        int packagesSize = packages.size();
        boolean checkInstalledApp = false;

        for (int i = 0; i < packagesSize; i++) {
            applicationInfo = new ApplicationInfo(packages.get(i));
            String installedAppLabel = applicationInfo.loadLabel(packageManager).toString();

            Log.d("launchApp", "launchApp: " + installedAppLabel);

            if (/*installedAppLabel.contains(appLabel) & */installedAppLabel.equalsIgnoreCase(appLabel)/* &
                    !installedAppLabel.startsWith("com.") &&
                    applicationInfo.flags != ApplicationInfo.FLAG_SYSTEM &&
                    applicationInfo.flags == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP*/) {
                checkInstalledApp = true;
                new TextToSpeechProcessor(context, "opening " + appLabel);
                Log.d("launchApp", "launchApp: " + installedAppLabel);
                break;
            }
        }

        if (checkInstalledApp) {
            intent = context.getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            new TextToSpeechProcessor(context, "searching on play store for " + appLabel);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://search?q=" + appName));
            context.startActivity(intent);
        }
    }


    private void callContact(String contact, Context context) {
        Uri callUri = Uri.parse("tel:" + contact);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, callUri);
        context.startActivity(callIntent);
    }

    private void showWeather() {

    }

    private void setAlarm(int hour, int minutes, Context context) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm")
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        //if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        //}
    }

    private void setTimer(int seconds, Context context) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    private void showMusicDetails() {

    }

    private void webSearch(String speechResult, Context context) {
        Log.d("web search", "webSearch: uri action");
        Uri webSearchUri = Uri.parse("https://www.google.com/search?q=" + speechResult);
        Intent intent = new Intent(Intent.ACTION_VIEW, webSearchUri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
