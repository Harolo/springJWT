package com.springJWT.Model;

import com.springJWT.Enum.RoleName;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;

    public Rol() {

    }

    public Rol(RoleName name) {
        this.name = name;
    }
}
