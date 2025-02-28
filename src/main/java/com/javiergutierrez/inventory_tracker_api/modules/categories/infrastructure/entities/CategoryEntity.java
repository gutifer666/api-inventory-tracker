package com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.entities;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@EqualsAndHashCode()
@Table(name = "category")
@Schema(description = "Representa la categoría de un producto.")
@Entity
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador único de la categoría.")
	private Long id;

	@NotBlank(message = "El nombre de la categoría no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre de la categoría no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Nombre de la categoría.")
	private String name;

	@NotBlank(message = "La descripción de la categoría no puede estar vacía ni ser nula.")
	@Size(max = 255, message = "La descripción de la categoría no puede tener más de 255 caracteres.")
	@Column(nullable = false, length = 255)
	@Schema(description = "Descripción de la categoría.")
	private String description;

}
