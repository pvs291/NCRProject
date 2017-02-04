package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan({"controller", "service"})
public class NcrProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcrProjectApplication.class, args);
	}
}
