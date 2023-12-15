package com.example.inventoryservice;

import com.example.inventoryservice.models.Inventory;
import com.example.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository) {
		return args -> {
			if (inventoryRepository.findAllByDeletedFalse().size() == 0) {
				Iterable<Inventory> iterable = () -> {
					List<Inventory> list = new ArrayList<>(2);

					list.add(Inventory.builder()
						.skuCode("iPhone 15")
						.quantity(100)
						.createdDate(LocalDateTime.now())
						.modifiedDate(LocalDateTime.now())
						.deleted(Boolean.FALSE)
						.build());

					list.add(Inventory.builder()
						.skuCode("Samsung A50")
						.quantity(500)
						.createdDate(LocalDateTime.now())
						.modifiedDate(LocalDateTime.now())
						.deleted(Boolean.FALSE)
						.build());

					return list.iterator();
				};

				inventoryRepository.saveAll(iterable);
			}
		};
	}
}
