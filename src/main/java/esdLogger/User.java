/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esdLogger;

/**
 *
 * @author gchaim
 */
public class User {
    String name;
    String user_num;
    String card1;
    String card2;

    public User() {
        this.name = "Test";
        this.user_num = "00";
        this.card1 = "0";
        this.card2 = "0";
    }
    
    public User(String name,String user_num, String card1,String card2) {
        this.name = name;
        this.user_num = user_num;
        this.card1 = card1;
        this.card2 = card2; 
    }

    public String getName() {
        return name;
    }

    public String getUser_num() {
        return user_num;
    }

    public String getCard1() {
        return card1;
    }

    public String getCard2() {
        return card2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public void setCard1(String card1) {
        this.card1 = card1;
    }

    public void setCard2(String card2) {
        this.card2 = card2;
    }

    @Override
    public String toString() {
        return user_num+": "+name+" "+card1+" "+card2;
    }

    public boolean equals(User another) {
        if(this.name.equals(another.name)){
            if(this.user_num.equals(another.user_num)){
                return true;
            }
        }
        return false;
    }    
}
