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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.ceze.einar.user.domain.model.Profile;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.LocationRepository;
import io.ceze.einar.user.domain.repository.ProfileRepository;
import io.ceze.einar.user.domain.repository.UserRepository;
import io.ceze.einar.util.exception.ResourceAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks private UserService userService;

    @Mock private UserRepository userRepository;

    @Mock private ProfileRepository profileRepository;

    @Mock private LocationRepository locationRepository;

    @Test
    void shouldCreateNewUser() {
        User user = new User("bob@einar.org");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User user1 = userService.create("bob@einar.org");

        assertThat(user1).isNotNull();
        assertThat(user1.getEmail()).isEqualTo("bob@einar.org");
    }

    @Test
    void exceptionIsThrownWhenCreateUserWithExistingEmail() {
        String email = "bob@einar.org";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThatThrownBy(() -> userService.create(email))
                .isInstanceOf(ResourceAlreadyExistException.class)
                .hasMessage("The User identified with 'bob@einar.org' already exists");
    }

    @Test
    void blankProfileIsAttachedForNewUser() {
        User alice = new User("alice@foo.com");
        Profile profile = new Profile();
        when(userRepository.save(any(User.class))).thenReturn(alice);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileRepository.findByUserId(any(Long.class))).thenReturn(Optional.of(profile));

        User user = userService.create("alice@foo.com");
        Optional<Profile> profile1 = profileRepository.findByUserId(323L);

        assertThat(user).isNotNull();
        assertThat(profile1).isNotEmpty();
        assertThat(profile1).map(Profile::getLocation).isNotNull();

    }
}
