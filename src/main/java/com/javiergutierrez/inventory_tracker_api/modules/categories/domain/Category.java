package com.javiergutierrez.inventory_tracker_api.modules.categories.domain;

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
public class Category implements Cloneable {

	@Schema(description = "Identificador único de la categoría.")
	private Long id;

	@NotBlank(message = "El nombre de la categoría no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre de la categoría no puede tener más de 50 caracteres.")
	@Schema(description = "Nombre de la categoría.")
	private String name;

	@NotBlank(message = "La descripción de la categoría no puede estar vacía ni ser nula.")
	@Size(max = 255, message = "La descripción de la categoría no puede tener más de 255 caracteres.")
	@Schema(description = "Descripción de la categoría.")
	private String description;

	@Override
	public Category clone() {
		try {
			return (Category) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
