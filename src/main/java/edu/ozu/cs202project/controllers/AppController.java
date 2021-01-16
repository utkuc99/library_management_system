package edu.ozu.cs202project.controllers;

//import com.sun.org.apache.xpath.internal.operations.Mod;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@SessionAttributes({ "userId", "userType", "itemData", "bookData", "searchData"})
public class AppController
{
    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime localDateTime2 = localDateTime.plusMonths(1);


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

    @GetMapping("/statistics_menu")
    public String statistics_menu(ModelMap model) {
        return "statistics_menu";
    }





    // USER PAGES

    @GetMapping("/list")
    public String list(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            List<String[]> data = conn.query("SELECT * FROM Books, Authors WHERE author = author_id",
                    (row, index) -> {
                        return new String[]{row.getString("title"), row.getString("author_name"), row.getString("is_avaliable"), row.getString("book_id")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "list";
        }

        if((Integer) model.getAttribute("userType") == 2) {
            List<String[]> data = conn.query("SELECT * FROM Books, Authors WHERE author = author_id and publisher = " + model.getAttribute("userId"),
                    (row, index) -> {
                        return new String[]{row.getString("title"), row.getString("author_name"), row.getString("is_avaliable"), row.getString("book_id")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "list";
        }

        List<String[]> data = conn.query("SELECT * FROM Books, Authors WHERE author = author_id and is_avaliable = 1",
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
                    LocalDateTime from = row.getTimestamp("expected_return_date").toLocalDateTime();
                    Duration duration = Duration.between(from, localDateTime);
                    Long dif = duration.toDays();
                    Double penalty = 0.0;
                    if(dif > 0) {penalty = row.getDouble("penalty") * dif;}
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("expected_return_date"), row.getString("returned"), row.getString("book_id"), penalty.toString() };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "borrow_hist";
    }





    // USER ACTIONS

    @GetMapping("/borrow")
    public String borrow(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " borrowed the book: " + id);
        conn.update(
                "INSERT INTO Borrows (borrower, book, borrow_date, expected_return_date) VALUES (?, ?, ?, ?)",
                model.getAttribute("userId"), id, localDateTime, localDateTime2
        );
        conn.update(
                "UPDATE Books SET is_borrowed = 1, borrow_count = borrow_count + 1 WHERE book_id = ?",
                id
        );
        book(id, model);
        return "book";
    }

    @GetMapping("/returnbook")
    public String returnbook(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " returned the book: " + id);
        LocalDateTime localDateTime = LocalDateTime.now();
        conn.update(
                "UPDATE Borrows SET returned = 1, real_return_date = ? WHERE book = ?",
                localDateTime, id
        );
        conn.update(
                "UPDATE Books SET is_borrowed = 0 WHERE book_id = ?",
                id
        );
        if(model.getAttribute("userType").equals(3)){
            borrowed_books(model);
            return "borrowed_books";
        }
        borrow_hist(model);
        return "borrow_hist";
    }

    @GetMapping("/hold")
    public String hold(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " held the book: " + id);
        conn.update(
                "UPDATE Books SET is_held = 1, held_user = ? WHERE book_id = ?",
                model.getAttribute("userId"), id
        );
        book(id, model);
        return "book";
    }

    @GetMapping("/unhold")
    public String unhold(@RequestParam String id, ModelMap model) {
        System.out.println(model.getAttribute("userId") + " unheld the book: " + id);
        conn.update(
                "UPDATE Books SET is_held = 0, held_user = null WHERE book_id = ?",
                id
        );
        book(id, model);
        return "book";
    }

    @GetMapping("/publisher_search")
    public String publisherSearch (ModelMap model){
        return "publisher_search";
    }

    @PostMapping("/publisher_search")
    public String publisherSearchPost (ModelMap model, @RequestParam String publisher, @RequestParam String genre){
        model.getAttribute("itemData");
        if (publisher.isEmpty()){
            String publisherEmpty = "";
            publisher = publisherEmpty;
        }
        if (genre.isEmpty()){
            String genreEmpty = "";
            genre = genreEmpty;
        }
        List<String[]> data = conn.query("SELECT DISTINCT name,genre_name FROM users,books,genres where users.user_id = books.publisher " +
                        "and books.genre = genres.genre_id and genre_name like'%"+genre+"%' and name like '%"+publisher+"%'",
                (row, index) -> {
                    return new String[]{ row.getString("name"), row.getString("genre_name") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));
        return "publisher_search";
    }


    // LİB MANAGER PAGES

    @GetMapping("/borrowed_books")
    public String borrowed_books(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM Borrows, Books, Users WHERE book = book_id AND borrower = user_id AND returned = 0;",
                (row, index) -> {
                    LocalDateTime from = row.getTimestamp("expected_return_date").toLocalDateTime();
                    Duration duration = Duration.between(from, localDateTime);
                    Long dif = duration.toDays();
                    Double penalty = 0.0;
                    if(dif > 0) {penalty = row.getDouble("penalty") * dif;}
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("username"), row.getString("expected_return_date"), row.getString("returned"), row.getString("book"), penalty.toString() };
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
                        LocalDateTime from = row.getTimestamp("expected_return_date").toLocalDateTime();
                        Duration duration = Duration.between(from, localDateTime);
                        Long dif = duration.toDays();
                        Double penalty = 0.0;
                        if(dif > 0) {penalty = row.getDouble("penalty") * dif;}
                        return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("expected_return_date"), row.getString("returned"), row.getString("book_id"), penalty.toString() };
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

    @PostMapping(value = "/book", params = "register")
    public String borrow(ModelMap model, @RequestParam String userID, @RequestParam String id)
    {
        if (userID.isEmpty() || id.isEmpty()){
            model.put("error", "Please fill everything");
            return "signUp";
        }
        else{
            conn.update(
                    "INSERT INTO Borrows (borrower, book, borrow_date, expected_return_date) VALUES (?, ?, ?, ?)",
                    userID, id, localDateTime, localDateTime2
            );
            conn.update(
                    "UPDATE Books SET is_borrowed = 1, borrow_count = borrow_count + 1 WHERE book_id = ?",
                    id
            );
            book(id, model);
            return "book" ;
        }
    }

    @GetMapping("/requests")
    public String requests(ModelMap model){

        List<String[]> data = conn.query("SELECT * FROM Requests;",
                (row, index) -> {
                    return new String[]{ row.getString("request_id"), row.getString("request_date"), row.getString("requester_id"),
                            row.getString("book_id"), row.getString("request_type"), row.getString("result"), row.getString("result_date"), row.getString("result_user") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "requests";
    }

    @PostMapping(value = "/requests", params = "accept")
    public String requestAccept(ModelMap model, @RequestParam String requestId, @RequestParam String bookId, @RequestParam String request_type){
        LocalDateTime localDateTime = LocalDateTime.now();

        conn.update("UPDATE Requests SET result = 1, result_date = ?, result_user = ? WHERE request_id = ?",
                localDateTime,model.getAttribute("userId"),requestId);

        if(request_type.equals("1")){
            conn.update("UPDATE Books SET is_avaliable = 1 WHERE book_id = ? ",bookId);
        } else {
            conn.update("UPDATE Books SET is_avaliable = 0 WHERE book_id = ? ",bookId);
        }
        requests(model);
        return "requests";
    }

    @PostMapping(value = "/requests" , params = "reject")
    public String requestReject(ModelMap model, @RequestParam String bookId, @RequestParam String requestId, @RequestParam String request_type){
        LocalDateTime localDateTime = LocalDateTime.now();

        if(request_type.equals("1")){
            conn.update("UPDATE Requests SET result = 0, result_date = ?, result_user = ?, book_id = NULL  WHERE request_id = ?", localDateTime,model.getAttribute("userId"),requestId);
            conn.update("DELETE FROM Books WHERE book_id = ? ",bookId);
        }else {
            conn.update("UPDATE Requests SET result = 0, result_date = ?, result_user = ?  WHERE request_id = ?", localDateTime,model.getAttribute("userId"),requestId);
        }




        requests(model);
        return "requests";
    }

    @GetMapping("/enable_book")
    public String enable_book(ModelMap model)
    {
        return "enable_book";
    }

    @PostMapping(value = "/enable_book", params = "add")
    public String enable_bookPost(ModelMap model, @RequestParam String bookid)
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        if((Integer)model.getAttribute("userType") == 3) {
            conn.update("UPDATE Books SET is_avaliable = 1 WHERE book_id = ?", bookid);
            return "user_menu";
        }
        return "enable_book";
    }

    @GetMapping("/disable_book")
    public String removeBook(ModelMap model)
    {
        return "disable_book";
    }

    @PostMapping(value = "/disable_book", params = "add")
    public String removeBookPost(ModelMap model, @RequestParam String bookid)
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        if((Integer)model.getAttribute("userType") == 3) {
            conn.update("UPDATE Books SET is_avaliable = 0 WHERE book_id = ?", bookid);
            return "user_menu";
        }
        return "disable_book";
    }



    // General Pages

    @GetMapping("/book")
    public String book(@RequestParam String id, ModelMap model)
    {
        List<book> data = conn.query("SELECT * FROM Books, Authors, Genres, Topics, Users WHERE author = author_id and genre = genre_id and topic = topic_id and publisher = user_id and book_id = " + id,
                (row, index) -> {
                    if(row.getBoolean("is_avaliable") == true || (Integer)model.getAttribute("userType") > 1 ){
                        return new book(row.getInt("book_id"), row.getString("title"), row.getDate("publication_date"), row.getString("author_name"), row.getString("name"), row.getString("genre_name"), row.getString("topic_name"), row.getBoolean("is_borrowed"), row.getBoolean("is_held"), row.getInt("held_user"), row.getInt("borrow_count"), row.getDouble("penalty"), row.getBoolean("is_avaliable"));
                    }
                    return null;
                });

        model.addAttribute("bookData", data.toArray(new book[0]));

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

        conn.update("INSERT IGNORE INTO authors (author_name) VALUES (?)", author_name);
        conn.update("INSERT IGNORE INTO genres (genre_name) VALUES (?)", genre);
        conn.update("INSERT IGNORE INTO topics (topic_name) VALUES (?)", topics);

        List<Integer> author_id = conn.query("SELECT author_id FROM authors WHERE author_name = '" + author_name + "'",
                (row, index) -> {
                    return row.getInt("author_id");
                });
        List<Integer> genre_id = conn.query("SELECT genre_id FROM genres WHERE genre_name = '" + genre + "'",
                (row, index) -> {
                    return row.getInt("genre_id");
                });
        List<Integer> topic_id = conn.query("SELECT topic_id FROM topics WHERE topic_name = '" + topics + "'",
                (row, index) -> {
                    return row.getInt("topic_id");
                });

        if((Integer)model.getAttribute("userType") == 2) {

            conn.update("INSERT INTO Books (book_id,title,publication_date,author,publisher,genre,topic,is_avaliable,is_borrowed,borrow_count," +
                    "is_held,held_user,penalty) VALUES (book_id,?,?,?,?,?,?,0,is_borrowed,borrow_count,is_held,held_user,penalty)",title,publication_date, author_id.get(0),
                    model.getAttribute("userId"),genre_id.get(0),topic_id.get(0));

            List<String[]> data = conn.query("SELECT book_id FROM Books WHERE title = '" + title +"'",
                    (row, index) -> {
                        return new String[] {row.getString("book_id")};
                    });
            String [] bookId = data.get(0);
            //System.out.println(bookId[0]);

            conn.update("INSERT INTO Requests (request_id,request_date,requester_id,book_id,request_type,result,result_date,result_user) VALUES (request_id,?,?," + bookId[0] +",1,result,result_date,result_user)",
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
            conn.update("INSERT INTO Books (book_id,title,publication_date,author,publisher,genre,topic,is_avaliable,is_borrowed,borrow_count," +
                            "is_held,held_user,penalty) VALUES (book_id,?,?,?,?,?,?,1,is_borrowed,borrow_count,is_held,held_user,penalty)",title,publication_date,author_id.get(0),
                    publisher,genre_id.get(0),topic_id.get(0));
            return "user_menu";
        }
    }

    @GetMapping("/search")
    public String search (ModelMap model){
        return "search";
    }

    @PostMapping(value = "/search", params = "searchBox")
    public String searchBox (ModelMap model, @RequestParam String title, @RequestParam String author, @RequestParam String genre, @RequestParam String topic,
                             @RequestParam(defaultValue = "false") boolean available, @RequestParam String year_published ){
        String is_available;
        String is_borrowed;
        if (title.isEmpty()){
            String titleEmpty = "";
            title = titleEmpty;
        }
        if (author.isEmpty()){
            String authorEmpty = "";
            author = authorEmpty;
        }
        if (genre.isEmpty()){
            String genreEmpty = "";
            genre = genreEmpty;
        }
        if (topic.isEmpty()){
            String topicEmpty = "";
            topic = topicEmpty;
        }
        if (available == false){
            is_borrowed = "";
        } else {
            is_borrowed = "and is_borrowed = 0";
        }
        if (year_published.isEmpty()){
            String publication_date = "";
            year_published = publication_date;
        }

        if((Integer) model.getAttribute("userType") == 1){

            List<String[]> data = conn.query("SELECT DISTINCT book_id,title,author_name,genre_name,topic_name,is_borrowed,is_avaliable,publication_date from books,genres,authors,topics where books.genre = genres.genre_id and\n" +
                            "books.author = authors.author_id and books.topic = topics.topic_id and title like '%"+title+"%' and author_name like '%"+author+"%' and genre_name like '%"+genre+"%' and topic_name like '%"+topic+"%' " +
                            "and is_avaliable = 1 " + is_borrowed + " and publication_date like '%"+year_published+"%' ",
                    (row, index) -> {
                        return new String[]{ row.getString("book_id"), row.getString("title"), row.getString("author_name"),
                                row.getString("genre_name"), row.getString("topic_name"), row.getString("is_avaliable"), row.getString("publication_date") };
                    });

            model.addAttribute("searchData", data.toArray(new String[0][2]));

        } else {
            List<String[]> data = conn.query("SELECT DISTINCT book_id,title,author_name,genre_name,topic_name,is_avaliable,publication_date from books,genres,authors,topics where books.genre = genres.genre_id and\n" +
                            "books.author = authors.author_id and books.topic = topics.topic_id and title like '%"+title+"%' and author_name like '%"+author+"%' and genre_name like '%"+genre+"%' and topic_name like '%"+topic+"%' " +
                            "and publication_date like '%"+year_published+"%' ",
                    (row, index) -> {
                        return new String[]{ row.getString("book_id"), row.getString("title"), row.getString("author_name"),
                                row.getString("genre_name"), row.getString("topic_name"), row.getString("is_avaliable"), row.getString("publication_date") };
                    });

        model.addAttribute("searchData", data.toArray(new String[0][2]));

        }

        search(model);
        return "search";
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

        List<String[]> data = conn.query("SELECT is_avaliable FROM Books WHERE book_id = " + bookID + " AND publisher = " + model.getAttribute("userId"),
                (row, index) ->{
                    return new String[]{row.getString("is_avaliable")};
                });
        String [] available = data.get(0);
        System.out.println(available[0]);

        if(available[0].equals("1") && requestType == 1){
            model.put("error4", "Book is already available");
            return "request";
        }

        if (available[0].equals("0") && requestType == 0){
            model.put("error5", "Book is already not available");
            return "request";
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
        List<String[]> data = conn.query("SELECT * FROM Borrows, Books, Users WHERE book = book_id AND borrower = user_id AND publisher = " + model.getAttribute("userId") + ";",
                (row, index) -> {
                    return new String[]{ row.getString("title"), row.getString("borrow_date"), row.getString("username"), row.getString("expected_return_date") };
                });

            model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "publisher_borrowed";
    }


    //Statistic Pages

    @GetMapping("/stats_most_genres")
    public String stats_most_genres(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            List<String[]> data = conn.query("SELECT genre_id, genre_name, COUNT(*) FROM Books, Genres, Borrows WHERE genre = genre_id AND book = book_id GROUP BY genre_id",
                    (row, index) -> {
                        return new String[]{row.getString("genre_id"), row.getString("genre_name"), row.getString("COUNT(*)")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "stats_most_genres";
        }
        return "user_menu";
    }

    @GetMapping("/stats_most_books")
    public String stats_most_books(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime.minusMonths(3));
            List<String[]> data = conn.query("SELECT book_id, title, COUNT(*) FROM Books, Borrows WHERE book = book_id AND borrow_date >= '" + timestamp + "' GROUP BY book_id",
                    (row, index) -> {
                        return new String[]{row.getString("book_id"), row.getString("title"), row.getString("COUNT(*)")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "stats_most_books";
        }
        return "user_menu";
    }

    @GetMapping("/stats_most_publisher")
    public String stats_most_publisher(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime.minusMonths(3));
            List<String[]> data = conn.query("SELECT user_id, name, COUNT(*) FROM Books, Borrows, Users WHERE book = book_id AND publisher = user_id GROUP BY user_id",
                    (row, index) -> {
                        return new String[]{row.getString("user_id"), row.getString("name"), row.getString("COUNT(*)")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "stats_most_publisher";
        }
        return "user_menu";
    }

    @GetMapping("/overdue_book_number")
    public String overdue_book_number(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);
            List<String[]> data = conn.query("SELECT COUNT(*) FROM Books, Borrows WHERE book = book_id AND returned = 0 AND expected_return_date < '" + timestamp + "'",
                    (row, index) -> {
                        return new String[]{row.getString("COUNT(*)")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "overdue_book_number";
        }
        return "user_menu";
    }

    @GetMapping("/borrowers_most_book")
    public String borrowers_most_book(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);
            List<String[]> data = conn.query("SELECT user_id, username, COUNT(*) FROM Borrows, Users WHERE borrower = user_id AND book = (SELECT book_id FROM Books, Borrows WHERE book = book_id GROUP BY book_id ORDER BY COUNT(*) DESC LIMIT 1) GROUP BY user_id",
                    (row, index) -> {
                        return new String[]{row.getString("user_id"), row.getString("username"), row.getString("COUNT(*)")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "borrowers_most_book";
        }
        return "user_menu";
    }

    @GetMapping("/stats_overdue_number")
    public String stats_overdue_number(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 3) {
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(localDateTime);
            List<String[]> data = conn.query("SELECT book_id, title, COUNT(*), borrow_count FROM Books, Borrows WHERE book = book_id AND real_return_date >= expected_return_date AND returned = 1 GROUP BY book_id",
                    (row, index) -> {
                        return new String[]{row.getString("book_id"), row.getString("title"), row.getString("borrow_count"), row.getString("COUNT(*)")};
                    });
            model.addAttribute("itemData", data.toArray(new String[0][2]));
            return "stats_overdue_number";
        }
        return "user_menu";
    }

    @GetMapping("/stats_user")
    public String stats_user(ModelMap model)
    {
        if((Integer) model.getAttribute("userType") == 1) {
            List<String[]> not_returned_late = conn.query("SELECT COUNT(*) FROM Users, Borrows WHERE borrower = user_id AND real_return_date >= expected_return_date AND returned = 0 and user_id = " + model.getAttribute("userId").toString(),
                    (row, index) -> {
                        return new String[]{row.getString("COUNT(*)")};
                    });
            List<String[]> returned_late = conn.query("SELECT COUNT(*) FROM Users, Borrows WHERE borrower = user_id AND real_return_date >= expected_return_date AND returned = 1 and user_id = " + model.getAttribute("userId").toString(),
                    (row, index) -> {
                        return new String[]{row.getString("COUNT(*)")};
                    });
            List<String[]> booked = conn.query("SELECT COUNT(*) FROM Users, Borrows WHERE borrower = user_id AND returned = 0 and user_id = " + model.getAttribute("userId").toString(),
                    (row, index) -> {
                        return new String[]{row.getString("COUNT(*)")};
                    });
            List<String[]> genre = conn.query("SELECT genre_name, COUNT(*) FROM Users, Borrows, Books, Genres WHERE book = book_id AND borrower = user_id AND genre = genre_id AND user_id = " + model.getAttribute("userId").toString() + " GROUP BY genre_id ORDER BY COUNT(*) DESC LIMIT 1",
                    (row, index) -> {
                        return new String[]{row.getString("genre_name"), row.getString("COUNT(*)")};
                    });
            String[] result_data[] = new String[][]{not_returned_late.get(0),
                    returned_late.get(0),
                    booked.get(0),
                    genre.get(0)};
            model.addAttribute("itemData", result_data);
            return "stats_user";
        }
        return "user_menu";
    }

}