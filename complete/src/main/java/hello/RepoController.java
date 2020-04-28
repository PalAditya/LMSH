package hello;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController    // This means that this class is a Controller
@Api(value="/demo",description="Customer Profile",produces ="application/json")
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class RepoController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value="Add customer",response=String.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Customer Details Retrieved",response=String.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Customer not found")})
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n = userRepository.save(n);
        return n.toString();
    }

    @DeleteMapping(path="delete")
    public String deleteUser(@RequestParam(value="id") int id)
    {
        User user = new User();
        user.setName("ERROR");
        User toDelete = userRepository.findById(id).orElse(user);
        if (toDelete.getName().equals("ERROR"))
        {
            return "No such user";
        }
        else
        {
            userRepository.delete(user);
            return "Deleted";
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/some")
    public @ResponseBody User getUserById(@RequestParam(value="id", defaultValue="1") int id)
    {
        User user = new User();
        user.setName("Aditya");
        user.setEmail("ap37@iitbbs.ac.in");
        user.setId(Integer.MAX_VALUE);
        return userRepository.findById(id).orElse(user);
    }

    @PatchMapping(path="/update")
    public String updateUserById(@RequestParam(value = "id") int id, User updatedUser)
    {
        User user = new User();
        user.setName("ERROR");
        User toUpdate = userRepository.findById(id).orElse(user);
        if (toUpdate.getName().equals("ERROR"))
        {
            return "No such user";
        }
        else
        {
           userRepository.delete(toUpdate);
           user.setName(updatedUser.getName());
           user.setEmail(updatedUser.getEmail());
           updatedUser = userRepository.save(user);
           return "Updated, new id is: "+updatedUser.toString();
        }
    }

    @PostMapping(path="/addBooks") // Map ONLY POST Requests
    public @ResponseBody
    String addNewBook (@RequestParam(name="bookname") String bookname
            , @RequestParam(name="authorname") String authorname,
                       @RequestParam(name="isbn") String isbn,
                       @RequestParam(name="location") String location) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Book b = new Book();
        b.setAuthorName(authorname);
        b.setBookName(bookname);
        b.setIsbn(isbn);
        b.setLocation(location);
        bookRepository.save(b);
        return "Saved book";
    }

    @GetMapping(path="/getABook/bookname") // Map ONLY POST Requests
    public @ResponseBody Book getBookByBookname (@RequestParam(defaultValue = "None", value = "name") String bookname)
    {
        return bookRepository.findByBookname(bookname);
        //return bookRepository.findBookByBookName(bookname);
    }
    @GetMapping(path="/getABook/isbn") // Map ONLY POST Requests
    public @ResponseBody Book getBookByISBN (@RequestParam (defaultValue = "None", value = "isbn") String isbn)
    {
        return bookRepository.findBookByIsbn(isbn);
        //return bookRepository.findBookByBookName(bookname);
    }

    @GetMapping(path="/getABook/author")
    public @ResponseBody Book getFirstBook (@RequestParam(defaultValue = "Agatha Christie", value = "name") String authorname) {
        return bookRepository.findBookByAuthorName(authorname);
    }

    @GetMapping(path="/getAllBooks")
    public @ResponseBody Iterable<Book> getAllBooks (@RequestParam(defaultValue = "Agatha Christie", value = "name") String authorname) {
        return bookRepository.findBooksByAuthorName(authorname);
    }
}
