package com.example.thuyvynguyen.my_friend_list_project;

public class Friend {

    private Date date;
    private Person person;
    private String sex;//sex 2 kinds of value: male/female

    public Friend(Person person, Date date, String sex) {
        this.date = date;
        this.person = person;
        this.sex = sex;
    }

    public String toString(){
        return person.toString() + "\n" +
               "Date of birth: " + date.toString() + "\n" +
                "sex: " + sex;
    }

    public Person getPerson(){
        return person;
    }

    public String getSex(){
        return sex;
    }


}