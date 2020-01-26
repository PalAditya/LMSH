package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class RepoController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
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

    @PostMapping(path="/addBooks") // Map ONLY POST Requests
    public @ResponseBody
    String addNewBook (@RequestParam String bookname
            , @RequestParam String authorname, @RequestParam String isbn, @RequestParam String location) {
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
}
