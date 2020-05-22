package DAO;

import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean putCarDAO(Car car) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return true;
    }

    public List<Car> getBrandedCars(String brand) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Car where brand = :paramBrand");
        query.setParameter("paramBrand", brand);
        List list = query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    public Car findByExample(String brand, String model, String licensePlate) {
        // переделать входные данные : ренд номерб модель, взращать кар
        Query query = session.createQuery("from Car where brand = :paramBrand and model = :paramModel and licensePlate = :paramlicensePlate");
        query.setParameter("paramBrand", brand);
        query.setParameter("paramModel", model);
        query.setParameter("paramlicensePlate", licensePlate);
        Car car = (Car) query.getResultList().get(0);
        session.close();
        return car;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    // использзовать простой запрос
    public boolean removeCarDAO(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.session;
            transaction = session.beginTransaction();
            Car car = session.get(Car.class, id);
            session.delete(car);
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            transaction.commit();
            session.close();
        }
        return true;
    }

    public boolean clear() {
        Transaction transaction = null;


        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from Car").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }
}
