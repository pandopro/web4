package service;

import DAO.CarDao;
import model.Car;
import model.TodayReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {
    private static final TodayReportService todayReportService = TodayReportService.getInstance();
    private static CarService carService;

    private final SessionFactory sessionFactory;
    private CarDao carDAO;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    private CarDao getCarDAO() {
        if (carDAO == null) {
            carDAO = new CarDao(sessionFactory.openSession());
        } else {
            carDAO.setSession(sessionFactory.openSession());
        }
        return carDAO;
    }

    public boolean putCar(Car car) {
        List<Car> listOfCar = getCarDAO().getBrandedCars(car.getBrand());
        if (listOfCar.size() >= 10) {
            return false;
        }
        boolean returnAnswer = getCarDAO().putCarDAO(car);
        return returnAnswer;
    }

    public List<Car> getAllCars() {
        return getCarDAO().getAllCars();
    }

    public boolean removeCar(String brand, String model, String licensePlate) {
        Car findCar = getCarDAO().findByExample(brand, model, licensePlate);
        if (findCar != null) {
            getCarDAO().removeCarDAO(findCar.getId());
            todayReportService.AddTodaySale(new TodayReport(findCar.getPrice(), "Проданно " + findCar.toString()));
            return true;
        }
        return false;
    }


}
