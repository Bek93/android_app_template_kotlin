package kr.smobile.personaAI.utils;


import kr.smobile.personaAI.view.indepth.PostLikedUserListActivity;
import kr.smobile.personaAI.view.indepth.FindUserActivity;
import kr.smobile.personaAI.view.main.MainActivity;

public enum Intented {

    ToFindUserActivity(FindUserActivity.class.getName()),
    ToPostLikedByUserActivity(PostLikedUserListActivity.class.getName()),
    ToMainActivity(MainActivity.class.getName());

   /* ToLoginActivity(LoginActivity.class.getName()),
    ToForgetActivity(ResetPasswordActivity.class.getName()),
    ToMainActivity(MainActivity.class.getName()),
    ToRegistrationActivity(RegistrationActivity.class.getName()),
    ToProfileInfoActivity(ProfileInfoActivity.class.getName()),
    ToProfileIndepthActivity(ProfileIndepthActivity.class.getName()),
    ToCartIndepthActivity(CartIndepthActivity.class.getName());*/

    private String name;

    Intented(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
