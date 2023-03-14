package com.example.springboot3security.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity(name = "sys_role_resource")
public class RoleResource implements Serializable {


  @Id
  private Long refId;
  private Long roleId;
  private Long resId;
  private LocalDateTime createdAt;


}
