package me.comphack.playerlogger.utils;

public class PlayerChat {

    private String message;
    private String username;
    private String dateTime;

    public PlayerChat(String message, String username, String dateTime) {
        this.dateTime = dateTime;
        this.message = message;
        this.username = username;
    }


    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getDateTime() {
        return dateTime;
    }
}
