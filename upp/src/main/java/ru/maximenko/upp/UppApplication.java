package ru.maximenko.upp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UppApplication {

	public static void main(String[] args) {

        BikeJsonRepository.basicInit();
        BikeJsonRepository repo = new BikeJsonRepository();
        /*
        System.out.println("Список велосипедов:");
        for (Bike bike : repo.findAll()) {
            System.out.println(formatBike(bike));
        }
        */

        SpringApplication.run(UppApplication.class, args);
        System.out.println("ok");
    }

    private static String formatBike(Bike bike) {
        return String.format(
                "%d | %s %s | Цена: %.2f | В наличии: %s",
                bike.getId(),
                bike.getBrand(),
                bike.getModel(),
                bike.getPrice(),
                bike.isInStock() ? "да" : "нет"
        );
    }

}
