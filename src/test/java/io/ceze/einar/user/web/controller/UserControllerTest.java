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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.ceze.einar.user.domain.service.UserService;
import io.ceze.mail.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@WebMvcTest(controllers = UserController.class)
@Import({UserController.class, MailService.class})
class UserControllerTest {

    @MockBean UserService userService;

    @Autowired MockMvc mockMvc;

    @Test
    void accountIsCreatedForAuthenticatedUser() throws Exception {
        mockMvc.perform(
                        post("/v1/users/register")
                                .with(jwt().jwt(j -> j.claim("sub", "bob@einar.org"))))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.ACCEPTED.value()));

        verify(userService, times(1)).create("bob@einar.org");
    }
}
