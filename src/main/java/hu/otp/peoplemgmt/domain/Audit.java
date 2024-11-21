package hu.otp.peoplemgmt.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Audit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @org.springframework.data.annotation.Version
    @Column(name="version")
    private Long version;

}
