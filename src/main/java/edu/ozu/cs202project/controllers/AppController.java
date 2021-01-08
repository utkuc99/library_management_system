package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.Salter;
import edu.ozu.cs202project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

@Controller
@SessionAttributes({ "username", "level", "itemData"})
public class AppController
{
    @Autowired
    LoginService service;

    @Autowired
    JdbcTemplate conn;

    @GetMapping("/")
    public String index(ModelMap model)
    {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(ModelMap model)
    {
        return "login";
    }

    @PostMapping("/login")
    public String login(ModelMap model, @RequestParam String username, @RequestParam String password)
    {

        password = Salter.salt(password, "CS202Project");

        if (!service.validate(username, password))
        {
            model.put("errorMessage", "Invalid Credentials");

            return "login";
        }

        model.put("username", username);

        return "login";
    }

    @RequestMapping(value = "/login" , params = "button", method=RequestMethod.POST)
    public String signUpButton(ModelMap model)
    {
        return "redirect:/signUp";
    }

    @GetMapping("/logout")
    public String logout(ModelMap model, WebRequest request, SessionStatus session)
    {
        session.setComplete();
        request.removeAttribute("username", WebRequest.SCOPE_SESSION);

        return "redirect:/login";
    }

    @GetMapping("/list")
    public String list(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Books",
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("author_name") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "list";
    }

    @GetMapping("/signUp")
    public String SignUp(ModelMap model)
    {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String SignUpPost(ModelMap model)
    {
        return "signUp";
    }
}
