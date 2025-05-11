package com.edacy.edacy.configuration;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.edacy.edacy.entities.Role;
import com.edacy.edacy.entities.User;
import com.edacy.edacy.enums.ERole;
import com.edacy.edacy.repository.RoleRepository;
import com.edacy.edacy.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        seedRoles();
        seedAdminUser();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role(null, ERole.ROLE_ADMIN);
            Role userRole = new Role(null, ERole.ROLE_USER);
            roleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    private void seedAdminUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Admin123!"));

           /*  Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            admin.setRoles(Set.of(adminRole)); */

            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newRole = new Role(null, ERole.ROLE_ADMIN);
                        newRole.setName(ERole.ROLE_ADMIN);
                        return roleRepository.save(newRole);
                    });

            admin.setRoles(Set.of(adminRole));
            

            userRepository.save(admin);
            System.out.println("Admin user seeded successfully");
        }
    }
}
