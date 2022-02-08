package com.quiz.proquiz;

public class Users {
     String userName;
    String gmail;
    String password;
    String mobile;
    String profilepic;
    long coins = 100;

    public Users(){

    }

    public Users(String userName, String gmail, String password, String mobile) {
        this.userName = userName;
        this.gmail = gmail;
        this.password = password;
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public String getGmail() {
        return gmail;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
