package ru.maximenko.upp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {

    private final BikeJsonRepository repository = new BikeJsonRepository();

    // 1. список всех велосипедов
    @GetMapping
    public ResponseEntity<List<Bike>> getAllBikes() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 2. велосипед по id
    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable int id) {
        Bike bike = repository.findById(id);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    // 3. новый велосипед
    @PostMapping
    public ResponseEntity<String> addBike(@RequestBody Bike bike) {
        boolean added = repository.add(bike);

        if (!added) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Велосипед с таким id уже существует");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Велосипед успешно добавлен");
    }

    // 4. обновить запись
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBike(
            @PathVariable int id,
            @RequestBody Bike bike) {
        boolean updated = repository.update(id, bike);
        if (!updated) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Велосипед не найден");
        }

        return ResponseEntity.ok("Велосипед обновлён");
    }

    // 5. удалить велосипед
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBike(@PathVariable int id) {
        boolean deleted = repository.delete(id);
        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Велосипед не найден");
        }
        return ResponseEntity.ok("Велосипед удалён");
    }

    // 6. выборка по бренду
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Bike>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(repository.findByBrand(brand));
    }

    // 7. выборка в наличии по диапазону цены
    // пример: /api/bikes/filter?minPrice=30000&maxPrice=80000
    @GetMapping("/filter")
    public ResponseEntity<List<Bike>> getInStockByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        return ResponseEntity.ok(
                repository.findInStockByPriceRange(minPrice, maxPrice)
        );
    }
}

