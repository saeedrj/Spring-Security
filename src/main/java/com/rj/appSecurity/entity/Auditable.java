package com.rj.appSecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rj.appSecurity.domain.RequestContext;
import com.rj.appSecurity.exception.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updateAt"}, allowGetters = true)
public abstract class Auditable {
    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    public void beforePersist() {
        var userId =0L;
//        if (userId == null) {
//            throw new ApiException("cannot persist entity without user ID in Request Context for this Thead");
//        }
        setCreatedAt(now());
        setCreatedBy(userId);
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }

    @PreUpdate
    public void beforeUpdate() {
        var userId = 0L;
//        if (userId == null) {
//            throw new ApiException("cannot persist entity without user ID in Request Context for this Thead");
//        }
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }


}
