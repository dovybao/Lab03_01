package com.example.vybao.lab03_01;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;

    public Contact(){

    }
    public Contact(String name,String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public Contact(int id,String name, String phoneNumber){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    //Get,Set ID
    public void setContactId(int id){
        this.id = id;
    }
    public int getContactId(){
        return id;
    }
    //Get,Set name
    public void setContactName(String name){
        this.name = name;
    }
    public String getContactName(){
        return name;
    }
    //Get,Set phoneNumber
    public void setContactPhone(String phone){
        this.phoneNumber = phone;
    }
    public String getContactPhone(){
        return phoneNumber;
    }

    @Override
    public String toString() {

        return (this.id +": " + this.name + " " + this.phoneNumber);
    }
}
