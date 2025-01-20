CREATE TABLE accounts (
  account_id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE inventories (
	inventory_id SERIAL NOT NULL,
	account_id INT,
	name VARCHAR(100) NOT NULL UNIQUE,
	created_at TIMESTAMP DEFAULT now(),
	last_updated TIMESTAMP NOT NULL,

	PRIMARY KEY(inventory_id),
	CONSTRAINT fk_account FOREIGN KEY (account_id)
		REFERENCES accounts(account_id) ON DELETE CASCADE
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

	CONSTRAINT fk_inventory FOREIGN KEY (inventory_id)
		REFERENCES inventories(inventory_id) ON DELETE CASCADE
);
