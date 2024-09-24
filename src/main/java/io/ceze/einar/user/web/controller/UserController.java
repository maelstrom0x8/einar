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
package io.ceze.einar.user.web.controller;

import io.ceze.config.security.Authenticated;
import io.ceze.einar.user.domain.dto.ProfileRequest;
import io.ceze.einar.user.domain.dto.ProfileResponse;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.service.UserService;
import io.ceze.einar.util.exception.ResourceAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Authenticated User user) {
        try {
            userService.create(user.getEmail());
            return ResponseEntity.accepted().build();
        } catch (ResourceAlreadyExistException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/verify")
    public void verifyToken(@RequestParam("token") String token) {}

    @PostMapping("/profile/{profile_id}")
    public ProfileResponse updateProfile(
            @PathVariable("profile_id") Long profileId, ProfileRequest request) {
        return null;
    }

    @DeleteMapping
    public void deleteAccount(@Authenticated User user) {
        userService.deleteAccount(user);
    }
}
