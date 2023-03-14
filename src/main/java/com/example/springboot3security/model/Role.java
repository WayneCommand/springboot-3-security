package com.example.springboot3security.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "sys_role")
public class Role {

  @Id
  private Long roleId;
  private String roleName;
  private LocalDateTime createdAt;
  private LocalDateTime modifyAt;

}
