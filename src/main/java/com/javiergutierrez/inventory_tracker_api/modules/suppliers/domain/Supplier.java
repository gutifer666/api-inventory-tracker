package com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain;

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
public class Supplier implements Cloneable {

	@Schema(description = "Identificador único del proveedor.")
	private Long id;

	@NotBlank(message = "El nombre del proveedor no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del proveedor no puede tener más de 50 caracteres.")
	@Schema(description = "Nombre del proveedor.")
	private String name;

	@Override
	public Supplier clone() {
		try {
			return (Supplier) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
