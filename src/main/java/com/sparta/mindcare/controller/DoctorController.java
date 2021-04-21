package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.DoctorDetailReturn;
import com.sparta.mindcare.controllerReturn.ResultReturn;
import com.sparta.mindcare.dto.DoctorDto;
import com.sparta.mindcare.dto.PartCommentDto;
import com.sparta.mindcare.model.Comment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.Star;
import com.sparta.mindcare.repository.CommentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.repository.StarRepository;
import com.sparta.mindcare.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final CommentRepository commentRepository;
    private final StarRepository starRepository;
    private final DoctorService doctorService;

    //상담사 전체
    @GetMapping("/api/doctors")
    public ResultReturn getDoctors(){
        List<Doctor> doctorList = doctorRepository.findAll();
        return new ResultReturn(true, doctorList, "상담사 전체 list 반환 성공");
    }

    @PostMapping("/api/doctors/categories") // Category(Specialty) 별 상담사 List 불러오기
    public ResultReturn getDoctorsCategories(@RequestBody List<String> requestCategories){
        // 교집합을 구해야하므로 List에 첫번째 category로 검색한 List를 넣어둔다.
        List<Doctor> doctorList = doctorRepository.findAllBySpecialties(requestCategories.get(0));
        for(String category : requestCategories){
            List<Doctor> doctorListOthers = doctorRepository.findAllBySpecialties(category);
            // 교집합 반환하는 메서드 retainAll() -> List에 저장된 객체 중에서 주어진 컬렉션간 공통된 것들만을 남기고 나머지는 삭제한다
            doctorList.retainAll(doctorListOthers);
        }
        return new ResultReturn(true, doctorList, "전문분야 별 상담사 list 반환 성공");
    }

    //상담사 상세
    @GetMapping("/api/doctors/{id}")
    public DoctorDetailReturn getDoctorDetail(@PathVariable Long id){
        try {
            Doctor doctor = doctorRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
            );
            List<PartCommentDto> partComments = new ArrayList();
            List<Comment> comments = commentRepository.findAllByDoctorId(doctor.getId());
            if(comments.isEmpty()){
                return new DoctorDetailReturn(true, doctor, null, 0, "반환 성공");
            }
            Star star=starRepository.findByDoctorId(id);
            Integer totalNum=star.getTotalNum();
            Integer totalScore= star.getTotalScore();
            float starScore=(totalNum==0) ? 0 : (float)totalScore/totalNum;
            for(Comment comment : comments){
                Long commentId= comment.getId();
                String writer= comment.getUsername();
                String writing= comment.getWriting();
                partComments.add(new PartCommentDto(commentId,writer,writing));
            }

            return new DoctorDetailReturn(true, doctor, partComments, starScore,"반환 성공!");
        }catch(IllegalArgumentException e){
            return new DoctorDetailReturn(false,null, null, 0, e.getMessage());
        }
    }

    //상담사 추가
    @PostMapping("/api/doctors")
    public void createDoctor(@RequestBody DoctorDto requestDto){
        Doctor doctor = new Doctor(requestDto);
        doctorRepository.save(doctor);
    }

    //상담사 리스트 추가
    @PostMapping("/api/doctors/all")
    public void createDoctor(@RequestBody List<DoctorDto> requestDtoList){
        for(DoctorDto doctorDto : requestDtoList){
            Doctor doctor = new Doctor(doctorDto);
            doctorRepository.save(doctor);
        }
    }

    //상담사 리스트 수정
    @PutMapping("/api/doctors/all")
    public void updateDoctor(@RequestBody List<DoctorDto> requestDtoList){
        for(DoctorDto docDto : requestDtoList){
                Long id = docDto.getId();
                doctorService.update(id,docDto);
        }
    }

    @DeleteMapping("/api/doctors/{id}")
    public void deleteDoctor(@PathVariable Long id){
        doctorRepository.deleteById(id);
    }
}
