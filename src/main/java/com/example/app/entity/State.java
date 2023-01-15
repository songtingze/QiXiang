package com.example.app.entity;

public class State {

    private boolean selected;
    private String seq;
    private String userName;
    private String password;
    private String address;
    private String state;

    public State(boolean selected,String seq,String address,String state,String userName,String password){
        this.seq=seq;
        this.userName=userName;
        this.password=password;
        this.address=address;
        this.selected=selected;
        this.state = state;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}
