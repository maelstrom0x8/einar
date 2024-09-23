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

import static io.ceze.einar.tables.Users.USERS;

import io.ceze.einar.tables.records.UsersRecord;
import io.ceze.einar.user.domain.model.User;
import io.ceze.einar.user.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

@Repository
class JooqUserRepository implements UserRepository {

    private final DSLContext dsl;

    public JooqUserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public User save(User user) {
        return dsl.insertInto(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .returning()
                .fetchOne(UserRecordMapper.INSTANCE);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.dsl
                .selectFrom(USERS)
                .where(USERS.USER_ID.eq(userId))
                .fetchOptional(UserRecordMapper.INSTANCE);
    }

    @Override
    public void delete(User user) {
        this.dsl.deleteFrom(USERS).where(USERS.USER_ID.eq(user.getId())).execute();
    }

    @Override
    public void deleteById(Long userId) {
        this.dsl.deleteFrom(USERS).where(USERS.USER_ID.eq(userId)).execute();
    }

    @Override
    public List<User> findAll() {
        return this.dsl.selectFrom(USERS).fetch(UserRecordMapper.INSTANCE);
    }

    @Override
    public boolean existsById(Long userId) {
        return this.dsl.fetchExists(USERS, USERS.USER_ID.eq(userId));
    }

    @Override
    public int count() {
        return dsl.fetchCount(USERS);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dsl.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptional(UserRecordMapper.INSTANCE);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.dsl.fetchExists(USERS, USERS.EMAIL.eq(email));
    }

    @Override
    public User update(User user) {
        this.dsl
                .update(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.LAST_MODIFIED, LocalDateTime.now())
                .set(USERS.ACTIVE, user.isActive())
                .set(USERS.VERIFIED, user.isVerified())
                .where(USERS.USER_ID.eq(user.getId()))
                .execute();

        return findById(user.getId()).orElseThrow();
    }

    static class UserRecordMapper implements RecordMapper<UsersRecord, User> {
        static final UserRecordMapper INSTANCE = new UserRecordMapper();

        private UserRecordMapper() {}

        @Override
        public User map(UsersRecord r) {
            User user = new User();
            user.setId(r.getUserId());
            user.setEmail(r.getEmail());
            user.setVerified(r.getVerified());
            user.setActive(r.getActive());
            user.setCreatedAt(r.getCreatedAt());
            user.setLastModified(r.getLastModified());
            return user;
        }
    }
}
