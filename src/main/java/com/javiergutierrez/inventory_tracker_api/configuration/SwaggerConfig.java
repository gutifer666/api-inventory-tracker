package com.javiergutierrez.inventory_tracker_api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "API Inventory Tracker",
				description = "Our app provides a concise inventory tracker",
				termsOfService = "https://javiergutierrez.trade/terminos_y_condiciones",
				version = "1.0.0",
				contact = @Contact(
						name = "Javier Gutierrez",
						url = "https://javiergutierrez.trade/",
						email = "santy@mail.com"
				),
				license = @License(
						name = "Standard Software Use License for Javier Gutierrez",
						url = "https://javiergutierrez.trade/licence"
				)
		),
		servers = {
				@Server(
						description = "DEV SERVER",
						url = "http://localhost:8080"
				)
		}
)
public class SwaggerConfig {}
