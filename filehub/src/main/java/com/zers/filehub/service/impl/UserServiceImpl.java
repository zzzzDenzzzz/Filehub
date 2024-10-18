package com.zers.filehub.service.impl;

import com.zers.filehub.entity.ConfirmationEntity;
import com.zers.filehub.entity.CredentialEntity;
import com.zers.filehub.entity.RoleEntity;
import com.zers.filehub.entity.UserEntity;
import com.zers.filehub.enumiration.Authority;
import com.zers.filehub.enumiration.EventType;
import com.zers.filehub.event.UserEvent;
import com.zers.filehub.exception.ApiException;
import com.zers.filehub.repository.ConfirmationRepository;
import com.zers.filehub.repository.CredentialRepository;
import com.zers.filehub.repository.RoleRepository;
import com.zers.filehub.repository.UserRepository;
import com.zers.filehub.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.zers.filehub.utils.UserUtils.createUserEntity;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    // private final BCryptPasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        var userEntity = userRepository.save(createNewUser(firstName, lastName, email));
        var credentialEntity = new CredentialEntity(userEntity, password);
        credentialRepository.save(credentialEntity);
        var confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(userEntity, EventType.REGISTRATION, Map.of("key", confirmationEntity.getKey())));
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(() -> new ApiException("Role not found"));
    }

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleName(Authority.USER.name());
        return createUserEntity(firstName, lastName, email, role);
    }
}
