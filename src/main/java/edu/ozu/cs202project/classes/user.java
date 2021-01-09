package edu.ozu.cs202project.classes;

import java.util.Date;

public class user {
    public Integer user_id;
    public String name;
    public String user_name;
    public Integer user_type;

    public user(Integer id, String name, String user_name, Integer type) {
        this.user_id = id;
        this.name=name;
        this.user_name=user_name;
        this.user_type=type;
    }

}
