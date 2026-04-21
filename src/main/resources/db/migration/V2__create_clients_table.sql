CREATE TABLE clients (
    id                 UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name               VARCHAR(255) NOT NULL,
    anydesk_id         VARCHAR(255),
    anydesk_password   TEXT,
    teamviewer_id      VARCHAR(255),
    teamviewer_password TEXT,
    anyviewer_id       VARCHAR(255),
    anyviewer_password TEXT,
    server             VARCHAR(20)  NOT NULL,
    server_username    VARCHAR(255) NOT NULL,
    server_password    TEXT         NOT NULL,
    db_user            VARCHAR(255),
    db_password        TEXT,
    notes              TEXT,
    sentinel_url       VARCHAR(255),
    sentinel_token     TEXT,
    active             BOOLEAN      NOT NULL,
    user_id            UUID         NOT NULL,
    created_at         TIMESTAMP    NOT NULL,

    CONSTRAINT fk_created_user_by FOREIGN KEY (user_id) REFERENCES users(id)
);
