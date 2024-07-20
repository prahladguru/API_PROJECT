package com.apiapp.api.service.Impl;
import com.apiapp.api.Repository.RegistrationRepository;
import com.apiapp.api.entity.Registration;
import com.apiapp.api.exception.ResourceNotfound;
import com.apiapp.api.payload.RegistrationDto;
import com.apiapp.api.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }
    public RegistrationServiceImpl(){

    }

    @Override
    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
        Registration registration = mapToEntity(registrationDto);
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDto dto=mapToDto(savedEntity);
        return dto;
    }

    @Override
    public void deleteRegistrationById(long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public RegistrationDto updateRegistration(long id, RegistrationDto registrationDto) {
        Optional<Registration> or = registrationRepository.findById(id);
       Registration registration = or.get();
        registration.setName(registrationDto.getName());
        registration.setEmail(registrationDto.getEmail());
        registration.setMobile(registrationDto.getMobile());
        Registration savedEntity = registrationRepository.save(registration);
        RegistrationDto dto=mapToDto(savedEntity);

        return dto;
    }

    @Override
    public List<RegistrationDto> getRegistration(int pageNo, int pageSize, String sortBy, String sortDir) {
       // RegistrationServiceImpl r = new RegistrationServiceImpl();
       // List<Registration> registrations = registrationRepository.findAll();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC,sortBy) : Sort.by(Sort.Direction.DESC,sortBy);

        //Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Registration> all = registrationRepository.findAll(pageable);
        List<Registration> registrations = all.getContent();
        List<RegistrationDto> registrationdtos = registrations.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
       // List<RegistrationDto> registrationdtos = registrations.stream().map(r::mapToDto).collect(Collectors.toList());
        System.out.println(all.getTotalPages());
        System.out.println(all.isLast());
        System.out.println(all.isFirst());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        return registrationdtos;

    }

    @Override
    public RegistrationDto getRegistrationById(long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotfound("Registration not found with id: " + id));
        RegistrationDto registrationDto = mapToDto(registration);
        return registrationDto;
    }


    public Registration mapToEntity(RegistrationDto Dto)
    {
        Registration reg=new Registration();
        reg.setName(Dto.getName());
        reg.setEmail(Dto.getEmail());
        reg.setMobile(Dto.getMobile());
        return reg;
    }
    public RegistrationDto mapToDto(Registration regs)
    {
        RegistrationDto dtos=new RegistrationDto();
        dtos.setId(regs.getId());
        dtos.setName(regs.getName());
        dtos.setEmail(regs.getEmail());
        dtos.setMobile(regs.getMobile());
        return dtos;
    }
}
