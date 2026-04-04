package com.acnecare.acnecare_app_api.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.acnecare.acnecare_app_api.identity.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
