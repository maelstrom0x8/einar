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
package io.ceze.einar.user.adapter.repository;

import io.ceze.einar.user.domain.model.Token;
import io.ceze.einar.user.domain.repository.TokenRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
class JooqTokenRepository implements TokenRepository {
    @Override
    public Token save(Token token) {
        return null;
    }

    @Override
    public Optional<Token> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void delete(Token token) {}

    @Override
    public void deleteById(UUID uuid) {}

    @Override
    public List<Token> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }
}
