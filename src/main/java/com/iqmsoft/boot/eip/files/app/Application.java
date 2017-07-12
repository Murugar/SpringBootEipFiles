package com.iqmsoft.boot.eip.files.app;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.iqmsoft.boot.eip.files.integration.ConfigProps;

import java.io.File;

@SpringBootApplication
@ComponentScan(basePackages = "com.iqmsoft.boot.eip.files")
@Component
@Slf4j
public class Application {

    @Autowired
    private ConfigProps configProps;

    private void copyFile() throws Exception {
        val source = new File(Application.class.getResource("/file.csv").toURI());
        val destination = new File(configProps.getIncomingDirectory() + "/file.csv");
        if (destination.exists()) {
            log.info("Removing existing file: {}", destination);
            destination.delete();
        }
        log.info("Copying file from: {} to: {}", source, destination);
        FileCopyUtils.copy(source, destination);
    }

    public static void main(String... args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        Application app = ctx.getBean(Application.class);
        app.copyFile();
    }
}
