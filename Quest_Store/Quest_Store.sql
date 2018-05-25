CREATE TABLE roles (
	role_id SERIAL PRIMARY KEY,
	role_name TEXT
);

CREATE TABLE users (
	user_id SERIAL PRIMARY KEY,
	role_id INTEGER NOT NULL REFERENCES roles(role_id),
	first_name TEXT,
	last_name TEXT,
	login TEXT UNIQUE,
	email TEXT UNIQUE,
	password INTEGER
);

CREATE TABLE classes (
	class_id SERIAL PRIMARY KEY,
	class_name TEXT
);

CREATE TABLE levels (
	level_id SERIAL PRIMARY KEY,
	level_req_balance INTEGER
);

CREATE TABLE students (
	user_id INTEGER NOT NULL REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	class_id INTEGER NOT NULL REFERENCES classes(class_id) ON UPDATE CASCADE,
	level_id INTEGER NOT NULL REFERENCES levels(level_id),	
	github TEXT,
	balance INTEGER,
	earned_coolcoins INTEGER
);

CREATE TABLE quests (
	quest_id SERIAL PRIMARY KEY,
	quest_category TEXT,
	quest_name TEXT,
	quest_description TEXT,
	quest_reward INTEGER
); 

CREATE TABLE students_quests (
	user_id INTEGER NOT NULL REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	quest_id INTEGER NOT NULL REFERENCES quests(quest_id) ON UPDATE CASCADE,
	is_completed BOOLEAN DEFAULT FALSE,
	PRIMARY KEY(user_id, quest_id)
);

CREATE TABLE artifacts (
	artifact_id SERIAL PRIMARY KEY,
	name TEXT,
	price INTEGER,
	description TEXT
);

CREATE TABLE students_artifacts (
	user_id INTEGER NOT NULL REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	artifact_id INTEGER NOT NULL REFERENCES artifacts(artifact_id) ON UPDATE CASCADE,
	is_used BOOLEAN DEFAULT FALSE,
	PRIMARY KEY(user_id, artifact_id)
);

CREATE TABLE mentors (
	user_id INTEGER NOT NULL REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE mentors_classes (
	user_id INTEGER NOT NULL REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	class_id INTEGER NOT NULL REFERENCES classes(class_id) ON UPDATE CASCADE,
	PRIMARY KEY(user_id, class_id)
);	
