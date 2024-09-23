/*
 * Copyright (C) 2024 Emmanuel Godwin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ceze.einar.user.domain.service;

import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.UserRepository;
import io.ceze.einar.util.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String email) {
        User user = null;
        try {
            user = userRepository.save(new User(email));
            log.info("User account for {} created successfully", user.getEmail());
            return user;
        } catch (Exception e) {
            log.error("Unable to save user account. {}", e.getMessage());
        }
        return null;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void toggleUserActiveStatus(Long id) {
        User user = getUserById(id);
        user.setActive(!user.isActive());
        userRepository.update(user);
    }

    public void deleteAccount(Long userId) {
        userRepository.deleteById(userId);
    }
}
