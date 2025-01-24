package com.rj.appSecurity;

import com.rj.appSecurity.domain.RequestContext;
import com.rj.appSecurity.entity.RoleEntity;
import com.rj.appSecurity.enumeration.Authority;
import com.rj.appSecurity.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner init(RoleRepository roleRepository) {
//        return args -> {
//            RequestContext.setUserId(0L);
//            var userRole = new RoleEntity();
//            userRole.setName(Authority.ADMIN.name());
//            userRole.setAuthorities(Authority.USER);
//            roleRepository.save(userRole);
//
//            var userRole2 = new RoleEntity();
//            userRole2.setName(Authority.USER.name());
//            userRole2.setAuthorities(Authority.ADMIN);
//            roleRepository.save(userRole2);
//            RequestContext.start();
//        };
//    }
}
