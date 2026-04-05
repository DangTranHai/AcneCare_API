package com.acnecare.acnecare_app_api.common.config;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.acnecare.acnecare_app_api.identity.entity.Role;
import com.acnecare.acnecare_app_api.identity.entity.User;
import com.acnecare.acnecare_app_api.identity.repository.RoleRepository;
import com.acnecare.acnecare_app_api.identity.repository.UserRepository;
import com.acnecare.acnecare_app_api.post.entity.Post;
import com.acnecare.acnecare_app_api.post.repository.PostRepository;
import com.acnecare.acnecare_app_api.profile.entity.UserProfile;
import com.acnecare.acnecare_app_api.profile.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, 
                                       RoleRepository roleRepository,
                                       UserProfileRepository userProfileRepository,
                                       PostRepository postRepository) {
        return args -> {
            if (userRepository.existsByEmail("admin@gmail.com")) {
                return;
            }

            roleRepository
                    .findByName("PATIENT")
                    .orElseGet(() -> roleRepository.save(Role.builder()
                            .name("PATIENT")
                            .description("Patient role")
                            .build()));

            Role adminRole = roleRepository
                    .findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder()
                            .name("ADMIN")
                            .description("Admin role")
                            .build()));

            User admin = userRepository.save(User.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("12345678"))
                    .roles(Set.of(adminRole))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .lastLoginAt(LocalDateTime.now())
                    .status("ACTIVE")
                    .build());

            userProfileRepository.save(UserProfile.builder()
                    .userId(admin.getId())
                    .firstName("Admin")
                    .lastName("System")
                    .phone("0123456789")
                    .build());

            log.info("Seeded default admin user admin@gmail.com with ADMIN role and profile");
            log.info("DEBUG: Admin ID: {}", admin.getId());
            log.info("DEBUG: Admin Password Hash: {}", admin.getPassword());

            // Seed some dummy users and posts for testing
            User user1 = userRepository.save(User.builder()
                    .email("user1@gmail.com")
                    .password(passwordEncoder.encode("12345678"))
                    .roles(Set.of(roleRepository.findByName("PATIENT").get()))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .status("ACTIVE")
                    .build());

            userProfileRepository.save(UserProfile.builder()
                    .userId(user1.getId())
                    .firstName("Nguyen")
                    .lastName("Van A")
                    .phone("0987654321")
                    .build());

            postRepository.save(Post.builder()
                    .postTitle("Bài viết test 1")
                    .postContent("Nội dung bài viết test 1 chờ duyệt")
                    .status("PENDING")
                    .user(user1)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            postRepository.save(Post.builder()
                    .postTitle("Bài viết test 2")
                    .postContent("Nội dung bài viết test 2 đã duyệt")
                    .status("APPROVED")
                    .user(user1)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            log.info("Seeded dummy users and posts for testing");
        };
    }
}
