package com.baymax.baymax;

import java.io.Serializable;

public class User implements Serializable{

    public String U_id;
    public String First_Name;
    public String Last_Name;
    public String Age;
    public String Gender;




  /*  public String getU_id() {
        return U_id;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }*/

    public User(){

    }

    public User(String id,String F,String L,String a,String g){
        U_id=id;
        First_Name=F;
        Last_Name=L;
        Age=a;
        Gender=g;
    }

   /* public String getUname() {
        return User_name;
    }

    public void setUname(String uname) {
        User_name = uname;
    }

    public String getUsomething() {
        return User_something;
    }

    public void setUsomething(String usomething) {
        User_something= usomething;
    }*/
}
