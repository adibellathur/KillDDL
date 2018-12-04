package com.team27.killddl.data;

public class User {

    private String name;
    private String username;
    private String password;


    public User(String n, String us, String pw){
        name = n;
        username = us;
        password = pw;
    }

    public User(String us, String pw){
        username = us;
        password = pw;
        name = "No name";
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String n){
        name = n;
    }

    public void setUsername(String n){
        username = n;
    }

    public void setPassword(String n){
        password = n;
    }
}
