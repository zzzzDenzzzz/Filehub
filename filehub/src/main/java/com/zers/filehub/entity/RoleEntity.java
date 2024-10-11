package com.zers.filehub.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zers.filehub.enumiration.Authority;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RoleEntity extends Audittable {
    private String name;
    private Authority authorities;
}
