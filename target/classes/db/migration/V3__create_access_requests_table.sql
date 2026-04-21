CREATE TABLE access_requests (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id    UUID        NOT NULL,
    requester_id UUID        NOT NULL,
    approver_id  UUID,
    status       VARCHAR(20),
    reason       TEXT,
    requested_at TIMESTAMP   NOT NULL,
    responded_at TIMESTAMP,
    expires_at   TIMESTAMP,

    CONSTRAINT fk_client_id    FOREIGN KEY (client_id)    REFERENCES clients(id),
    CONSTRAINT fk_requester_id FOREIGN KEY (requester_id) REFERENCES users(id),
    CONSTRAINT fk_approver_id  FOREIGN KEY (approver_id)  REFERENCES users(id)
);
