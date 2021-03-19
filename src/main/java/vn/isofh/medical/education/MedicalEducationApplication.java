package vn.isofh.medical.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableEurekaClient
@RefreshScope
@ComponentScan(basePackages = {"vn.isofh"})
@SpringBootApplication
public class MedicalEducationApplication {

  public static void main(String[] args) {
    SpringApplication.run(MedicalEducationApplication.class, args);
  }

}
