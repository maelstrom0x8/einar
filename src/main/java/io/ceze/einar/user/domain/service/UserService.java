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

import io.ceze.einar.user.domain.dto.ProfileRequest;
import io.ceze.einar.user.domain.dto.ProfileResponse;
import io.ceze.einar.user.domain.model.Location;
import io.ceze.einar.user.domain.model.Profile;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.LocationRepository;
import io.ceze.einar.user.domain.repository.ProfileRepository;
import io.ceze.einar.user.domain.repository.UserRepository;
import io.ceze.einar.util.exception.ResourceAlreadyExistException;
import io.ceze.einar.util.exception.UserNotFoundException;
import io.ceze.events.UserCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final ProfileRepository profileRepository;

    private final ApplicationEventPublisher eventPublisher;

    public UserService(
            UserRepository userRepository,
            LocationRepository locationRepository,
            ProfileRepository profileRepository,
            org.springframework.context.ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.profileRepository = profileRepository;
        this.eventPublisher = eventPublisher;
    }

    public void create(String email) throws ResourceAlreadyExistException {
        if (userRepository.existsByEmail(email)) {
            log.error("Duplicates not allowed. User already exists.");
            throw new ResourceAlreadyExistException(User.class, email);
        }

        try {
            User user = userRepository.save(new User(email));
            Location location = locationRepository.save(new Location());

            Profile profile = new Profile();
            profile.setUser(user);
            profile.setLocation(location);
            profileRepository.save(profile);

            log.info("User account for {} created successfully", user.getEmail());
            eventPublisher.publishEvent(new UserCreated(email));

        } catch (ResourceAlreadyExistException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save user account. {}", e.getMessage());
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void toggleUserActiveStatus(User usr, Long id) throws IllegalAccessException {
        User user = getUserById(id);
        if (!usr.getId().equals(user.getId()))
            throw new IllegalAccessException("Cannot update another account");
        boolean b = user.isActive();
        log.info("{} account for {}", b ? "Activating" : "Deactivating", user.getEmail());
        user.setActive(!b);
        userRepository.update(user);
        log.info("Account {} {} successfully", user.getEmail(), !b ? "deactivated" : "activated");
    }

    /// Deletes the user account and all attached resources.
    /// @param user The current authenticated user
    public void deleteAccount(User user) {
        userRepository.delete(user);
    }

    public ProfileResponse updateProfile(User user, ProfileRequest request) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow();
        log.info("Updating user profile pf_{}_{}", user.getId(), profile.getId());
        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setDateOfBirth(request.dateOfBirth());
        if (request.locationInfo() != null) {
            ProfileRequest.LocationInfo info = request.locationInfo();
            log.info("Updating location details <{}>", user.getEmail());
            Location location = profile.getLocation();
            location.setStreetNumber(info.streetNumber());
            location.setStreet(info.streetName());
            location.setCity(info.city());
            location.setState(info.state());
            location.setCountry(info.country());
            location.setPostalCode(info.postalCode());
            var loc = locationRepository.update(location);
            profile.setLocation(loc);
        }
        var pf = profileRepository.update(profile);
        return ProfileResponse.from(pf);
    }
}
