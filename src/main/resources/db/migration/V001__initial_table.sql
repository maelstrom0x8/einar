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
    street_no INTEGER,
    street_name VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
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
