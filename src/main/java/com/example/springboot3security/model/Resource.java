package com.example.springboot3security.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "sys_resource")
public class Resource {


  @Id
  private Long resId;
  private String resName;
  private String identifier;
  private String url;
  private LocalDateTime createdAt;
  private LocalDateTime modifyAt;


}
