package ru.maximenko.upp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BikeJsonRepository {

    private static final String FILE_NAME = "bikes.json";
    //списки "стандартных" марок и моделей велосипедов
    private static final String[] BRANDS = {"OPTIMAL", "KUDO", "HIGH SEASON", "ELZA"};
    private static final String[] MODELS = {"Cluster", "Megan", "Racer", "Turbo", "Cross", "Explorer",
            "Velocity", "Trail", "Urban", "Pro", "Classic", "Sport"};

    private final ObjectMapper mapper = new ObjectMapper();
    private List<Bike> bikes = new ArrayList<>();


    public BikeJsonRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                bikes = mapper.readValue(file, new TypeReference<List<Bike>>() {});
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла JSON");
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_NAME), bikes);
        } catch (Exception e) {
            System.out.println("Ошибка записи файла JSON");
            e.printStackTrace();
        }
    }

    public List<Bike> findAll() {
        return bikes;
    }

    public Bike findById(int id) {
        return bikes.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean add(Bike bike) {
        boolean exists = this.findById(bike.getId()) != null; // гарантируем уникальность id
        if (exists) {
            return false;
        }
        bikes.add(bike);
        saveToFile();
        return true;
    }

    public boolean update(int id, Bike updatedBike) {
        for (int i = 0; i < bikes.size(); i++) {
            if (bikes.get(i).getId() == id) {
                bikes.set(i, updatedBike);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        boolean removed = bikes.removeIf(b -> b.getId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
    }


    public List<Bike> findByBrand(String brand) {
        return bikes.stream()
                .filter(b -> b.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public List<Bike> findInStockByPriceRange(double minPrice, double maxPrice) {
        return bikes.stream()
                .filter(b -> b.isInStock())
                .filter(b -> b.getPrice() >= minPrice && b.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // инициализация файла базовым набором записей
    public static void basicInit() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Файл не найден. Создаем новый со случайными велосипедами...");
            try {
                List<Bike> initialBikes = generateRandomBikes(20);
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, initialBikes);
                System.out.println("Файл успешно создан и заполнен 20 велосипедами");
            } catch (Exception e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static List<Bike> generateRandomBikes(int count) {
        List<Bike> bikes = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= count; i++) {
            String brand = BRANDS[random.nextInt(BRANDS.length)];
            String modelBase = MODELS[random.nextInt(MODELS.length)];
            String model = modelBase + " " + (random.nextInt(10) + 1); // добавляем число от 1 до 10
            double price = 10000 + random.nextDouble() * 110000; // от 10000 до 120000
            boolean inStock = random.nextBoolean();

            Bike bike = new Bike(i, model, brand, Math.round(price * 100.0) / 100.0, inStock);
            bikes.add(bike);
        }

        return bikes;
    }
}