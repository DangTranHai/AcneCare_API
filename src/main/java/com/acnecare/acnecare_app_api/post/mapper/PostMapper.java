package com.acnecare.acnecare_app_api.post.mapper;

import org.mapstruct.Mapper;
import com.acnecare.acnecare_app_api.post.entity.Post;
import com.acnecare.acnecare_app_api.admin.dto.response.AdminPostResponse;
import com.acnecare.acnecare_app_api.admin.dto.request.AdminPostCreationRequest;
import com.acnecare.acnecare_app_api.admin.dto.request.AdminPostUpdateRequest;

@Mapper(componentModel = "spring")
public interface PostMapper {

    AdminPostResponse toAdminPostResponse(Post post);

    default Post toPost(AdminPostCreationRequest request) {
        if (request == null) {
            return null;
        }
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .status(request.getStatus() == null ? "DRAFT" : request.getStatus())
                .build();
    }

    default void updatePostFromRequest(AdminPostUpdateRequest request, Post post) {
        if (request == null || post == null) {
            return;
        }
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getStatus() != null) {
            post.setStatus(request.getStatus());
        }
    }
}
