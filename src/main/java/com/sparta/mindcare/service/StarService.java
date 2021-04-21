package com.sparta.mindcare.service;

import com.sparta.mindcare.dto.CommentDto;
import com.sparta.mindcare.dto.StarDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Star;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class StarService {

    private final StarRepository starRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Star createStar(StarDto starDto){
        Long doctorId=starDto.getDoctorId();
        Star star= starRepository.findByDoctorId(doctorId);
        if(star==null){
            star = new Star(starDto);
            starRepository.save(star);
        }
        else{
            star.add(starDto);
        }
        return star;
    }

    @Transactional
    public Star updateStar(Long commentId, Integer score){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new NullPointerException("후기가 존재하지 않습니다.")
        );
        Integer oldScore=comment.getScore();
        Star star = comment.getStar();
        star.update(oldScore, score);
        return star;
    }


    @Transactional
    public void deleteStar(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new NullPointerException("후기가 존재하지 않습니다.")
        );

        Integer score = comment.getScore();
        Star star= comment.getStar();
        star.delete(score);
    }

}
