package com.example.android_ordering;
import java.util.ArrayList;
import java.util.List;
public class User {
    private String id;
    private String email;
    private String usrname;
    private String passwword;
    private String accountype;
    private String sex;
    private String address;
    private List<Shoppingitem>cartlist;

    public User(){}

    public User(String email, String usrname, String passwword, String accountype, String sex, String address, List<Shoppingitem> cartlist) {
        this.email = email;
        this.usrname = usrname;
        this.passwword = passwword;
        this.accountype = accountype;
        this.sex = sex;
        this.address = address;
        this.cartlist = cartlist;
    }


    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getPasswword() {
        return passwword;
    }

    public void setPasswword(String passwword) {
        this.passwword = passwword;
    }

    public String getAccountype() {
        return accountype;
    }

    public void setAccountype(String accountype) {
        this.accountype = accountype;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Shoppingitem> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<Shoppingitem> cartlist) {
        this.cartlist = cartlist;
    }
}
