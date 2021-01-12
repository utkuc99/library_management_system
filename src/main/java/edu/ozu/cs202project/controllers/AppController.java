package edu.ozu.cs202project.controllers;

//import com.sun.org.apache.xpath.internal.operations.Mod;
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

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        }else{
            model.put("userId", login_user.user_id);
            model.put("userType", login_user.user_type);
            return "user_menu";
        }
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

    @GetMapping("/signUp")
    public String SignUp(ModelMap model)
    {
        return "signUp";
    }

    @PostMapping(value = "/signUp", params = "register")
    public String SignUpPost(ModelMap model, @RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String surname,
    @RequestParam String phone_number,@RequestParam String birthdate)

    {
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || phone_number.isEmpty() || birthdate.isEmpty()){
            model.put("error", "Please fill everything");
            return "signUp";
        }
        else{
            conn.update("INSERT INTO Users (user_id,name,surname,phone_number,birthdate,username,password,user_type) VALUES (user_id,?,?,?,?,?,?,user_type)",
                    name,surname,phone_number,birthdate,username,password);
            return "post_signup";

        }
    }
    @GetMapping("/post_signup")
    public String post_signup(ModelMap model)
    {
        return "post_signup";
    }







    // MENUS

    @GetMapping("/user_menu")
    public String user_menu(ModelMap model) {
        return "user_menu";
    }





    // USER PAGES

    @GetMapping("/list")
    public String list(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            List<String[]> data = conn.query("SELECT * FROM Books",
                    (row, index) -> {
                        return new String[]{row.getString("title"), row.getString("author_name"), row.getString("book_id")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "list";
        }

        List<String[]> data = conn.query("SELECT * FROM Books WHERE is_avaliable = 1",
                (row, index) -> {
                    return new String[]{row.getString("title"), row.getString("author_name"), row.getString("book_id")};
                });
        model.addAttribute("itemData", data.toArray(new String[0][2]));
        return "list";

    }

    @GetMapping("/borrow_hist")
    public String borrow_hist(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Borrows, Books WHERE book = book_id AND borrower = " + model.getAttribute("userId"),
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("expected_return_date"), row.getString("returned"), row.getString("book_id") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "borrow_hist";
    }





    // USER ACTIONS

    @GetMapping("/borrow")
    public String borrow(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " borrowed the book: " + id);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime2 = localDateTime.plusMonths(1);
        conn.update(
                "INSERT INTO Borrows (borrower, book, borrow_date, expected_return_date) VALUES (?, ?, ?, ?)",
                model.getAttribute("userId"), id, localDateTime, localDateTime2
        );
        conn.update(
                "UPDATE Books SET is_borrowed = 1, borrow_count = borrow_count + 1 WHERE book_id = ?",
                id
        );
        return "book";
    }

    @GetMapping("/returnbook")
    public String returnbook(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " returned the book: " + id);
        LocalDateTime localDateTime = LocalDateTime.now();
        conn.update(
                "UPDATE Borrows SET returned = 1, real_return_date = ? WHERE book = ? AND borrower = ?",
                localDateTime, id, model.getAttribute("userId")
        );
        conn.update(
                "UPDATE Books SET is_borrowed = 0 WHERE book_id = ?",
                id
        );
        return "borrow_hist";
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





    // LİB MANAGER PAGES

    @GetMapping("/borrowed_books")
    public String borrowed_books(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Borrows, Books, Users WHERE book = book_id AND borrower = user_id AND returned = 0;",
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("username"), row.getString("expected_return_date") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "borrowed_books";
    }

    @GetMapping("/users")
    public String users(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Users WHERE user_type = 1;",
                (row, index) -> {
                    return new String[]{ row.getString("username"), row.getString("name"), row.getString("surname"), row.getString("user_id") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "users";
    }

    @GetMapping("/user")
    public String user(@RequestParam String id, ModelMap model)
    {

        List<user> data = conn.query("SELECT * FROM Users WHERE user_id = " + id,
                (row, index) -> {
                    if(row.getInt("user_type") == 1){
                        return new user(row.getInt("user_id"), row.getString("name"), row.getString("surname"), row.getString("username"), row.getString("phone_number"), row.getString("birthdate"), row.getInt("user_type"));
                    }
                    return null;
                });

        model.addAttribute("itemData", data.toArray(new user[0]));

        return "user";
    }

    @GetMapping("/borrow_hist_admin")
    public String borrow_hist_admin(@RequestParam String id, ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3){
            List<String[]> data = conn.query("SELECT * FROM Borrows, Books WHERE book = book_id AND borrower = " + id,
                    (row, index) -> {
                        return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("expected_return_date"), row.getString("returned"), row.getString("book_id") };
                    });

            model.addAttribute("itemData", data.toArray(new String[0][2]));
        }

        return "borrow_hist";
    }

    @GetMapping("/publisher_signup")
    public String publisherSignup(ModelMap model)
    {
        return "publisher_signup";
    }

    @PostMapping(value = "/publisher_signup", params = "register")
    public String publisherSignupPost (ModelMap model, @RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String phone_number)
    {
        {
            if (username.isEmpty() || password.isEmpty() || name.isEmpty() ||  phone_number.isEmpty() ){
                model.put("error", "Please fill everything");
                return "publisher_signup";
            }
            else{
                conn.update("INSERT INTO Users (user_id,name,surname,phone_number,birthdate,username,password,user_type) VALUES (user_id,?,surname,?,birthdate,?,?,2)",
                        name,phone_number,username,password);
                return "publisher_signup_post";

            }
        }
    }



    // General Pages

    @GetMapping("/book")
    public String book(@RequestParam String id, ModelMap model)
    {
        List<book> data = conn.query("SELECT * FROM Books WHERE book_id = " + id,
                (row, index) -> {
                    if(row.getBoolean("is_avaliable") == true){
                        return new book(row.getInt("book_id"), row.getString("title"), row.getDate("publication_date"), row.getString("author_name"), row.getInt("publisher"), row.getString("genre"), row.getString("topics"), row.getBoolean("is_borrowed"), row.getBoolean("is_held"), row.getInt("held_user"), row.getInt("borrow_count"), row.getInt("last_borrow"), row.getDouble("penalty"), row.getBoolean("is_avaliable"));
                    }
                    return null;
                });

        model.addAttribute("itemData", data.toArray(new book[0]));

        return "book";
    }

    @GetMapping("/addBook")
    public String addBook(ModelMap model)
    {
        return "addBook";
    }

    @PostMapping(value = "/addBook", params = "add")
    public String addBookPost(ModelMap model, @RequestParam String title, @RequestParam String publication_date, @RequestParam String author_name,
                              @RequestParam String publisher, @RequestParam String genre,@RequestParam String topics)
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        if((Integer)model.getAttribute("userType") == 2) {
            conn.update("INSERT INTO Books (book_id,title,publication_date,author_name,publisher,genre,topics,is_avaliable,is_borrowed,borrow_count,last_borrow," +
                    "is_held,held_user,penalty) VALUES (book_id,?,?,?,?,?,?,0,is_borrowed,borrow_count,last_borrow,is_held,held_user,penalty)",title,publication_date,author_name,
                    model.getAttribute("userId"),genre,topics);

            List<book> data = conn.query("SELECT book_id FROM Books WHERE title = " + title,
                    (row, index) -> {
                        return new book (row.getInt("book_id"));
                    });

            conn.update("INSERT INTO Requests (request_id,request_date,requester_id,book_id,request_type,result,result_date,result_user) VALUES (request_id,?,?,null,1,result,result_date,result_user)",
                    localDateTime,model.getAttribute("userId"));
            return "user_menu";
        }
        else{
            Integer publisherID;
            try{
                publisherID = Integer.parseInt(publisher);
            } catch (NumberFormatException e){
                model.put("error", "Enter the publisher id");
                return "addBook";
            }
            conn.update("INSERT INTO Books (book_id,title,publication_date,author_name,publisher,genre,topics,is_avaliable,is_borrowed,borrow_count,last_borrow," +
                            "is_held,held_user,penalty) VALUES (book_id,?,?,?,?,?,?,1,is_borrowed,borrow_count,last_borrow,is_held,held_user,penalty)",title,publication_date,author_name,
                    publisher,genre,topics);
            return "user_menu";
        }
    }

    // Publisher

    @GetMapping("/request")
    public String request(ModelMap model)
    {
        return "request";
    }

    @PostMapping(value = "/request", params = "request")
    public String postRequest (ModelMap model, @RequestParam String bookID, @RequestParam String request_type)
    {
        Integer requestType;
        Integer book_id;
        LocalDateTime localDateTime = LocalDateTime.now();
        try{
            book_id = Integer.parseInt(bookID);
            requestType = Integer.parseInt(request_type);

        } catch (NumberFormatException e){
            model.put("error_id", "Please enter an id");
            return "request";
        }

        if (bookID == null){
            model.put("error", "Please fill everything");
            return "request";
        } else{
            List<String[]> data = conn.query("SELECT book_id FROM Books WHERE book_id = " + bookID + " AND publisher = " + model.getAttribute("userId"),
            (row, index) ->{
                return new String[]{row.getString("book_id")};
            });
            if(data == null){
                model.addAttribute("itemData", data.toArray(new String[0][2]));
                model.put("error2", "Book doesn't exist");
                return "request";
            }
        }

        if (requestType >= 2){
            model.put("error3", "Please enter either 0 or 1");
            return "request";
        }

        conn.update("INSERT INTO Requests (request_id,request_date,requester_id,book_id,request_type,result,result_date,result_user) " +
                "VALUES (request_id,?,?,?,?,result,result_date,result_user)",localDateTime,model.getAttribute("userId"),bookID,requestType);
        return "user_menu";
    }

    @GetMapping("/publisher_borrowed")
    public String publisherBorrowed (ModelMap model)
    {
        System.out.println(model.getAttribute("userId"));
        List<String[]> data = conn.query("SELECT * FROM Borrows, Books, Users WHERE book = book_id AND borrower = user_id AND returned = 0 AND publisher = " + model.getAttribute("userId") + ";",
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("username"), row.getString("expected_return_date") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "publisher_borrowed";
    }

}
