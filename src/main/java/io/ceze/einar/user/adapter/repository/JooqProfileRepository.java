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

import static io.ceze.einar.tables.Profiles.PROFILES;
import static io.ceze.einar.tables.Users.USERS;
import static org.jooq.impl.DSL.asterisk;

import io.ceze.einar.tables.records.ProfilesRecord;
import io.ceze.einar.user.domain.model.Address;
import io.ceze.einar.user.domain.model.Profile;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.ProfileRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

@Repository
class JooqProfileRepository implements ProfileRepository {

    private final DSLContext dsl;

    JooqProfileRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<Profile> findByUserId(Long userId) {
        Optional<Record> record =
                this.dsl
                        .select(asterisk())
                        .from(PROFILES)
                        .innerJoin(USERS)
                        .on(USERS.USER_ID.eq(PROFILES.PROFILE_ID))
                        .where(USERS.USER_ID.eq(userId))
                        .fetchOptional();

        return record.map(r -> mapRecordToProfile().apply(r));
    }

    @Override
    public Profile update(Profile profile) {
        this.dsl
                .update(PROFILES)
                .set(PROFILES.FIRST_NAME, profile.getFirstName())
                .set(PROFILES.LAST_NAME, profile.getLastName())
                .set(PROFILES.DOB, profile.getDateOfBirth())
                .set(PROFILES.STREET_NO, profile.getAddress().getStreetNumber())
                .set(PROFILES.STREET_NAME, profile.getAddress().getStreet())
                .set(PROFILES.CITY, profile.getAddress().getCity())
                .set(PROFILES.STATE, profile.getAddress().getState())
                .set(PROFILES.POSTAL_CODE, profile.getAddress().getPostalCode())
                .set(PROFILES.COUNTRY, profile.getAddress().getCountry())
                .set(PROFILES.LAST_MODIFIED, LocalDateTime.now())
                .where(PROFILES.USER_ID.eq(profile.getUser().getId()))
                .execute();

        return findByUserId(profile.getUser().getId()).orElseThrow();
    }

    @Override
    public Profile save(Profile profile) {
        this.dsl
                .insertInto(PROFILES)
                .set(PROFILES.USER_ID, profile.getUser().getId())
                .set(PROFILES.FIRST_NAME, profile.getFirstName())
                .set(PROFILES.LAST_NAME, profile.getLastName())
                .set(PROFILES.DOB, profile.getDateOfBirth())
                .set(PROFILES.STREET_NO, profile.getAddress().getStreetNumber())
                .set(PROFILES.STREET_NAME, profile.getAddress().getStreet())
                .set(PROFILES.CITY, profile.getAddress().getCity())
                .set(PROFILES.STATE, profile.getAddress().getState())
                .set(PROFILES.POSTAL_CODE, profile.getAddress().getPostalCode())
                .set(PROFILES.COUNTRY, profile.getAddress().getCountry())
                .set(PROFILES.CREATED_AT, LocalDateTime.now())
                .set(PROFILES.LAST_MODIFIED, LocalDateTime.now())
                .execute();

        return profile;
    }

    @Override
    public Optional<Profile> findById(Long profileId) {
        return this.dsl
                .selectFrom(PROFILES)
                .where(PROFILES.PROFILE_ID.eq(profileId))
                .fetchOptional(ProfileRecordMapper.INSTANCE);
    }

    @Override
    public void delete(Profile profile) {
        this.dsl
                .deleteFrom(PROFILES)
                .where(PROFILES.USER_ID.eq(profile.getUser().getId()))
                .execute();
    }

    @Override
    public void deleteById(Long profileId) {
        this.dsl.deleteFrom(PROFILES).where(PROFILES.PROFILE_ID.eq(profileId)).execute();
    }

    @Override
    public List<Profile> findAll() {
        return this.dsl.selectFrom(PROFILES).fetch(ProfileRecordMapper.INSTANCE);
    }

    @Override
    public boolean existsById(Long profileId) {
        return this.dsl.fetchExists(PROFILES, PROFILES.PROFILE_ID.eq(profileId));
    }

    @Override
    public int count() {
        return this.dsl.fetchCount(PROFILES);
    }

    private Function<? super Record, Profile> mapRecordToProfile() {
        return record -> {
            Profile profile = new Profile();
            profile.setFirstName(record.get(PROFILES.FIRST_NAME));
            profile.setLastName(record.get(PROFILES.LAST_NAME));
            profile.setDateOfBirth(record.get(PROFILES.DOB));
            profile.setCreatedAt(record.get(PROFILES.CREATED_AT));
            profile.setId(record.get(PROFILES.PROFILE_ID));
            profile.setLastModified(record.get(PROFILES.LAST_MODIFIED));

            User user = new User();
            user.setId(record.get(PROFILES.USER_ID));

            Address address = new Address();
            address.setStreetNumber(record.get(PROFILES.STREET_NO));
            address.setStreet(record.get(PROFILES.STREET_NAME));
            address.setCity(record.get(PROFILES.CITY));
            address.setState(record.get(PROFILES.STATE));
            address.setPostalCode(record.get(PROFILES.POSTAL_CODE));
            address.setCountry(record.get(PROFILES.COUNTRY));

            profile.setUser(user);
            profile.setAddress(address);
            return profile;
        };
    }

    static class ProfileRecordMapper implements RecordMapper<ProfilesRecord, Profile> {

        static final ProfileRecordMapper INSTANCE = new ProfileRecordMapper();

        private ProfileRecordMapper() {}

        @Override
        public Profile map(ProfilesRecord record) {
            Profile profile = new Profile();
            profile.setFirstName(record.getFirstName());
            profile.setLastName(record.getLastName());
            profile.setDateOfBirth(record.getDob());
            profile.setCreatedAt(record.getCreatedAt());
            profile.setId(record.getProfileId());
            profile.setLastModified(record.getLastModified());

            User user = new User();
            user.setId(record.getUserId());

            Address address = new Address();
            address.setStreetNumber(record.getStreetNo());
            address.setStreet(record.getStreetName());
            address.setCity(record.getCity());
            address.setState(record.getState());
            address.setPostalCode(record.getPostalCode());
            address.setCountry(record.getCountry());

            profile.setUser(user);
            profile.setAddress(address);
            return profile;
        }
    }
}
