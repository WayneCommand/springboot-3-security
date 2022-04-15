package com.example.springboot3security.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class UserRole implements Serializable {

  @Id
  private Long refId;
  private Long userId;
  private Long roleId;
  private LocalDateTime createdAt;


}
