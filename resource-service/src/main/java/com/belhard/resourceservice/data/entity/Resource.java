package com.belhard.resourceservice.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resource")
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "audio")
    private byte[] audio;
}
