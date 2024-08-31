//package telran.java51.student.dao;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.springframework.stereotype.Component;
//
//import telran.java51.student.model.Student;
//import telran.java51.student.service.StudentService;
//
//@Component
//public class StudentRepositoryImpl implements StudentRepository {
//	
//
//	Map<Integer, Student> students = new ConcurrentHashMap<>();
//
//	@Override
//	public Student save(Student student) {
//		students.put(student.getId(), student);
//		return student;
//	}
//
//	@Override
//	public Optional<Student> findById(int id) {
//		return Optional.ofNullable(students.get(id));
//	}
//
//	@Override
//	public Collection<Student> findAll() {
//		return students.values();
//	}
//	
//	@Override
//	public void deleteById(int id) {;
//	
//	students.remove(id);
//	}
//}
