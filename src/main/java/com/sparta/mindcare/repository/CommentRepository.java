package com.sparta.mindcare.repository;


import com.sparta.mindcare.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {


}
