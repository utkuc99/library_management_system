package edu.ozu.cs202project.services;

import edu.ozu.cs202project.classes.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginService
{
    @Autowired
    JdbcTemplate conn;

    public user validate(String username, String password)
    {
        List<user> data = conn.query("SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'",
                (row, index) -> {
                    return new user(row.getInt("user_id"), row.getString("name"), row.getString("surname"), row.getString("username"), row.getString("phone_number"), row.getString("birthdate"), row.getInt("user_type"));
                });

        if(data.size() == 1){
            return data.get(0);
        }else{
            return null;
        }
    }
}
