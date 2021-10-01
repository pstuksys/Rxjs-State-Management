package com.paulius.server;

import com.paulius.server.enumeration.Status;
import com.paulius.server.model.ServerModel;
import com.paulius.server.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ServerRepo repository) {
		return args -> {
			repository.save(new ServerModel(null,"192.168.1.160","Ubuntu Linux","16GB","PC",
					"http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
			repository.save(new ServerModel(null,"192.168.1.58","Fedora Linux","16GB","Dell Tower",
					"http://localhost:8080/server/image/server2.png", Status.SERVER_UP));
			repository.save(new ServerModel(null,"192.168.1.48","Windows","32GB","Web Server",
					"http://localhost:8080/server/image/server3.png", Status.SERVER_DOWN));
		};
	}
}
