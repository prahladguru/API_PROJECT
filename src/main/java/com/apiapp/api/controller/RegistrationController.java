package com.apiapp.api.controller;

import com.apiapp.api.entity.Registration;
import com.apiapp.api.payload.RegistrationDto;
import com.apiapp.api.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
 //          http://localhost:8080/api/v1/registration
public class RegistrationController {
    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> addRegistration(
            @Valid @RequestBody RegistrationDto registrationDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
        RegistrationDto reg = registrationService.createRegistration(registrationDto);
        return new ResponseEntity<>(reg, HttpStatus.CREATED);
    }
    //    http://localhost:8080/api/v1/registration?id=
    @DeleteMapping
    public ResponseEntity<String> deleteRegistrationById(@RequestParam long id){
       registrationService.deleteRegistrationById(id);
       return new ResponseEntity<>("Registration deleted", HttpStatus.OK);
    }
     @PutMapping
    public ResponseEntity<RegistrationDto>updateRegistration(
            @RequestParam long id,
            @RequestBody RegistrationDto registrationDto
    ){
       RegistrationDto dto =  registrationService.updateRegistration(id, registrationDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //       http://localhost:8080/api/v1/registration?pageNo=0&pageSize=5&sortBy=email&sortDir=asc
      @GetMapping
    public ResponseEntity<List<RegistrationDto>> getRegistration(
            @RequestParam(name="pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam(name="pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(name="sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(name="sortDir",defaultValue = "name",required = false)String sortDir

      ){
        List<RegistrationDto> dtos = registrationService.getRegistration(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @GetMapping("/byid")
    public ResponseEntity<RegistrationDto> getRegistrationById(
            @RequestParam long id
    ){
       RegistrationDto  dto  =  registrationService.getRegistrationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
