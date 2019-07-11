package ru.stairenx.wellsofttestapp.item;

public class LoginItem {

    String login;
    String password;

    public LoginItem(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
