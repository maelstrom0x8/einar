/*
 * This file is generated by jOOQ.
 */
package io.ceze.einar.tables.records;


import io.ceze.einar.tables.Users;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.users.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.users.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.users.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.users.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.users.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.users.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Users.USERS.USER_ID;
    }

    @Override
    public Field<String> field2() {
        return Users.USERS.USERNAME;
    }

    @Override
    public Field<String> field3() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Integer component1() {
        return getUserId();
    }

    @Override
    public String component2() {
        return getUsername();
    }

    @Override
    public String component3() {
        return getEmail();
    }

    @Override
    public Integer value1() {
        return getUserId();
    }

    @Override
    public String value2() {
        return getUsername();
    }

    @Override
    public String value3() {
        return getEmail();
    }

    @Override
    public UsersRecord value1(Integer value) {
        setUserId(value);
        return this;
    }

    @Override
    public UsersRecord value2(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public UsersRecord value3(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsersRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
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
    public UsersRecord(Integer userId, String username, String email) {
        super(Users.USERS);

        setUserId(userId);
        setUsername(username);
        setEmail(email);
        resetChangedOnNotNull();
    }
}
