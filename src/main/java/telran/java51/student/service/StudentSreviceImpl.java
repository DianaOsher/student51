package telran.java51.student.service;

import java.rmi.StubNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import telran.java51.student.dao.StudentRepository;
import telran.java51.student.dto.ScoreDto;
import telran.java51.student.dto.StudentCreateDto;
import telran.java51.student.dto.StudentDto;
import telran.java51.student.dto.StudentUpdateDto;
import telran.java51.student.dto.exeptions.StudentNotFoundExeption;
import telran.java51.student.model.Student;

@Service
@RequiredArgsConstructor
public class StudentSreviceImpl implements StudentService {
	
	
	final StudentRepository studentRepository;
	final ModelMapper modelMapper;
	


//	public StudentSreviceImpl(StudentRepository studentRepository) {
//		this.studentRepository = studentRepository;
//	}

	@Override
	public Boolean addStudent(StudentCreateDto studentCreateDto) {
		if(studentRepository.existsById(studentCreateDto.getId())){
		return false;
	}
//		Student student = new Student(studentCreateDto.getId(), studentCreateDto.getName(), studentCreateDto.getPassword());
				Student student =  modelMapper.map(studentCreateDto, Student.class); 
				studentRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		//return new StudentDto(id, student.getName(), student.getScores());
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentDto removeStudent(Integer id) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		studentRepository.deleteById(id);
		//return new StudentDto(id, student.getName(), student.getScores());
		return modelMapper.map(student, StudentDto.class);
		
		
	}

	@Override
	public StudentCreateDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto) {
     Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
     if(studentUpdateDto.getName() !=null) {
    	 student.setName(studentUpdateDto.getName());
     }
     if(studentUpdateDto.getPassword() !=null){
    	 student.setPassword(studentUpdateDto.getPassword());
     }
     student = studentRepository.save(student);
		//return new StudentCreateDto(student.getId(), student.getName(), student.getPassword());
     return modelMapper.map(student, StudentCreateDto.class);
	}

	@Override
	public Boolean addScore(Integer id, ScoreDto scoreDto) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		boolean res = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
		student = studentRepository.save(student);
		return res;
	}

	@Override
	public List<StudentDto> finStudentByName(String name) {
		return studentRepository.findByNameIgnoreCase(name)
								//.map(s -> new StudentDto(s.getId(), s.getName(), s.getScores()))
						.map(s -> modelMapper.map(s, StudentDto.class))
								.collect(Collectors.toList());
	}

	@Override
	public Long getStudentsNameQuantity(Set<String> names) {
		return studentRepository.countByNameInIgnoringCase(names);
	}

	@Override
	public List<StudentDto> getStudentByExamMinScores(String exam, Integer minScore) {
		return studentRepository.findByExamAndScoreGreaterThan(exam, minScore)
				//.map(s -> new StudentDto(s.getId(), s.getName(), s.getScores()))
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList());
				
	}

}
