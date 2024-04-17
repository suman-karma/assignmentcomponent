package io.mhe.assignmentcomponent;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//@ComponentScan(basePackages={"io.mhe"})
@EnableAutoConfiguration
public class AssignmentComponentApplication implements CommandLineRunner {
	@Autowired
	IAssignmentCopyDAO assignmentCopyDAO;

	public static void main(String[] args) {
		SpringApplication.run(AssignmentComponentApplication.class, args);
		System.out.println("HELLO 2024 !!!");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("HELLO 2024 !!! Getting assignment name for id "+325573);
		// Homework Default policy
		System.out.println(" title is : "+ assignmentCopyDAO.getAssignmentName(325573));
	}
}
