package com.deploymanager.deploy_manager.domain.accessRequest;

import com.deploymanager.deploy_manager.domain.accessRequest.enums.AccessStatus;
import com.deploymanager.deploy_manager.domain.client.Client;
import com.deploymanager.deploy_manager.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "access_requests" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_client_id"))
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false, foreignKey = @ForeignKey(name = "fk_requester_id"))
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", foreignKey = @ForeignKey(name = "fk_approver_id"))
    private User approver;

    @Enumerated(EnumType.STRING)
    private AccessStatus status;

    private String reason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime respondedAt;

    private LocalDateTime expiresAt;


}
