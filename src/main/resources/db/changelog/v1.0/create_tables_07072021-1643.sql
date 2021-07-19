CREATE TABLE IF NOT EXISTS discipline (
    id INT PRIMARY KEY,
    title VARCHAR(256) NOT NULL UNIQUE
);
INSERT INTO discipline (id, title) VALUES (0, 'JAVA') ON CONFLICT DO NOTHING;
INSERT INTO discipline (id, title) VALUES (1, 'CSHARP') ON CONFLICT DO NOTHING;
INSERT INTO discipline (id, title) VALUES (2, 'CPP') ON CONFLICT DO NOTHING;
INSERT INTO discipline (id, title) VALUES (3, 'PYTHON') ON CONFLICT DO NOTHING;
CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    role INT DEFAULT 0
);
CREATE TABLE IF NOT EXISTS student (
    account_id INT PRIMARY KEY REFERENCES account(id) ON DELETE CASCADE,
    first_name VARCHAR(64),
    middle_name VARCHAR(64),
    last_name VARCHAR(64),
    university VARCHAR(256),
    faculty VARCHAR(256),
    direction VARCHAR(256),
    grade INT CHECK (grade > 0 AND grade < 8),
    group_number INT CHECK (group_number > 0)
);
CREATE TABLE IF NOT EXISTS test (
    id SERIAL PRIMARY KEY,
    discipline INT NOT NULL REFERENCES discipline(id) ON DELETE CASCADE,
    creator INT NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    title VARCHAR(256) NOT NULL,
    time_start TIMESTAMP,
    time_end TIMESTAMP CHECK (time_end IS NULL OR time_end > time_start)
);
CREATE TABLE IF NOT EXISTS question (
    id SERIAL PRIMARY KEY,
    test_id INT NOT NULL REFERENCES test(id) ON DELETE CASCADE,
    title VARCHAR(2048) NOT NULL,
    weight INT NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS choice (
    id SERIAL PRIMARY KEY,
    question_id INT NOT NULL REFERENCES question(id) ON DELETE CASCADE,
    number INT NOT NULL,
    title VARCHAR(256) NOT NULL,
    correct BOOLEAN DEFAULT FALSE
);
CREATE TABLE IF NOT EXISTS variant (
    id SERIAL PRIMARY KEY,
    test_id INT NOT NULL REFERENCES test(id) ON DELETE CASCADE,
    number INT NOT NULL
);
CREATE TABLE IF NOT EXISTS variant_config (
    variant_id INT REFERENCES variant(id) ON DELETE CASCADE,
    question_id INT REFERENCES question(id) ON DELETE CASCADE,
    number INT NOT NULL,
    PRIMARY KEY (variant_id, question_id)
);
CREATE TABLE IF NOT EXISTS response (
    id SERIAL PRIMARY KEY,
    respondent_id INT NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    test_id INT NOT NULL REFERENCES test(id) ON DELETE CASCADE,
    variant_id INT NOT NULL REFERENCES variant(id) ON DELETE CASCADE,
    time_start TIMESTAMP NOT NULL DEFAULT NOW(),
    time_end TIMESTAMP,
    UNIQUE (respondent_id, test_id)
);
CREATE TABLE IF NOT EXISTS answer (
    response_id INT NOT NULL REFERENCES response(id) ON DELETE CASCADE,
    choice_id INT NOT NULL REFERENCES choice(id) ON DELETE CASCADE,
    PRIMARY KEY (response_id, choice_id)
)

