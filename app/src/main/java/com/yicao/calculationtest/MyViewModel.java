package com.yicao.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private static final String KEY_HIGH_SCORE = "key_high_score";
    private static final String KEY_LEFT_NUMBER = "key_left_number";
    private static final String KEY_RIGHT_NUMBER = "key_right_number";
    private static final String KEY_OPERATOR = "key_operator";
    private static final String KEY_ANSWER = "key_answer";
    private static final String SAVE_SHP_DATA_NAME = "calculation";
    private static final String KEY_CURRENT_SCORE = "key_current_current";
    boolean win_flag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0));
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(KEY_ANSWER);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    void generate(){
        int level = 40;
        Random random = new Random();
        int x = random.nextInt(level) + 1;
        int y = random.nextInt(level) + 1;
        int z = random.nextInt(2);
        if (z % 2 == 0) {
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_LEFT_NUMBER, x);
            handle.set(KEY_RIGHT_NUMBER, y);
            handle.set(KEY_ANSWER, x + y);
        } else {
            handle.set(KEY_OPERATOR, "-");
            if (x > y) {
                handle.set(KEY_LEFT_NUMBER, x);
                handle.set(KEY_RIGHT_NUMBER, y);
                handle.set(KEY_ANSWER, x - y);
            } else {
                handle.set(KEY_LEFT_NUMBER, y);
                handle.set(KEY_RIGHT_NUMBER, x);
                handle.set(KEY_ANSWER, y - x);
            }
        }
    }

    void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generate();
    }

    void save(){
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shp.edit();
        edit.putInt(KEY_HIGH_SCORE, getHighScore().getValue());
        edit.apply();
    }
}
