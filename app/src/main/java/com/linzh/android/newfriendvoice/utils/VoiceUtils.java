package com.linzh.android.newfriendvoice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import com.linzh.android.newfriendvoice.ui.main.MainActivity;

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
        VOICE_CODE.put("你", "10");
        VOICE_CODE.put("大家", "11");
        VOICE_CODE.put("我", "12");
        VOICE_CODE.put("他", "13");
        VOICE_CODE.put("们", "14");
        VOICE_CODE.put("来自", "20");
        VOICE_CODE.put("要", "21");
        VOICE_CODE.put("请", "22");
        VOICE_CODE.put("帮助", "23");
        VOICE_CODE.put("谢谢", "24");
        VOICE_CODE.put("抱歉", "25");
        VOICE_CODE.put("是", "26");
        VOICE_CODE.put("可以", "27");
        VOICE_CODE.put("不", "28");
        VOICE_CODE.put("见到", "29");
        VOICE_CODE.put("喜欢", "30");
        VOICE_CODE.put("在", "31");
        VOICE_CODE.put("吃", "32");
        VOICE_CODE.put("好", "40");
        VOICE_CODE.put("吗", "41");
        VOICE_CODE.put("开心", "42");
        VOICE_CODE.put("漂亮", "43");
        VOICE_CODE.put("什么", "44");
        VOICE_CODE.put("一点", "45");
        VOICE_CODE.put("能", "46");
        VOICE_CODE.put("吗", "47");
        VOICE_CODE.put("友声", "50");
        VOICE_CODE.put("团队", "51");
        VOICE_CODE.put("吃饭", "52");
        VOICE_CODE.put("吃饭", "53");
        VOICE_CODE.put("喝水", "54");
        VOICE_CODE.put("地方", "55");
        VOICE_CODE.put("上课", "56");
    }

    private VoiceUtils() {
        // Nothing to do...
    }

    public static void initVoiceSetting(Context context) {
        mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    mTextToSpeech.setLanguage(Locale.CHINA);
                }
            }
        });

        // 检查编码xml文件是否存在，不存在则默认写入
        String fileName = AppConstants.PREF_NAME;
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        Set<String> keySet = VOICE_CODE.keySet();
        if (CommonUtils.fileExist(fileName)) {// 存在
            SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            VOICE_CODE.clear();
            for (String key : keySet) {
                if (!key.startsWith("PREF_KEY")) {
                    VOICE_CODE.put(key, preferences.getString(key, ""));
                }
            }
        } else {
            for (String key : keySet) {
                editor.putString(key, VOICE_CODE.get(key));
            }
        }
        editor.apply();
        AppLogger.d(TAG, "loading voice code succeed");
    }

    public static void speak(String content) {
        if (!TextUtils.isEmpty(content)) {
            mTextToSpeech.speak(content, TextToSpeech.QUEUE_ADD, null, null);
        }
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
