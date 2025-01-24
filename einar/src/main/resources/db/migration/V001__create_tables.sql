
CREATE TYPE state AS ENUM ('CLOSED', 'OPEN', 'ARCHIVED');

CREATE TABLE inventories (
	inventory_id SERIAL NOT NULL,
	name VARCHAR(100) NOT NULL,
	account_id INT NOT NULL,
	description TEXT,
	state state NOT NULL DEFAULT 'OPEN',
	created_at TIMESTAMP DEFAULT now(),
	last_updated TIMESTAMP NOT NULL DEFAULT now(),
	tenant_id INT NOT NULL,

	UNIQUE (tenant_id, name),
	PRIMARY KEY(inventory_id)
);

CREATE TABLE items (
	item_id SERIAL PRIMARY KEY,
	inventory_id INT,
	name VARCHAR(100) NOT NULL UNIQUE,
	threshold INT NOT NULL,
	description TEXT,
	sku VARCHAR(12) NOT NULL UNIQUE,
	created_at TIMESTAMP NOT NULL,
	last_updated TIMESTAMP NOT NULL,

	UNIQUE (inventory_id, name),
	CONSTRAINT fk_inventory FOREIGN KEY (inventory_id)
		REFERENCES inventories(inventory_id) ON DELETE CASCADE
);

CREATE TABLE customers (
	customer_id BIGSERIAL PRIMARY KEY,
	name VARCHAR(100),
	email VARCHAR(100) UNIQUE,
	phone VARCHAR(20),
	created_at TIMESTAMP NOT NULL DEFAULT now(),
	last_updated TIMESTAMP NOT NULL DEFAULT now(),
	tenant_id INT NOT NULL
);
