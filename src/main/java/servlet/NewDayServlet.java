package servlet;

import service.DailyReportService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewDayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        DailyReportService dailyReportService = DailyReportService.getInstance();
        dailyReportService.setEndDayReportDAO();
        resp.setStatus(200);
    }
}
