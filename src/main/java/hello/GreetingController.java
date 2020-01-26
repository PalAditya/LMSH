package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s! Your random size is %s";
	private final AtomicLong counter = new AtomicLong();

	private int getRandomSize()
	{
		int arr[] = {36, 38, 40, 42, 44};
		int val = (int)(Math.random()*10000);
		val %= 4;
		return arr[val];

	}

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
							String.format(template, name, getRandomSize()), 36);
	}
}
