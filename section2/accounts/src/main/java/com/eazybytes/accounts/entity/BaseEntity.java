package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass// this to declare it as superclass

public class BaseEntity {
    @Column(updatable = false) // to not update the this col when update is made
    private LocalDateTime createdAt;
    @Column(updatable = false)
    private String createdBy;
    @Column(insertable = false)// to not entry this col when insert is made
    private LocalDateTime updatedAt;
    @Column(insertable = false)
    private String updatedBy;
}
