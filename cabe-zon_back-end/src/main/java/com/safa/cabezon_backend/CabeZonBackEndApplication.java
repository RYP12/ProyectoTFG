package com.safa.cabezon_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CabeZonBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(CabeZonBackEndApplication.class, args);
    }

}
