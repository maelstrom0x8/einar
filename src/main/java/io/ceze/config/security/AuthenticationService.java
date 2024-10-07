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

import io.ceze.einar.user.domain.model.Token;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.TokenRepository;
import io.ceze.einar.user.domain.repository.UserRepository;
import io.ceze.events.UserCreated;
import java.time.Duration;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AuthenticationService {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
  private final TokenRepository tokenRepository;

  private final UserRepository userRepository;

  public AuthenticationService(TokenRepository tokenRepository, UserRepository userRepository) {
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
  }

  @Async
  @TransactionalEventListener
  void onUserCreated(UserCreated event) {
    log.info("Processing event {}", event);
  }

  public AuthenticatedUser authenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Jwt principal = (Jwt) authentication.getPrincipal();

    Map<String, Object> claims = principal.getClaims();
    return new AuthenticatedUser(principal.getSubject(), claims);
  }

  public Token generateToken(User user) {
    Token token = new Token(user, "", Duration.ofHours(2), false);
    return tokenRepository.save(token);
  }

  public void verifyToken(String token) {
    String[] id_value = token.split("/");
    if (id_value.length < 2) {
      throw new IllegalArgumentException("Invalid token format");
    }

    try {
      Token token1 = tokenRepository
          .findById(1)
          .orElseThrow(() -> new IllegalArgumentException("Token not found"));

      if (!token1.getValue().equals(id_value[1])) {
        throw new RuntimeException("Problem with token contents");
      } else {
        token1.getUser().setVerified(true);
        userRepository.save(token1.getUser());
        tokenRepository.save(token1);
      }

    } catch (IllegalArgumentException | NullPointerException e) {
      throw new IllegalArgumentException("Invalid token or format", e);
    }
  }
}
