CREATE TABLE audit_logs (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID        NOT NULL,
    action       VARCHAR(50) NOT NULL,
    client_id    UUID,
    requester_id UUID,
    detail       TEXT,
    ip_address   VARCHAR(45) NOT NULL,
    created_at   TIMESTAMP   NOT NULL,

    CONSTRAINT fk_actor_id  FOREIGN KEY (user_id)      REFERENCES users(id),
    CONSTRAINT fk_client    FOREIGN KEY (client_id)    REFERENCES clients(id),
    CONSTRAINT fk_requester FOREIGN KEY (requester_id) REFERENCES access_requests(id)
);
