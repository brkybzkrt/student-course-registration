package service;

import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.CourseCreateUpdateDto;
import com.student.course.registration.entitycommon.dtos.CourseResponseDto;
import com.student.course.registration.entitycommon.entities.Course;
import com.student.course.registration.entitycommon.entities.CourseType;
import com.student.course.registration.repository.CourseRepository;
import com.student.course.registration.service.impl.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.mockito.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;


    @Test
    public void courseService_create_test(){

        // Arrange
        Course savedCourse = new Course();
        savedCourse.setId(1L);
        savedCourse.setName("Java 101");
        savedCourse.setMaxCapacity(30);
        savedCourse.setCreatedAt(LocalDateTime.now());
        savedCourse.setUpdatedAt(LocalDateTime.now());

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        // Act
        CourseCreateUpdateDto dto = new CourseCreateUpdateDto();
        dto.setName("Java 101");
        dto.setMaxCapacity(30);

        ResponseEntity<Object> responseEntity = courseService.create(dto);

        // Assert
        assertEquals(201, responseEntity.getStatusCodeValue());

        SuccessResponse<?> successResponse = (SuccessResponse<?>) responseEntity.getBody();
        assertNotNull(successResponse);

        var data = successResponse.getData();
        assertNotNull(data);
        assertTrue(data.toString().contains("Java 101"));

        // Verify
        verify(courseRepository, times(1)).save(any(Course.class));
    }


    @Test
    public void courseService_getById_test(){

        // Arrange
        Course mockCourse = new Course();
        mockCourse.setId(1L);
        mockCourse.setName("Java 101");
        mockCourse.setMaxCapacity(30);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));

        // Act
        ResponseEntity<Object> response = courseService.getById("1");


        // Assert
        assertEquals(200, response.getStatusCodeValue());


        SuccessResponse<?> successResponse = (SuccessResponse<?>) response.getBody();
        assertNotNull(successResponse);
        assertEquals(200, successResponse.getStatus());

        CourseResponseDto responseData = (CourseResponseDto) successResponse.getData();
        assertEquals("Java 101", responseData.getName());
        assertEquals(30, responseData.getMaxCapacity());


        // Verify
        verify(courseRepository, times(1)).findById(1L);

    }


    @Test
    public void courseService_updateById_test(){

        // Arrange
        String id="1";

        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setName("Java 101");
        existingCourse.setType(CourseType.MANDATORY);
        existingCourse.setMaxCapacity(30);


        CourseCreateUpdateDto createUpdateDto = new CourseCreateUpdateDto();
        createUpdateDto.setName("Java (Spring)");
        createUpdateDto.setType(CourseType.ELECTIVE);
        createUpdateDto.setMaxCapacity(50);


        Course updatedCourse = new Course();
        updatedCourse.setId(1L);
        updatedCourse.setName("Java (Spring)");
        updatedCourse.setMaxCapacity(50);
        updatedCourse.setType(CourseType.ELECTIVE);


        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);


        // Act
        ResponseEntity<Object> response = courseService.updateById(id, createUpdateDto);


        // Assert
        SuccessResponse<?> successResponse = (SuccessResponse<?>) response.getBody();
        assertNotNull(successResponse);
        assertEquals(200,successResponse.getStatus());

        // Verify
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void courseService_deleteById_test(){

        // Arrange
        String id="1";

        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setName("Java 101");
        existingCourse.setType(CourseType.MANDATORY);
        existingCourse.setMaxCapacity(30);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));


        // Act
        ResponseEntity<Object> response = courseService.deleteById(id);


        // Assert
        SuccessResponse<?> successResponse = (SuccessResponse<?>) response.getBody();
        assertNotNull(successResponse);
        assertEquals("Successfully Deleted",successResponse.getMessage());


        // Verify
        verify(courseRepository,times(1)).deleteById(1L);
        verify(courseRepository,times(1)).findById(1L);

    }

    @Test
    public void courseService_getCoursesByTypes_test(){

        // Arrange
        CourseType courseType= CourseType.MANDATORY;

        Course course = new Course();
        course.setId(1L);
        course.setName("Java 101");
        course.setMaxCapacity(30);
        course.setType(CourseType.MANDATORY);

        when(courseRepository.getCoursesByType(courseType)).thenReturn(List.of(course));


        // Act
        ResponseEntity<Object> response = courseService.getCoursesByType(courseType);


        // Assert
        SuccessResponse<?> successResponse= (SuccessResponse<?>)  response.getBody();

        assertNotNull(successResponse);
        assertEquals(200,successResponse.getStatus());


        //  Verify
        verify(courseRepository, times(1)).getCoursesByType(courseType);

    }


    @Test
    public void courseService_getCoursesByIds_test(){

        // Arrange
        List<Long> ids= List.of(1L,2L);

        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Java 101");
        course1.setType(CourseType.MANDATORY);
        course1.setMaxCapacity(30);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Python 101");
        course1.setType(CourseType.MANDATORY);
        course2.setMaxCapacity(25);


        when(courseRepository.findAllById(ids)).thenReturn(List.of(course1, course2));


        // Act
        ResponseEntity<Object> response = courseService.getCoursesByIds(ids);

        // Assert
        SuccessResponse<?> body = (SuccessResponse<?>) response.getBody();
        assertNotNull(body);
        assertEquals(200, body.getStatus());


        String responseDataString = body.getData().toString();
        assertTrue(responseDataString.contains("Java 101"));
        assertTrue(responseDataString.contains("Python 101"));


        // Verify
        verify(courseRepository, times(1)).findAllById(ids);
    }
}
