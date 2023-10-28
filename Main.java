import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Модель данных
class Record {
    private String date;
    private String time;
    private String description;

    public Record(String date, String time, String description) {
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}

// Интерфейс модели
interface Model {
    void addRecord(String date, String time, String description);

    List<Record> getRecords();

    List<Record> searchRecordsByDate(String date);

    void saveToFile(String filename);

    void loadFromFile(String filename);
}

// Реализация модели
class ModelImpl implements Model {
    private List<Record> records = new ArrayList<>();

    @Override
    public void addRecord(String date, String time, String description) {
        Record record = new Record(date, time, description);
        records.add(record);
    }

    @Override
    public List<Record> getRecords() {
        return records;
    }

    @Override
    public List<Record> searchRecordsByDate(String date) {
        List<Record> result = new ArrayList<>();
        for (Record record : records) {
            if (record.getDate().equals(date)) {
                result.add(record);
            }
        }
        return result;
    }

    @Override
    public void saveToFile(String filename) {
        // Реализация сохранения в файл
    }

    @Override
    public void loadFromFile(String filename) {
        // Реализация загрузки из файла
    }
}

// Интерфейс представления
interface View {
    void displayRecords(List<Record> records);

    String getInput();

    void showMessage(String message);
}

// Реализация представления
class ViewImpl implements View {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayRecords(List<Record> records) {
        for (Record record : records) {
            System.out.println("Date: " + record.getDate());
            System.out.println("Time: " + record.getTime());
            System.out.println("Description: " + record.getDescription());
            System.out.println();
        }
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}

// Интерфейс презентера
interface Presenter {
    void addRecord();

    void searchRecordsByDate();

    void saveRecordsToFile();

    void loadRecordsFromFile();
}

// Реализация презентера
class PresenterImpl implements Presenter {
    private Model model;
    private View view;

    public PresenterImpl(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void addRecord() {
        view.showMessage("Enter date (e.g., 2023-10-28):");
        String date = view.getInput();
        view.showMessage("Enter time (e.g., 19:00):");
        String time = view.getInput();
        view.showMessage("Enter description:");
        String description = view.getInput();
        model.addRecord(date, time, description);
        view.showMessage("Record added successfully!");
    }

    @Override
    public void searchRecordsByDate() {
        view.showMessage("Enter date to search (e.g., 2023-10-28):");
        String date = view.getInput();
        List<Record> records = model.searchRecordsByDate(date);
        view.displayRecords(records);
    }

    @Override
    public void saveRecordsToFile() {
        view.showMessage("Enter filename to save records:");
        String filename = view.getInput();
        model.saveToFile(filename);
        view.showMessage("Records saved to file.");
    }

    
    
    public void loadRecordsFromFile() {
        view.showMessage("Enter filename to load records:");
        String filename = view.getInput();
        model.loadFromFile(filename);
        view.showMessage("Records loaded from file.");
    }
}

public class Main {
    public static void main(String[] args) {
        Model model = new ModelImpl();
        View view = new ViewImpl();
        Presenter presenter = new PresenterImpl(model, view);

        while (true) {
            view.showMessage("1. Add Record");
            view.showMessage("2. Search Records by Date");
            view.showMessage("3. Save Records to File");
            view.showMessage("4. Load Records from File");
            view.showMessage("5. Exit");

            String choice = view.getInput();

            switch (choice) {
                case "1":
                    presenter.addRecord();
                    break;
                case "2":
                    presenter.searchRecordsByDate();
                    break;
                case "3":
                    presenter.saveRecordsToFile();
                    break;
                case "4":
                    presenter.loadRecordsFromFile();
                    break;
                case "5":
                    System.exit(0);
                    break;
                default:
                    view.showMessage("Invalid choice. Please try again.");
            }
        }
    }
}
