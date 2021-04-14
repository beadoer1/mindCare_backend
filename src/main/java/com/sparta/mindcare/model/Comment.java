package com.sparta.mindcare.model;

import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String username;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String writing;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", nullable=false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable=false)
    private User user;

    public Comment(String writing, Doctor doctor, User user){
        this.username=user.getUsername();
        this.writing=writing;
        this.doctor=doctor;
        this.user= user;


    }

  
}
