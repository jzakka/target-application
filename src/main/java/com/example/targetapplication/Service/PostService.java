package com.example.targetapplication.Service;

import com.example.targetapplication.Entity.Post;
import com.example.targetapplication.Repository.PostRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PostRepository postRepository;

    private final String TOPIC = "posts";

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

    public Post createPost(String title, String writer, String content) {
        Post post = Post.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .writer(writer)
                .content(content)
                .createDate(LocalDateTime.now().toString())
                .modifyDate(LocalDateTime.now().toString())
                .build();

        Message message = createMessage(post);

        kafkaTemplate.send(TOPIC, LocalDate.now().toString(), message);

        return post;
    }

    public List<Post> posts() {
        return postRepository.findAll();
    }

    private Message createMessage(Post post) {
        List<Field> fields = List.of(
                new Field("string", false, "id"),
                new Field("string", true, "title"),
                new Field("string", true, "writer"),
                new Field("string", true, "content"),
                new Field("string", true, "create_date"),
                new Field("string", true, "modify_date"));
        Schema schema = new Schema();
        schema.setFields(fields);

        return new Message(schema, post);
    }
}
