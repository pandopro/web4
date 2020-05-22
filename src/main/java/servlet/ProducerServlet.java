package servlet;

import model.Car;
import service.CarService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        CarService carService = CarService.getInstance();
        Car car = new Car(
                req.getParameter("brand"),
                req.getParameter("model"),
                req.getParameter("licensePlate"),
                Long.parseLong(req.getParameter("price")));
        if (carService.putCar(car) == true) {
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
    }
}
