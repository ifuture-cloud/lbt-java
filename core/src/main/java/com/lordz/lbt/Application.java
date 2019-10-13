package com.lordz.lbt;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：zzz
 * @date ：Created in 7/3/19 6:38 PM
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        //application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
