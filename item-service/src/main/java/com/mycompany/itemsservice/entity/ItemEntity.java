package com.mycompany.itemsservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Items")
@Data
@NoArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(nullable = false, length = 255, name = "item_title")
    private String title;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
