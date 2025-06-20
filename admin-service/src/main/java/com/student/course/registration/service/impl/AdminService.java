package com.student.course.registration.service.impl;


import com.student.course.registration.authcommon.config.KeycloakConfiguration;
import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.AdminCreateUpdateDto;
import com.student.course.registration.dto.AdminResponseDto;
import com.student.course.registration.entitycommon.entities.Admin;
import com.student.course.registration.entitycommon.entities.Student;
import com.student.course.registration.exceptionfiltercommon.exceptionFilter.ResourceNotFoundException;
import com.student.course.registration.repository.AdminRepository;
import com.student.course.registration.service.IAdminService;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AdminRepository adminRepository;

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


            return ResponseEntity.ok().body(getAdminResponse(response.getBody(),201,null));

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
            RoleRepresentation userRole = realmResource.roles().get(confs.getAdminRole()).toRepresentation();

            usersResource.get(userId)
                    .roles()
                    .realmLevel()
                    .add(Collections.singletonList(userRole));


            AdminCreateUpdateDto newAdmin= new AdminCreateUpdateDto();

            BeanUtils.copyProperties(registerDto,newAdmin);
            newAdmin.setKeycloakId(userId);

            return create(newAdmin);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<Object> create(AdminCreateUpdateDto createDto) {
        try {
            Admin newAdmin = new Admin();
            BeanUtils.copyProperties(createDto,newAdmin);

            Admin savedAdmin = adminRepository.save(newAdmin);

            AdminResponseDto response= new AdminResponseDto();
            BeanUtils.copyProperties(savedAdmin,response);

            return ResponseEntity.status(201).body(getAdminResponse(response,201,null));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Admin> admin = adminRepository.findById(Long.valueOf(id));

            if(admin.isPresent()){
                AdminResponseDto courseResponse = new AdminResponseDto();
                BeanUtils.copyProperties(admin.get(),courseResponse);

                return ResponseEntity.status(200).body(getAdminResponse(courseResponse,200,null));
            }
            else  throw new ResourceNotFoundException("Admin not found with id: " + id);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> updateById(String id, AdminCreateUpdateDto adminCreateUpdateDto) {
        try {
            Optional<Admin> admin = adminRepository.findById(Long.valueOf(id));

            if(admin.isPresent()){
                BeanUtils.copyProperties(adminCreateUpdateDto,admin.get());

                Admin createdAdmin = adminRepository.save(admin.get());

                AdminResponseDto response = new AdminResponseDto();
                BeanUtils.copyProperties(createdAdmin,response);

                return ResponseEntity.status(200).body(getAdminResponse(response,200,null));
            }
            else  throw new ResourceNotFoundException("Admin not found with id: " + id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(String id) {

        try {
            Optional<Admin> admin = adminRepository.findById(Long.valueOf(id));

            if(admin.isPresent()){
                adminRepository.deleteById(admin.get().getId());

                return ResponseEntity.status(200).body(getAdminResponse(null,200,"Successfully Deleted"));
            }

            else  throw new ResourceNotFoundException("Admin not found with id: " + id);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private <T> SuccessResponse<T> getAdminResponse(T data, Integer status, String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setData(data);

        return response;
    }
}
