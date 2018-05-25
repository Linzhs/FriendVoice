package com.linzh.android.newfriendvoice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by linzh on 2018/3/25.
 */

public final class VoiceUtils {

    private static final String TAG = "VoiceUtils";

    private static final Map<String, String> VOICE_CODE = new HashMap<>();

    private static TextToSpeech mTextToSpeech;

    static {
        VOICE_CODE.put("10", "你");
        VOICE_CODE.put("11", "大家");
        VOICE_CODE.put("12","我");
        VOICE_CODE.put("13", "他");
        VOICE_CODE.put("14", "们");
        VOICE_CODE.put("20", "来自");
        VOICE_CODE.put("21", "要");
        VOICE_CODE.put("22", "请");
        VOICE_CODE.put("23", "帮助");
        VOICE_CODE.put("24", "谢谢");
        VOICE_CODE.put("25", "抱歉");
        VOICE_CODE.put("26", "是");
        VOICE_CODE.put("27", "可以");
        VOICE_CODE.put("28", "不");
        VOICE_CODE.put("29", "见到");
        VOICE_CODE.put("30", "喜欢");
        VOICE_CODE.put("31", "在");
        VOICE_CODE.put("32", "吃");
        VOICE_CODE.put("40", "好");
        VOICE_CODE.put("41", "吗");
        VOICE_CODE.put("42", "开心");
        VOICE_CODE.put("43", "漂亮");
        VOICE_CODE.put("44", "什么");
        VOICE_CODE.put("45", "一点");
        VOICE_CODE.put("46", "能");
        VOICE_CODE.put("47", "吗");
        VOICE_CODE.put("50", "友声");
        VOICE_CODE.put("51", "团队");
        VOICE_CODE.put("52", "吃饭");
        VOICE_CODE.put("53", "吃饭");
        VOICE_CODE.put("54", "喝水");
        VOICE_CODE.put("55", "地方");
        VOICE_CODE.put("56", "上课");
    }

    private VoiceUtils() {
        // Nothing to do...
    }

    public static void initVoiceSetting(Context context) {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=" + AppConstants.SPEECH_APPID);

        if (mTextToSpeech == null) {
            mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        mTextToSpeech.setLanguage(Locale.CHINA);
                    }
                }
            });
        }
    }

    public static void stopTextToSpeech() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    public static void speak(String content) {
        if (!TextUtils.isEmpty(content)) {
            mTextToSpeech.speak(content, TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    public static void updateVoiceCode(Context context) {
        // 检查编码xml文件是否存在，不存在则默认写入
        String fileName = AppConstants.PREF_NAME;
        String dir = AppConstants.DATA_URL + context.getPackageName() +
                AppConstants.SHARED_PREFS_URL + fileName + ".xml";
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        if (CommonUtils.fileExist(dir)) {// 存在  SharedPreferences -> VOICE_COE
            SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            Set<String> keySet = preferences.getAll().keySet();
            VOICE_CODE.clear();
            for (String key : keySet) {
                if (!key.startsWith("PREF_KEY")) {
                    VOICE_CODE.put(key, preferences.getString(key, ""));
                }
            }
        } else { // VOICE_COE -> SharedPreferences
            Set<String> keySet = VOICE_CODE.keySet();
            for (String key : keySet) {
                editor.putString(key, VOICE_CODE.get(key));
            }
        }
        editor.apply();
        AppLogger.d(TAG, "loading voice code succeed");
    }

    public static Map<String, String> getVoiceCodes() {
        return VOICE_CODE;
    }

    public static void setVoiceCode(String key, String value) {
        VOICE_CODE.put(key, value);
    }

    public static String getVoiceValue(String key) {
        return VOICE_CODE.get(key);
    }
}
