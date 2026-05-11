package com.proyecto.supermercado.config;

import com.proyecto.supermercado.entity.Users;
import com.proyecto.supermercado.enums.RolEnum;
import com.proyecto.supermercado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner inicializarDatos() {
        return args -> {
            String adminEmail = "admin@supermercado.com";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                Users admin = new Users();
                admin.setName("Administrador");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRolId(RolEnum.ADMIN.getId()); // 1L
                userRepository.save(admin);
                System.out.println(">>> Usuario admin creado: " + adminEmail + " / admin123");
            }
        };
    }
}
