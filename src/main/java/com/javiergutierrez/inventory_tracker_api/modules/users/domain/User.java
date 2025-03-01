package com.javiergutierrez.inventory_tracker_api.modules.users.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@EqualsAndHashCode()
public class User implements Cloneable {

	@Schema(description = "Identificador único del usuario.")
	private Long id;

	@NotBlank(message = "El nombre del usuario no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del usuario no puede tener más de 50 caracteres.")
	@Schema(description = "Nombre del usuario.")
	private String username;

	@NotBlank(message = "El password del usuario no puede estar vacía ni ser nula.")
	@Size(max = 100, message = "El password del usuario no puede tener más de 100 caracteres.")
	@Schema(description = "Password del usuario.")
	private String password;

	@NotBlank(message = "El rol del usuario no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El rol del usuario no puede tener más de 50 caracteres.")
	@Schema(description = "Rol del usuario.")
	private String roles;

	@NotBlank(message = "El nombre completo del usuario no puede estar vacío ni ser nulo.")
	@Size(max = 100, message = "El nombre completo del usuario no puede tener más de 100 caracteres.")
	@Schema(description = "Nombre completo del usuario.")
	private String fullName;

	@Builder.Default
	@Schema(description = "Ventas totales.")
	private Integer sales = 0;

	@Builder.Default
	@Schema(description = "Ganancias totales.")
	private Double earnings = 0.0;

	public void addSale(Integer quantity) {
		this.sales += quantity;
	}

	public void addEarnings(Double amount) {
		this.earnings += amount;
	}

	public void removeSale(Integer quantity) {
		this.sales -= quantity;
	}

	public void removeEarnings(Double amount) {
		this.earnings -= amount;
	}

	@Override
	public User clone() {
		try {
			return (User) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
