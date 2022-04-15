package com.example.springboot3security.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Resource {


  @Id
  private Long resId;
  private String resName;
  private String identifier;
  private String url;
  private LocalDateTime createdAt;
  private LocalDateTime modifyAt;


}
