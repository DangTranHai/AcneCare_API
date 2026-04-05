package com.acnecare.acnecare_app_api.admin.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.acnecare.acnecare_app_api.admin.dto.response.AdminUserResponse;
import com.acnecare.acnecare_app_api.identity.entity.Role;
import com.acnecare.acnecare_app_api.identity.entity.User;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminUserResponse toAdminUserResponse(User user);

    default List<String> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
