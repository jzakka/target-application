package com.example.targetapplication.Repository;

import com.example.targetapplication.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
}
