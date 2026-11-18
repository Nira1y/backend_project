CREATE TABLE IF NOT EXISTS "user" (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    role          VARCHAR(20)  NOT NULL
);

ALTER TABLE recipes ADD COLUMN IF NOT EXISTS owner_id BIGINT;

INSERT INTO "user" (username, password_hash, role)
SELECT 'test_owner', '$2a$10$abcdefghijklmnopqrstuv', 'USER'
WHERE NOT EXISTS (SELECT 1 FROM "user");

UPDATE recipes
SET owner_id = (SELECT id FROM "user" ORDER BY id LIMIT 1)
WHERE owner_id IS NULL;

ALTER TABLE recipes ALTER COLUMN owner_id SET NOT NULL;

ALTER TABLE recipes
    ADD CONSTRAINT fk_recipes_owner
    FOREIGN KEY (owner_id) REFERENCES "user" (id);