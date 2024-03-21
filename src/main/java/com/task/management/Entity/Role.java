package com.task.management.Entity;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 60)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
