package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
            String json = gson.toJson(CarService.getInstance().getAllCars());
            System.out.println(json.toString());
            resp.getWriter().println(json);
            resp.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(400);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CarService carService = CarService.getInstance();
            carService.removeCar(req.getParameter("brand"), req.getParameter("model"), req.getParameter("licensePlate"));
        } catch (JsonSyntaxException e) {
            resp.setStatus(403);
        }
    }
}
