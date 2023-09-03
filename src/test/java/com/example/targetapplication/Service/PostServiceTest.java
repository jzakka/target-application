package com.example.targetapplication.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;

    @Value("${dummy.content}")
    String dummyContent;

    @Test
    @DisplayName("게시글이 토픽에 저장되는 지 확인")
    void sendToTopic() {
        postService.createPost("테스트입니다", "Chung", dummyContent);
    }
}