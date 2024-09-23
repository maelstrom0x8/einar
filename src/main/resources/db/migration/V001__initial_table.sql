CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(16),
    email VARCHAR(100) NOT NULL
);

CREATE TABLE addresses (
    addr_id SERIAL PRIMARY KEY,
    addr_num INTEGER,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    UNIQUE(addr_num, street, city)
);

CREATE TABLE apartments (
    apt_id BIGSERIAL PRIMARY KEY,
    address_id BIGINT REFERENCES addresses(addr_id),
    owner_id INTEGER REFERENCES users(user_id)
);

CREATE TABLE negotiation (
    id SERIAL PRIMARY KEY,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
