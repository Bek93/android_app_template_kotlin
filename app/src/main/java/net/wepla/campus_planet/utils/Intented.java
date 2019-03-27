package net.wepla.campus_planet.utils;


public enum Intented {
    test("test");

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
