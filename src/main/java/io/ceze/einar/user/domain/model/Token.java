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
package io.ceze.einar.user.domain.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.util.Assert;

public class Token implements Serializable {

    private User user;
    private UUID tokenId;
    private String value;
    private LocalDateTime createdAt;
    private boolean expired;
    private Duration duration;

    private Token() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public static class Builder {

        private final Token token;

        private Builder() {
            this.token = new Token();
        }

        public static Builder withDuration(Duration duration) {
            Builder b = new Builder();
            b.token.setDuration(duration);
            b.token.setTokenId(UUID.randomUUID());
            return b;
        }

        public Builder value(String value) {
            this.token.setValue(token.getTokenId().toString() + "/" + value);
            return this;
        }

        public Builder user(User user) {
            this.token.setUser(user);
            return this;
        }

        public Token build() {
            Assert.notNull(this.token.duration, () -> "Token duration cannot be null");
            Assert.notNull(this.token.user.getId(), () -> "User id must be present");
            this.token.setCreatedAt(LocalDateTime.now());
            this.token.setExpired(false);
            return this.token;
        }
    }
}
