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
package io.ceze.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class OAuth2SecurityManager {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2SecurityManager.class);

    public AuthenticationId authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt principal = (Jwt) authentication.getPrincipal();

        try {
            Long userId = principal.getClaim("email");
            return new AuthenticationId(userId.intValue(), principal.getSubject());
        } catch (Exception e) {
            LOG.error("Error extracting the 'user_id' claim");
        }

        return null;
    }
}
