package flow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class FlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner writeData(FileWriterGateway gateway, Environment env) {
	  return args -> {
	    String[] activeProfiles = env.getActiveProfiles();
	    if (activeProfiles.length > 0) {
	      String profile = activeProfiles[0];
	      gateway.writeToFile("simple1.txt", "Hello, Spring! (" + profile + ")");
	      System.out.println("scuess");
	    } else {
	      System.out.println("请激活其中一个xmlconfig, javaconfig, or javadsl.");
	    }
	  };
	}
	
}
