package com.confiance.gateway.config;

import com.confiance.gateway.entity.AdminUser;
import com.confiance.gateway.entity.Role;
import com.confiance.gateway.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${confiance.admin.username:Admin_Con}")
    private String adminUsername;

    @Value("${confiance.admin.password:Con@2301}")
    private String adminPassword;

    @Value("${confiance.admin.email:admin@confiance.com}")
    private String adminEmail;

    @Value("${confiance.admin.full-name:System Administrator}")
    private String adminFullName;

    @Override
    public void run(String... args) {
        if (adminUserRepository.existsByUsername(adminUsername)) {
            log.info("Admin user '{}' already exists — skipping seed.", adminUsername);
            return;
        }

        AdminUser admin = AdminUser.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .email(adminEmail)
                .fullName(adminFullName)
                .role(Role.SUPER_ADMIN)
                .enabled(true)
                .build();

        adminUserRepository.save(admin);
        log.info("Default admin user '{}' created successfully.", adminUsername);
    }
}