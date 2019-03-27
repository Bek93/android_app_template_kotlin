package net.wepla.campus_planet.base;

import net.wepla.campus_planet.model.AuthResponse;
import net.wepla.campus_planet.model.Profile;

public interface PreferencesHelper {


    AuthResponse getAccessToken();

    void setAccessToken(String accessToken);

    Boolean isSignedIn();

    void setSignedIn(boolean signedIn);

    Profile getUser();

    void setUser(Profile user);

}
