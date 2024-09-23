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
package io.ceze.einar.space.adapter.repository;

import io.ceze.einar.space.domain.model.Space;
import io.ceze.einar.space.domain.repository.SpaceRepository;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class JooqSpaceRepository implements SpaceRepository {

    private final DSLContext dsl;

    JooqSpaceRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Space save(Space space) {
        return null;
    }

    @Override
    public Optional<Space> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void delete(Space space) {}

    @Override
    public void deleteById(Long aLong) {}

    @Override
    public List<Space> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }
}
