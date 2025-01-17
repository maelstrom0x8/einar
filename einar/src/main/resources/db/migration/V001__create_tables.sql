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
