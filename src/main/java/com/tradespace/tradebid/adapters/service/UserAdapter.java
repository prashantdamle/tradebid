package com.tradespace.tradebid.adapters.service;

import com.tradespace.tradebid.adapters.db.UserEntity;
import com.tradespace.tradebid.adapters.db.UserRepository;
import com.tradespace.tradebid.domain.exceptions.UserNotFoundException;
import com.tradespace.tradebid.domain.model.User;
import com.tradespace.tradebid.domain.ports.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).map(UserEntity::toUser)
                .orElseThrow(() -> new UserNotFoundException(userName));
    }

}
