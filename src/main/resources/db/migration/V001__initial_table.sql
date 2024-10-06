CREATE SEQUENCE profiles_id_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE users_id_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE locations_id_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE spaces_id_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    verified BOOLEAN DEFAULT FALSE,
    email VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE locations (
    location_id BIGSERIAL PRIMARY KEY,
    street_no VARCHAR(100),
    street_name VARCHAR(255),
    city VARCHAR(20),
    state VARCHAR(20),
    postal_code VARCHAR(8),
    country CHAR(2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE profiles (
    profile_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    location_id BIGINT,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    dob DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE SET NULL
);

CREATE TABLE spaces (
    space_id BIGSERIAL PRIMARY KEY,
    created_by BIGINT NOT NULL,
    location_id BIGINT,
    description TEXT,
    price NUMERIC(10, 3),
    FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE SET NULL
);

CREATE OR REPLACE FUNCTION update_expiration()
RETURNS TRIGGER AS $$
BEGIN
    NEW.expires_at := NEW.created_at + NEW.duration;
    NEW.expired := NEW.expires_at <= NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE tokens (
    token_id BIGSERIAL PRIMARY KEY,
    value TEXT NOT NULL,
    user_id BIGINT REFERENCES users(user_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duration INTERVAL NOT NULL,
    expires_at TIMESTAMP,
    expired BOOLEAN
);

CREATE TRIGGER before_insert_update
BEFORE INSERT OR UPDATE ON tokens
FOR EACH ROW
EXECUTE FUNCTION update_expiration();
