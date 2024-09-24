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

import io.ceze.einar.user.domain.model.Location;
import io.ceze.einar.user.domain.repository.LocationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JooqLocationRepository implements LocationRepository {
    @Override
    public Location save(Location location) {
        return null;
    }

    @Override
    public Optional<Location> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void delete(Location location) {}

    @Override
    public void deleteById(Long aLong) {}

    @Override
    public List<Location> findAll() {
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

    @Override
    public Location update(Location location) {
        return null;
    }
}
