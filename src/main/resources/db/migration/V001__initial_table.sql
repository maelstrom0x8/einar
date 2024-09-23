CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    verified BOOLEAN DEFAULT FALSE,
    email VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE profiles (
    profile_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    dob DATE,
    street_no INTEGER,
    street_name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(20) NOT NULL,
    country CHAR(2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP NOT NULL,
    PRIMARY KEY (profile_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
