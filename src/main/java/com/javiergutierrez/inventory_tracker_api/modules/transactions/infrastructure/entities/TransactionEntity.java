package com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@EqualsAndHashCode()
@Table(name = "transactions")
@Schema(description = "Representa una transacción en el sistema.")
@Entity
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador único de la transacción.")
	private Long id;

	@NotBlank(message = "El nombre del empleado no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del empleado no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Nombre del empleado.")
	private String employeeName;

	@NotBlank(message = "El nombre del cliente no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del cliente no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Nombre del cliente.")
	private String clientName;

	@NotBlank(message = "El código del producto no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El código del producto no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Código del producto.")
	private String productCode;

	@NotBlank(message = "El nombre del producto no puede estar vacío ni ser nulo.")
	@Size(max = 50, message = "El nombre del producto no puede tener más de 50 caracteres.")
	@Column(nullable = false, length = 50)
	@Schema(description = "Nombre del producto.")
	private String productName;

	@NotNull(message = "La cantidad no puede ser nula.")
	@Column(nullable = false)
	@Schema(description = "Cantidad de productos en la transacción.")
	private Integer quantity;

	@NotNull(message = "El precio de la transacción no puede ser nula.")
	@Column(nullable = false)
	@Schema(description = "Precio de la transacción.")
	private Double transactionPrice;

	// Hacer fecha default del sistema
	@Builder.Default
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(columnDefinition = "DATE")
	@Schema(description = "Fecha de realización de la transacción.")
	private Date createdAt = new Date();
}
