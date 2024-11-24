package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base class for entities, providing common audit properties.
 * @author Andras Nyilas
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit {

    /**
     * The timestamp when the entity was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the entity was last modified.
     */
    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    /**
     * The version of the entity used for optimistic locking.
     */
    @Version
    @Column(name = "version")
    private Long version;

    /**
     * Gets the timestamp when the entity was created.
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the entity was created.
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp when the entity was last modified.
     * @return the modification timestamp
     */
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Sets the timestamp when the entity was last modified.
     * @param modifiedAt the modification timestamp to set
     */
    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    /**
     * Gets the version of the entity used for optimistic locking.
     * @return the version of the entity
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version of the entity used for optimistic locking.
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}
