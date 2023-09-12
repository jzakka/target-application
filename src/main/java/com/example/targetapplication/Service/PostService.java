package com.example.targetapplication.Service;

import com.example.targetapplication.Entity.Post;
import com.example.targetapplication.Repository.PostRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post find(String postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Message{
        private Schema schema;
        private Post payload;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Schema {
        private String type = "struct";
        @Setter
        private List<Field> fields;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class Field {
        private String type;
        private boolean optional;
        private String field;
    }

    @Transactional
    public Post createPost(String title, String writer, String content) {
        Post post = Post.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .writer(writer)
                .content(content)
                .createDate(LocalDateTime.now().toString())
                .modifyDate(LocalDateTime.now().toString())
                .build();

        return postRepository.save(post);
    }

    public List<Post> posts() {
        return postRepository.findAll();
    }

}
