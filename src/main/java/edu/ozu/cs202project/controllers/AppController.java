package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.classes.user;
import edu.ozu.cs202project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import edu.ozu.cs202project.classes.book;

import java.util.List;

@Controller
@SessionAttributes({ "userId", "userType", "level", "itemData"})
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

        //password = Salter.salt(password, "CS202Project");

        user login_user = service.validate(username, password);

        if (login_user == null)
        {
            model.put("errorMessage", "Invalid Credentials");

            return "login";
        }else if(login_user.user_type == 1){
            model.put("userId", login_user.user_id);
            model.put("userType", login_user.user_type);
            return "user_menu";
        }

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

    @GetMapping("/user_menu")
    public String user_menu(ModelMap model) {
        return "user_menu";
    }

    @GetMapping("/list")
    public String list(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Books",
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("author_name"), row.getString("book_id"), row.getString("is_avaliable") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "list";
    }

    @GetMapping("/borrow_hist")
    public String borrow_hist(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Borrows, Books WHERE book = book_id AND borrower = " + model.getAttribute("userId"),
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("book_id") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "borrow_hist";
    }

    @GetMapping("/book")
    public String book(@RequestParam String id, ModelMap model)
    {
        List<book> data = conn.query("SELECT * FROM Books WHERE book_id = " + id,
                (row, index) -> {
                    if(row.getBoolean("is_avaliable") == true){
                        return new book(row.getInt("book_id"), row.getString("title"), row.getDate("publication_date"), row.getString("author_name"), row.getInt("publisher"), row.getString("genre"), row.getString("topics"), row.getBoolean("is_borrowed"), row.getBoolean("is_held"), row.getInt("held_user"));
                    }
                    return null;
                });

        model.addAttribute("itemData", data.toArray(new book[0]));

        return "book";
    }

    @GetMapping("/borrow")
    public String borrow(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " borrowed the book: " + id);
        conn.update(
                "INSERT INTO Borrows (borrower, book) VALUES (?, ?)",
                model.getAttribute("userId"), id
        );
        conn.update(
                "UPDATE Books SET is_borrowed = 1 WHERE book_id = ?",
                id
        );
        return "book";
    }

    @GetMapping("/hold")
    public String hold(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " held the book: " + id);
        conn.update(
                "UPDATE Books SET is_held = 1, held_user = ? WHERE book_id = ?",
                model.getAttribute("userId"), id
        );
        return "book";
    }

    @GetMapping("/unhold")
    public String unhold(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " unheld the book: " + id);
        conn.update(
                "UPDATE Books SET is_held = 0, held_user = null WHERE book_id = ?",
                id
        );
        return "book";
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
