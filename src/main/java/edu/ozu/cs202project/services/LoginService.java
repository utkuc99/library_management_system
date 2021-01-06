package edu.ozu.cs202project.services;

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

    public boolean validate(String username, String password)
    {
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM users WHERE username = ? AND password = ?", new Object[]{ username, password }
                );

        return response.size() == 1;
    }
}
