package com.example.team1.Prometheus.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", updatable = false)
    private Long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    //엔티티 생성될 때 발행시간 저장하는 컬럼 추가
    @CreatedDate
    @Column(name = "upload_date")
    private LocalDateTime uploadAt;

    //엔티티 수정될 때 수정시간 저장하는 컬럼 추가
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}