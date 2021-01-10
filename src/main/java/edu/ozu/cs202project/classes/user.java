package edu.ozu.cs202project.classes;

import java.util.Date;

public class user {
    public Integer user_id;
    public String name;
    public String surname;
    public String phone_number;
    public String birthdate;
    public String user_name;
    public Integer user_type;

    public user(Integer id, String name, String surname, String user_name, String phone_number, String birthdate, Integer type) {
        this.user_id = id;
        this.name = name;
        this.surname = surname;
        this.user_name = user_name;
        this.phone_number = phone_number;
        this.birthdate = birthdate;
        this.user_type = type;
    }

}
