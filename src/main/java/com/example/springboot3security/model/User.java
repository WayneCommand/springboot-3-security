package com.example.springboot3security.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "sys_user")
public class User {

  @Id
  private Long userId;
  private String userName;
  private String userEmail;
  private String password;
  private LocalDateTime createdAt;
  private LocalDateTime modifyAt;


}
