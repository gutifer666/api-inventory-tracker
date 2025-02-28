package com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.entities;

import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.entities.CategoryEntity;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.entities.SupplierEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "product")
@Schema(description = "Representa un producto en el sistema.")
@Entity
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador único del producto.")
	private Long id;

	@NotBlank(message = "El código del producto no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El código del producto no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Código del producto.")
	private String code;

	@NotBlank(message = "El nombre del producto no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del producto no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Nombre del producto.")
	private String name;

	@NotBlank(message = "La descripción del producto no puede estar vacía ni ser nula.")
	@Size(max = 255, message = "La descripción del producto no puede tener más de 255 caracteres.")
	@Column(nullable = false, length = 255)
	@Schema(description = "Descripción del producto.")
	private String description;

	@NotNull(message = "La categoría del producto no puede ser nula.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@Schema(description = "Categoría a la que pertenece el producto.")
	private CategoryEntity category;

	@NotNull(message = "El proveedor del producto no puede ser nulo.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@Schema(description = "Proveedor del producto.")
	private SupplierEntity supplier;

	@NotNull(message = "El precio del producto no puede ser nulo.")
	@Column(nullable = false)
	@Schema(description = "Precio del producto.")
	private Double costPrice;

	@NotNull(message = "El precio de venta del producto no puede ser nulo.")
	@Schema(description = "Precio de venta del producto.")
	@Column(nullable = false)
	private Double retailPrice;

	@NotNull(message = "La cantidad del producto no puede ser nula.")
	@Column(nullable = false)
	@Schema(description = "Cantidad del producto.")
	private Integer quantity;

}
