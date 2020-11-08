CREATE TABLE IF NOT EXISTS users (
    id                  BIGINT UNSIGNED   NOT NULL      AUTO_INCREMENT PRIMARY KEY,

    username            VARCHAR(100)      NOT NULL,
    email               VARCHAR(100)      NOT NULL,
    password            VARCHAR(100)      NOT NULL      CHECK (char_length(password) >= 8),
    verified            BIT(1),
    active              BIT(1),
    roles               VARCHAR(100)      NOT NULL,
    registration_date   DATETIME          NOT NULL
);


CREATE TABLE IF NOT EXISTS verification_tokens (
    id                  BIGINT UNSIGNED     NOT NULL      AUTO_INCREMENT PRIMARY KEY,

    token	            VARCHAR(255)        NOT NULL,
    expiry_date	        DATETIME            NOT NULL,
    user_id	            BIGINT UNSIGNED	    NOT NULL,

    FOREIGN KEY (user_id)                   REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS application_settings (
    id                  INT UNSIGNED        NOT NULL      AUTO_INCREMENT PRIMARY KEY
);


CREATE TABLE IF NOT EXISTS contexts (
    id                  INT UNSIGNED    NOT NULL      AUTO_INCREMENT PRIMARY KEY,

    name	            VARCHAR(100)    NOT NULL,
    app_setting_id      INT UNSIGNED    NOT NULL,

    FOREIGN KEY (app_setting_id)        REFERENCES application_settings (id)
);


CREATE TABLE IF NOT EXISTS projects (
    id                  INT UNSIGNED    NOT NULL      AUTO_INCREMENT PRIMARY KEY,

    name	            VARCHAR(100)    NOT NULL,
    app_setting_id      INT UNSIGNED    NOT NULL,

    FOREIGN KEY (app_setting_id)        REFERENCES application_settings (id)
);


CREATE TABLE IF NOT EXISTS user_settings (
    id                  BIGINT UNSIGNED     NOT NULL      AUTO_INCREMENT PRIMARY KEY,

    user_id	            BIGINT UNSIGNED     NOT NULL,
    lookup_assignee_id	BIGINT UNSIGNED,
    lookup_project_id	INT UNSIGNED,
    lookup_context_id	INT UNSIGNED,
    display_done        bit(1)              NOT NULL,

    FOREIGN KEY (user_id)            REFERENCES users (id),
    FOREIGN KEY (lookup_assignee_id) REFERENCES users (id),
    FOREIGN KEY (lookup_project_id)  REFERENCES projects (id),
    FOREIGN KEY (lookup_context_id)  REFERENCES contexts (id)
);


CREATE TABLE IF NOT EXISTS todos (
    id                  BIGINT UNSIGNED     NOT NULL      AUTO_INCREMENT PRIMARY KEY,

    creator_id          BIGINT UNSIGNED     NOT NULL,
    assignee_id         BIGINT UNSIGNED     NOT NULL,
    context_id          INT UNSIGNED,
    project_id          INT UNSIGNED,

    creation_date       DATE                NOT NULL,
    due_date            DATE,
    completion_date     DATE,

    title               VARCHAR(255)        NOT NULL,
    description         VARCHAR(1000),
    history             VARCHAR(1000),
    link                VARCHAR(1000),

    priority            ENUM('MUST','HIGH','MEDIUM','LOW','NONE')                          NOT NULL,
    status              ENUM('NOT_STARTED','PROGRESS','ON_HOLD','BLOCKED','COMPLETED')     NOT NULL,

    is_deleted          bit(1)              NOT NULL,
    is_instant          bit(1)              NOT NULL,
    is_public           bit(1)              NOT NULL,

    FOREIGN KEY (creator_id)                REFERENCES users (id),
    FOREIGN KEY (assignee_id)               REFERENCES users (id),
    FOREIGN KEY (context_id)                REFERENCES contexts (id),
    FOREIGN KEY (project_id)                REFERENCES projects (id)
);



