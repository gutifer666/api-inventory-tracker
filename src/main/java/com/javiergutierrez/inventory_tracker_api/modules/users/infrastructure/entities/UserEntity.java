package com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@EqualsAndHashCode()
@Table(name = "user")
@Schema(description = "Representa un usuario en el sistema.")
@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador único del usuario.")
	private Long id;

	@NotBlank(message = "El nombre del usuario no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del usuario no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50, unique = true)
	@Schema(description = "Nombre del usuario.")
	private String username;

	@NotBlank(message = "El password del usuario no puede estar vacía ni ser nula.")
	@Size(max = 100, message = "El password del usuario no puede tener más de 100 caracteres.")
	@Column(nullable = false, length = 100)
	@Schema(description = "Password del usuario.")
	private String password;

	@NotBlank(message = "El rol del usuario no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El rol del usuario no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Rol del usuario.")
	private String roles;

	@NotBlank(message = "El nombre completo del usuario no puede estar vacío ni ser nulo.")
	@Size(max = 100, message = "El nombre completo del usuario no puede tener más de 100 caracteres.")
	@Column(nullable = false, length = 100)
	@Schema(description = "Nombre completo del usuario.")
	private String fullName;

	@Builder.Default
	@ColumnDefault("0")
	@Column()
	@Schema(description = "Ventas totales.")
	private Integer sales = 0;

	@Builder.Default
	@ColumnDefault("0")
	@Column()
	@Schema(description = "Ganancias totales.")
	private Double earnings = 0.0;

}
