package br.com.ryan.safe_deliver.service;

import br.com.ryan.safe_deliver.dto.request.HospitalRegisterRequest;
import br.com.ryan.safe_deliver.dto.request.UserRegisterRequest;
import br.com.ryan.safe_deliver.dto.response.HospitalRegisterResponse;
import br.com.ryan.safe_deliver.dto.response.UserRegisterResponse;
import br.com.ryan.safe_deliver.entity.Hospital;
import br.com.ryan.safe_deliver.entity.User;
import br.com.ryan.safe_deliver.enums.Role;
import br.com.ryan.safe_deliver.repository.HospitalRepository;
import br.com.ryan.safe_deliver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HospitalRegisterResponse addHospital(HospitalRegisterRequest hospitalRegisterRequest) {
        Hospital hospital = new Hospital();
        hospital.setName(hospitalRegisterRequest.name());
        hospital.setCNPJ(hospitalRegisterRequest.CNPJ());
        Hospital hospitalSaved = hospitalRepository.save(hospital);
        return new HospitalRegisterResponse(hospitalSaved.getId(), hospitalSaved.getName(), hospitalSaved.getCNPJ());
    }

    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID hospitalId = null;
        User user = (User) authentication.getPrincipal();
        if (user.getRole() == Role.SYS_ADMIN) {
            if (userRegisterRequest.hospital_id() == null) throw new IllegalArgumentException("hospital id é obrigátório");
            if (userRegisterRequest.role() != Role.HOSPITAL_MANAGER) throw new AccessDeniedException("Sem permissão para criar esse tipo de usuario");
            hospitalId = userRegisterRequest.hospital_id();
        }
        if (user.getRole() == Role.HOSPITAL_MANAGER) {
            if (userRegisterRequest.role() != Role.DRIVER) throw new AccessDeniedException("Sem permissão para criar esse tipo de usuario");
            hospitalId = user.getHospital().getId();
        }
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new EntityNotFoundException("Hospital não encontrado"));
        User savedUser = new User();
        savedUser.setName(userRegisterRequest.name());
        savedUser.setEmail(userRegisterRequest.email());
        savedUser.setPassword(passwordEncoder.encode(userRegisterRequest.password()));
        savedUser.setRole(userRegisterRequest.role());
        savedUser.setHospital(hospital);
        savedUser = userRepository.save(savedUser);
        return new UserRegisterResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getRole(), hospitalId);
    }
}
