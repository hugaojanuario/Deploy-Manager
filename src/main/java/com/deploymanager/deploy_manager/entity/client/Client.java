package com.deploymanager.deploy_manager.entity.client;

import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;
import com.deploymanager.deploy_manager.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String anydeskId;
    private String anydeskPassword;

    private String teamviewerId;
    private String teamviewerPassword;

    private String anyviewerId;
    private String anyviewerPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeServerClient server;

    @Column(nullable = false)
    private String serverUsername;

    @Column(nullable = false)
    private String serverPassword;

    private String dbUser;
    private String dbPassword;

    private String notes;

    private String sentinelUrl;
    private String sentinelToken;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_created_user_by"))
    private User createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
