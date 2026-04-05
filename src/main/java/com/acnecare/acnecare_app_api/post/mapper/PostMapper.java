package com.acnecare.acnecare_app_api.post.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.acnecare.acnecare_app_api.post.dto.request.PostsRequest;
import com.acnecare.acnecare_app_api.post.dto.response.PostsResponse;
import com.acnecare.acnecare_app_api.post.entity.Post;
import com.acnecare.acnecare_app_api.identity.dto.response.RoleResponse;
import com.acnecare.acnecare_app_api.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPosts(PostsRequest request);    
    PostsResponse toPostsResponse(Post posts);
    
    @Mapping(target = "user", ignore = true)
    List<PostsResponse> toPostsResponseList(List<Post> postsList);
    Set<RoleResponse> toRoleResponseSet(Set<Role> roles);

}
