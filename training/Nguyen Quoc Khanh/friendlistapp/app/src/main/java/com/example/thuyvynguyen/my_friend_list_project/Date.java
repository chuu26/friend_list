package com.example.thuyvynguyen.my_friend_list_project;

public class Date {

    public int day;
    public int month;
    public int year;

    //Insert argument into date
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //Default date when no argument is 1/1/2000
    public Date() {
        day = 1;
        month = 1;
        year = 2000;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String toString(){
        return day + "/" + month + "/" + year;
    }

    //Check whether the date is birthday
    public boolean isBirthDay(Date date) {
        if (day == date.day && month == date.month && year == date.year) {
            return true;
        }
        return false;
    }
}