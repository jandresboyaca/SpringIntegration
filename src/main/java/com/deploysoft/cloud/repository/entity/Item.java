package com.deploysoft.cloud.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "item")
@ToString
public class Item implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String message;


}
