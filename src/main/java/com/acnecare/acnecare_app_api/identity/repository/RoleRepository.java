package com.acnecare.acnecare_app_api.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.acnecare.acnecare_app_api.identity.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
