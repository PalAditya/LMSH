package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HomeController {

    class Home
    {
        String val;
        Home(String val)
        {
            this.val = val;
        }
        public String getVal()
        {
            return val;
        }
    }
    private static final String template = "This is the homepage :)";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public Home home() {
        return new Home(template);
    }
}