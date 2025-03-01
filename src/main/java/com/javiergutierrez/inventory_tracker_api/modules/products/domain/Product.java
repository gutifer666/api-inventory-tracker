package com.javiergutierrez.inventory_tracker_api.modules.products.domain;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class Product implements Cloneable {

	@Schema(description = "Identificador único del producto.")
	private Long id;

	@NotBlank(message = "El código del producto no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El código del producto no puede tener más de 50 caracteres.")
	@Schema(description = "Código del producto.")
	private String code;

	@NotBlank(message = "El nombre del producto no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del producto no puede tener más de 50 caracteres.")
	@Schema(description = "Nombre del producto.")
	private String name;

	@NotBlank(message = "La descripción del producto no puede estar vacía ni ser nula.")
	@Size(max = 255, message = "La descripción del producto no puede tener más de 255 caracteres.")
	@Schema(description = "Descripción del producto.")
	private String description;

	@NotNull(message = "La categoría del producto no puede ser nula.")
	@Schema(description = "Categoría a la que pertenece el producto.")
	private Category category;

	@NotNull(message = "El proveedor del producto no puede ser nulo.")
	@Schema(description = "Proveedor del producto.")
	private Supplier supplier;

	@NotNull(message = "El precio de coste del producto no puede ser nulo.")
	@Schema(description = "Precio de coste del producto.")
	private Double costPrice;

	@NotNull(message = "El precio de venta del producto no puede ser nulo.")
	@Schema(description = "Precio de venta del producto.")
	private Double retailPrice;

	@NotNull(message = "La cantidad del producto no puede ser nula.")
	@Schema(description = "Cantidad del producto.")
	private Integer quantity;

	public Double sellingGain () {
		return retailPrice - costPrice;
	}

	public Double totalCost () {
		return costPrice * quantity;
	}

	public Double totalRetail () {
		return retailPrice * quantity;
	}

	public Double totalGain () {
		return sellingGain() * quantity;
	}

	public void addQuantity(Integer quantity) {
		this.quantity += quantity;
	}

	public void addQuantity() {
		this.quantity++;
	}

	public void subtractQuantity(Integer quantity) {
		this.quantity -= quantity;
	}

	public void subtractQuantity() {
		this.quantity--;
	}

	public boolean hasStock(Integer quantity) {
		return this.quantity >= quantity;
	}

	public boolean hasStock() {
		return this.quantity > 0;
	}

	public boolean isSameProduct(Product product) {
		return this.code.equals(product.getCode());
	}

	@Override
	public Product clone() {
		try {
			return (Product) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
