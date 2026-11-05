CREATE TABLE ingredients (
    id BIGSERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity DOUBLE PRECISION,
    calories INTEGER,
    CONSTRAINT fk_ingredient_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);