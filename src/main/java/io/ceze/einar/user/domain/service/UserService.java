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

import io.ceze.config.security.EinarSecurityManager;
import io.ceze.einar.user.domain.model.Location;
import io.ceze.einar.user.domain.model.Profile;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.LocationRepository;
import io.ceze.einar.user.domain.repository.ProfileRepository;
import io.ceze.einar.user.domain.repository.UserRepository;
import io.ceze.einar.util.exception.ResourceAlreadyExistException;
import io.ceze.einar.util.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final ProfileRepository profileRepository;

    private final EinarSecurityManager einarSecurityManager;

    public UserService(
            UserRepository userRepository,
            LocationRepository locationRepository,
            ProfileRepository profileRepository,
            EinarSecurityManager einarSecurityManager) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.profileRepository = profileRepository;
        this.einarSecurityManager = einarSecurityManager;
    }

    public User create(String email) throws ResourceAlreadyExistException {
        try {

            if (userRepository.existsByEmail(email)) {
                log.error("Duplicates not allowed. User already exists.");
                throw new ResourceAlreadyExistException(User.class, email);
            }

            User user = userRepository.save(new User(email));
            Location location = locationRepository.save(new Location());
            Profile profile = new Profile();
            profile.setUser(user);
            profile.setLocation(location);
            profileRepository.save(profile);
            log.info("User account for {} created successfully", user.getEmail());
            return user;
        } catch (Exception e) {
            if (e instanceof ResourceAlreadyExistException) throw e;
            log.error("Unable to save user account. {}", e.getMessage());
        }
        return null;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void toggleUserActiveStatus(User user, Long id) {
        user.setActive(!user.isActive());
        userRepository.update(user);
    }

    /// Deletes the user account and all attached resources.
    /// @param user The current authenticated user
    public void deleteAccount(User user) {
        userRepository.delete(user);
    }
}
