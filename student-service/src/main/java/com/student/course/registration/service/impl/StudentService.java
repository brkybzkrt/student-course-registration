package com.student.course.registration.service.impl;


import com.student.course.registration.authcommon.config.KeycloakConfiguration;
import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.StudentCreateUpdateDto;
import com.student.course.registration.entitycommon.dtos.StudentResponseDto;
import com.student.course.registration.entitycommon.entities.Student;
import com.student.course.registration.exceptionfiltercommon.exceptionFilter.ResourceNotFoundException;
import com.student.course.registration.repository.StudentRepository;
import com.student.course.registration.service.IStudentService;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {

    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private KeycloakConfiguration confs;

    @Override
    public ResponseEntity<Object> login(LoginDto loginDto) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("grant_type", "password");
            form.add("client_id", confs.getClientId());
            form.add("client_secret", confs.getClientSecret());
            form.add("username", loginDto.getUsername());
            form.add("password", loginDto.getPassword());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(confs.getAuthServerUrl(), entity, String.class);


            return ResponseEntity.status(HttpStatus.CREATED.value()).body(getStudentResponse(response.getBody(),HttpStatus.CREATED.value(), null));

        } catch (Exception e) {
           throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> register(RegisterDto registerDto) {
        try{

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(confs.getBaseUrl())
                    .realm("master")
                    .clientId("admin-cli")
                    .username(confs.getAdminUsername())
                    .password(confs.getAdminPassword())
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();


            org.keycloak.representations.idm.UserRepresentation user = new org.keycloak.representations.idm.UserRepresentation();
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());

            user.setEnabled(true);

            var usersResource = keycloak.realm(confs.getRealm()).users();
            var response = usersResource.create(user);

            if (response.getStatus() != 201) {
                return ResponseEntity.status(response.getStatus()).body("Keycloak user creation failed");
            }

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            var credential = new org.keycloak.representations.idm.CredentialRepresentation();
            credential.setTemporary(false);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(registerDto.getPassword());

            usersResource.get(userId).resetPassword(credential);


            RealmResource realmResource = keycloak.realm(confs.getRealm());
            RoleRepresentation userRole = realmResource.roles().get(confs.getUserRole()).toRepresentation();

            usersResource.get(userId)
                    .roles()
                    .realmLevel()
                    .add(Collections.singletonList(userRole));

            StudentCreateUpdateDto newStudent= new StudentCreateUpdateDto();

            BeanUtils.copyProperties(registerDto,newStudent);
            newStudent.setKeycloakId(userId);

           return create(newStudent);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<Object> create(StudentCreateUpdateDto createDto) {
        try {
            Student newStudent = new Student();
            BeanUtils.copyProperties(createDto,newStudent);

            Student savedStudent = studentRepository.save(newStudent);

            StudentResponseDto response= new StudentResponseDto();
            BeanUtils.copyProperties(savedStudent,response);

            return ResponseEntity.status(HttpStatus.CREATED.value()).body(getStudentResponse(response,HttpStatus.CREATED.value(),null));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));

            if(student.isPresent()){
                StudentResponseDto studentResponse = new StudentResponseDto();
                BeanUtils.copyProperties(student.get(),studentResponse);
                studentResponse.setCanRegisterCourse(student.get().canRegisterMoreCourses());


                return ResponseEntity.ok().body(getStudentResponse(studentResponse,HttpStatus.OK.value(), null));
            }
            else  throw new ResourceNotFoundException("Student not found with id: " + id);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> updateById(String id, StudentCreateUpdateDto studentCreateUpdateDto) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));

            if(student.isPresent()){
                BeanUtils.copyProperties(studentCreateUpdateDto,student.get());

                Student createdStudent = studentRepository.save(student.get());

                StudentResponseDto response = new StudentResponseDto();
                BeanUtils.copyProperties(createdStudent,response);

                return ResponseEntity.ok().body(getStudentResponse(response,HttpStatus.OK.value(), null));
            }
            else  throw new ResourceNotFoundException("Student not found with id: " + id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(String id) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));

            if(student.isPresent()){
                studentRepository.deleteById(student.get().getId());

                return ResponseEntity.ok().body(getStudentResponse(null,HttpStatus.OK.value(), "Successfully Deleted"));
            }

            else  throw new ResourceNotFoundException("Student not found with id: " + id);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private <T> SuccessResponse<T> getStudentResponse(T data, Integer status, String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setData(data);

        return response;
    }
}
