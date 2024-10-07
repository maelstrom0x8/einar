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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.ceze.einar.user.domain.dto.ProfileRequest;
import io.ceze.einar.user.domain.dto.ProfileResponse;
import io.ceze.einar.user.domain.model.Location;
import io.ceze.einar.user.domain.model.Profile;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.LocationRepository;
import io.ceze.einar.user.domain.repository.ProfileRepository;
import io.ceze.einar.user.domain.repository.UserRepository;
import io.ceze.einar.util.exception.ResourceAlreadyExistException;
import io.ceze.events.UserCreated;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ProfileRepository profileRepository;

  @Mock
  private LocationRepository locationRepository;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @Test
  void shouldCreateNewUser() {
    User user = new User("bob@einar.org");
    Profile profile = new Profile();
    Location location = new Location();

    when(locationRepository.save(any(Location.class))).thenReturn(location);
    when(profileRepository.save(any(Profile.class))).thenReturn(profile);
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.create("bob@einar.org");

    verify(userRepository).existsByEmail("bob@einar.org");
    verify(userRepository).save(any(User.class));
    verify(locationRepository).save(any(Location.class));
    verify(profileRepository).save(any(Profile.class));
    verify(eventPublisher).publishEvent(any(UserCreated.class));
    verify(eventPublisher, times(1)).publishEvent(new UserCreated(user.getEmail()));
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

    userService.create("alice@foo.com");
    Optional<Profile> profile1 = profileRepository.findByUserId(323L);

    // assertThat(user).isNotNull();
    assertThat(profile1).isNotEmpty();
    assertThat(profile1).map(Profile::getLocation).isNotNull();
  }

  @Test
  void userActiveStatusIsUpdated() {
    User bob = new User("bob@foo.com");
    bob.setActive(false);
    bob.setId(1L);
    User alice = new User("alice@foo.com");
    alice.setActive(true);
    alice.setId(2L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(bob));
    when(userRepository.findById(2L)).thenReturn(Optional.of(alice));

    assertDoesNotThrow(() -> userService.toggleUserActiveStatus(bob, 1L));
    assertDoesNotThrow(() -> userService.toggleUserActiveStatus(alice, 2L));

    assertThat(bob.isActive()).isTrue();
    assertThat(alice.isActive()).isFalse();
  }

  @Test
  void exceptionIsThrownWhenUpdatingAnotherUserAccount() {
    User bob = new User("bob@foo.com");
    bob.setId(1L);

    User alice = new User("alice@foo.com");
    alice.setActive(true);
    alice.setId(2L);

    when(userRepository.findById(2L)).thenReturn(Optional.of(alice));

    assertThatThrownBy(() -> userService.toggleUserActiveStatus(bob, 2L))
        .isInstanceOf(IllegalAccessException.class);
  }

  @Test
  void profileIsUpdatedForExistingUser() {
    User user = new User("bob@foo.com");
    user.setId(93873L);
    Profile profile = new Profile();
    profile.setId(9204L);
    profile.setUser(user);
    profile.setLocation(new Location());

    ProfileRequest request = new ProfileRequest(
        "Bob",
        "Smith",
        LocalDate.of(1989, 4, 8),
        new ProfileRequest.LocationInfo(12, "street-name", "city", "state", "XXXXXX", "JPN"));

    when(userRepository.save(any(User.class))).thenReturn(user);
    when(profileRepository.save(any(Profile.class))).thenReturn(profile);
    when(profileRepository.findByUserId(any())).thenReturn(Optional.of(profile));

    when(locationRepository.save(any(Location.class))).thenReturn(profile.getLocation());

    userService.create(user.getEmail());

    ProfileResponse response = userService.updateProfile(user, request);

    assertThat(response).isNotNull();
    assertThat(response.firstName()).isEqualTo("Bob");
    System.out.println(response);
  }
}
