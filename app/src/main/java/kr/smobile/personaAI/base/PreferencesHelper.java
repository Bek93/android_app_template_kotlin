package kr.smobile.personaAI.base;

import kr.smobile.personaAI.model.AuthResponse;
import kr.smobile.personaAI.model.User;

public interface PreferencesHelper {


    AuthResponse getAccessToken();

    void setAccessToken(String accessToken);

    Boolean isSignedIn();

    void setSignedIn(boolean signedIn);

    String getUserId();

    void setUserId(String userId);

    User getUser();

    void setUser(User user);

}
