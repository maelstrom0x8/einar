/*
 * This file is generated by jOOQ.
 */
package io.ceze.einar.tables.records;


import io.ceze.einar.tables.Users;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record6<Long, LocalDateTime, LocalDateTime, Boolean, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.users.user_id</code>.
     */
    public void setUserId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.users.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.users.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.users.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>public.users.last_modified</code>.
     */
    public void setLastModified(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.users.last_modified</code>.
     */
    public LocalDateTime getLastModified() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.users.active</code>.
     */
    public void setActive(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.users.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>public.users.verified</code>.
     */
    public void setVerified(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.users.verified</code>.
     */
    public Boolean getVerified() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>public.users.email</code>.
     */
    public void setEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.users.email</code>.
     */
    public String getEmail() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, LocalDateTime, LocalDateTime, Boolean, Boolean, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, LocalDateTime, LocalDateTime, Boolean, Boolean, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Users.USERS.USER_ID;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return Users.USERS.CREATED_AT;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Users.USERS.LAST_MODIFIED;
    }

    @Override
    public Field<Boolean> field4() {
        return Users.USERS.ACTIVE;
    }

    @Override
    public Field<Boolean> field5() {
        return Users.USERS.VERIFIED;
    }

    @Override
    public Field<String> field6() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Long component1() {
        return getUserId();
    }

    @Override
    public LocalDateTime component2() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime component3() {
        return getLastModified();
    }

    @Override
    public Boolean component4() {
        return getActive();
    }

    @Override
    public Boolean component5() {
        return getVerified();
    }

    @Override
    public String component6() {
        return getEmail();
    }

    @Override
    public Long value1() {
        return getUserId();
    }

    @Override
    public LocalDateTime value2() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime value3() {
        return getLastModified();
    }

    @Override
    public Boolean value4() {
        return getActive();
    }

    @Override
    public Boolean value5() {
        return getVerified();
    }

    @Override
    public String value6() {
        return getEmail();
    }

    @Override
    public UsersRecord value1(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UsersRecord value2(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public UsersRecord value3(LocalDateTime value) {
        setLastModified(value);
        return this;
    }

    @Override
    public UsersRecord value4(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public UsersRecord value5(Boolean value) {
        setVerified(value);
        return this;
    }

    @Override
    public UsersRecord value6(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsersRecord values(Long value1, LocalDateTime value2, LocalDateTime value3, Boolean value4, Boolean value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(Long userId, LocalDateTime createdAt, LocalDateTime lastModified, Boolean active, Boolean verified, String email) {
        super(Users.USERS);

        setUserId(userId);
        setCreatedAt(createdAt);
        setLastModified(lastModified);
        setActive(active);
        setVerified(verified);
        setEmail(email);
        resetChangedOnNotNull();
    }
}
