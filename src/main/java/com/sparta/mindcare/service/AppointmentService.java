package com.sparta.mindcare.service;

import com.sparta.mindcare.model.AppointmentTimeCheck;
import com.sparta.mindcare.controllerReturn.ResultReturn;
import com.sparta.mindcare.dto.AppointmentDto;
import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.AppointmentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public ResultReturn getPossibleTime(Long doctorId, Map<String,String> requestDate){
        // 1. 사용자가 선택한 날짜에 상담사가 근무하는지 확인
        // 사용자가 선택한 날짜를 LocalDate 객체로 바꿔 요일 format으로 변경
        LocalDate appointmentDate = LocalDate.parse(requestDate.get("date")); // "2021-04-16" -> LocalDate 인스턴스화
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko"));
        String checkDayOfWeek = appointmentDate.format(formatter); // LocalDate -> 요일 String 으로 만듬 "금"

        // 상담사 정보를 가져와 근무 요일 List를 불러옴
        Doctor doctor;
        try {
            doctor = doctorRepository.findById(doctorId).orElseThrow(
                    () -> new IllegalArgumentException("해당 상담사의 정보가 존재하지 않습니다.")
            );
        } catch(IllegalArgumentException e){
            return new ResultReturn(false,null,e.getMessage());
        }

        List<String> daysOfWeek = doctor.getDaysOfWeek();

        // 예약희망일이 오늘 이전의 날인지 확인하여 그럴 경우 error 메시지 반환
        if(appointmentDate.isBefore(LocalDate.now())){
            return new ResultReturn(false,null,"이미 지난 날짜입니다. 날짜를 다시 확인해주세요.");
        }
        // 상담사의 근무 요일 List에 선택한 날짜의 요일이 포함되어있는지 확인 후 없는 경우 false 반환
        else if(!daysOfWeek.contains(checkDayOfWeek)){
            return new ResultReturn(false,null,"상담사가 근무하지 않는 날짜입니다. 근무일을 확인해주세요!");
        }

        // 2. 예약 현황에서 이미 예약된 시간에 대해 체크 // 당일 예약 시 현재 시간 이후의 시간만 true 반영하는 로직 필요할 듯
        // doctor의 근무 시간을 불러옴
        Map<String,Long> workingTime = doctor.getWorkingTime();
        Long startTime = workingTime.get("startTime");
        Long endTime = workingTime.get("endTime");

        // 해당 일, 해당 상담사에 대해 사전에 예약된 List를 불러옴
        List<Appointment> alreadyAppointmentList = appointmentRepository.findByDateAndDoctor(appointmentDate, doctor);
        List<LocalTime> alreadyAppointmentTimeList = new ArrayList<>();
        for(Appointment alreadyAppointment : alreadyAppointmentList) {
            alreadyAppointmentTimeList.add(alreadyAppointment.getTime());
        }
        // 반환값에 넣을 시간대 리스트를 생성
        List<AppointmentTimeCheck> timeList = new ArrayList<>();

        // 기 예약 시간들을 시간대 리스트에 반영
        for(Long i = startTime; i < endTime; i++){ // 9시 ~ 16시 근무인 경우 9시 ~ 9시 50분 , ... , 15시 ~ 15시 50분 까지 상담 가능
            AppointmentTimeCheck timecheck = new AppointmentTimeCheck(Math.toIntExact(i)); // Math.toIntExact 'Long -> int'
            // 기 예약 시간 false 처리
            if(appointmentDate.isEqual(LocalDate.now())){
                // 예약일이 당일일 경우 지금 시간 이전의 시간들은 false 처리
                timecheck.setPossibleAppointment(!(timecheck.getTime().isBefore(LocalTime.now())
                        || alreadyAppointmentTimeList.contains(timecheck.getTime())));
            }else{
                timecheck.setPossibleAppointment(!alreadyAppointmentTimeList.contains(timecheck.getTime()));
            }
            timeList.add(timecheck);
        }
        // 확인 결과 반환값 정리
        return new ResultReturn(true,timeList,"상담 가능한 시간을 반환합니다.");
    }

    // 위처럼 결과값을 던지는 것보다 아래처럼 예외를 불러일으키는 것이 더 보기 좋은 듯 한데 옳은 방식은 무엇일까?
    public void createAppointment(Long doctorId, User user, Map<String,String> requestDateTime) throws IllegalArgumentException{

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalArgumentException("예약 요청한 상담사가 존재하지 않습니다.")
        );
        LocalDate date = LocalDate.parse(requestDateTime.get("date"));
        LocalTime time = LocalTime.parse(requestDateTime.get("time"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko"));
        String checkDayOfWeek = date.format(formatter); // LocalDate -> 요일 String 으로 만듬. ex) "금"
        // 해당 일, 해당 상담사에 대해 사전에 예약된 List를 불러옴
        List<Appointment> alreadyAppointmentList = appointmentRepository.findByDateAndDoctor(date, doctor);
        List<LocalTime> alreadyAppointmentTimeList = new ArrayList<>();
        for(Appointment alreadyAppointment : alreadyAppointmentList) {
            alreadyAppointmentTimeList.add(alreadyAppointment.getTime());
        }
        // doctor의 근무 시간을 불러옴
        Map<String,Long> workingTime = doctor.getWorkingTime();
        LocalTime startTime = LocalTime.of(Math.toIntExact(workingTime.get("startTime")),0);;
        LocalTime endTime = LocalTime.of(Math.toIntExact(workingTime.get("endTime")),0);

        if(user == null){
            throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
        }
        else if(date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("이미 지난 날짜입니다. 날짜를 다시 확인해주세요.");
        }
        else if(!doctor.getDaysOfWeek().contains(checkDayOfWeek)){
            throw new IllegalArgumentException("상담사가 근무하지 않는 날짜입니다. 근무일을 다시 확인해주세요!");
        }
        else if(date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())){
            throw new IllegalArgumentException("이미 지난 시간 입니다. 시간을 다시 확인해주세요.");
        }
        else if(alreadyAppointmentTimeList.contains(time)){
            throw new IllegalArgumentException("이미 예약된 시간 입니다. 다시 확인 바랍니다.");
        }
        else if(time.isBefore(startTime) || time.isAfter(endTime) || time.equals(endTime)){
            throw new IllegalArgumentException("상담사가 근무하지 않는 시간입니다. 시간을 다시 확인해주세요!");
        }
        AppointmentDto requestDto = new AppointmentDto(user,doctor,date,time);
        Appointment appointment = new Appointment(requestDto);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void updateIsComplited(User user){
        List<Appointment> appointmentList = appointmentRepository.findAllByUserId(user.getId());
        for(Appointment appointment : appointmentList){
            if(!appointment.getComplited()){
                if(appointment.getDate().isBefore(LocalDate.now())) {
                    appointment.update(true);
                }else if(appointment.getDate().isEqual(LocalDate.now())
                        &&appointment.getTime().isBefore(LocalTime.now())){
                    appointment.update(true);
                }
            }
        }
    }
}
