package kr.smobile.personaAI.base;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.VisibleForTesting;
import androidx.multidex.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.smobile.personaAI.model.AuthResponse;
import kr.smobile.personaAI.model.User;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by bek on 14/08/2017.
 */

public class PreferencesManager implements PreferencesHelper {

    @Override
    public AuthResponse getAccessToken() {
        return new Gson().fromJson((String) getValue(String.class, Key.accessToken, ""), AuthResponse.class);
    }

    @Override
    public void setAccessToken(String accessToken) {
        setValue(Key.accessToken, accessToken);
    }

    @Override
    public Boolean isSignedIn() {
        return (Boolean) getValue(Boolean.class, Key.signed, false);
    }

    @Override
    public void setSignedIn(boolean signedIn) {
        setValue(Key.signed, signedIn);
    }

    @Override
    public String getUserId() {
        return (String) getValue(String.class, Key.user_id, null);
    }

    @Override
    public void setUserId(String userId) {
        setValue(Key.user_id, userId);
    }

    @Override
    public User getUser() {
        return new Gson().fromJson((String) getValue(String.class, Key.user, ""), User.class);
    }

    @Override
    public void setUser(User user) {
        setValue(Key.user, new Gson().toJson(user));
    }

    public enum Key {
        accessToken, signed, device_token, user, user_id, popup_day, WIDTH
    }

    @VisibleForTesting
    public static final String PREF_NAME = BuildConfig.APPLICATION_ID + ".local";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    @Inject
    public PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    @SuppressWarnings("unchecked")
    public void setValue(Key key, Object value) {
        if (value.getClass().equals(String.class)) {
            mPref.edit().putString(key.name(), (String) value).apply();
        } else if (value.getClass().equals(Integer.class)) {
            mPref.edit().putInt(key.name(), (Integer) value).apply();
        } else if (value.getClass().equals(Float.class)) {
            mPref.edit().putFloat(key.name(), (Float) value).apply();
        } else if (value.getClass().equals(Boolean.class)) {
            mPref.edit().putBoolean(key.name(), (Boolean) value).apply();
        } else if (value.getClass().equals(Long.class)) {
            mPref.edit().putLong(key.name(), (Long) value).apply();
        } else if (value.getClass().equals(new TypeToken<Set<String>>() {
        }.getClass())) {
            mPref.edit().putStringSet(key.name(), (Set<String>) value).apply();
        } else {
            assert true;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Object getValue(Class<T> aClass, Key key, Object defaultValue) {

        if (aClass.equals(String.class)) {
            return mPref.getString(key.name(), (String) defaultValue);
        } else if (aClass.equals(Integer.class)) {
            return mPref.getInt(key.name(), (Integer) defaultValue);
        } else if (aClass.equals(Float.class)) {
            return mPref.getFloat(key.name(), (Float) defaultValue);
        } else if (aClass.equals(Boolean.class)) {
            return mPref.getBoolean(key.name(), (Boolean) defaultValue);
        } else if (aClass.equals(Long.class)) {
            return mPref.getLong(key.name(), (Long) defaultValue);
        } else if (aClass.equals(new TypeToken<Set<String>>() {
        }.getClass())) {
            return mPref.getStringSet(key.name(), (Set<String>) defaultValue);
        } else {
            assert true;
            return null;
        }
    }

    public void remove(Key key) {
        mPref.edit().remove(key.name()).apply();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

}
