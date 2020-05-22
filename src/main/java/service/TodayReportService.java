package service;

import DAO.TodayReportDAO;
import model.TodayReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class TodayReportService {

    private static TodayReportService todayReportService;
    TodayReportDAO todayReportDAO;
    private SessionFactory sessionFactory;

    private TodayReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static TodayReportService getInstance() {
        if (todayReportService == null) {
            todayReportService = new TodayReportService(DBHelper.getSessionFactory());
        }
        return todayReportService;
    }

    private TodayReportDAO getTodayReportDAO() {

        if (todayReportDAO == null) {
            todayReportDAO = new TodayReportDAO(sessionFactory.openSession());
        } else {
            todayReportDAO.setSession(sessionFactory.openSession());
        }
        return todayReportDAO;

    }

    public List<TodayReport> finalReport() {
        List<TodayReport> list = getTodayReportDAO().reportDao();
        getTodayReportDAO().clear();
        return list;
    }

    public void AddTodaySale(TodayReport todayReport) {
        if (todayReport.getPrice() > 0) {
            getTodayReportDAO().AddTodaySale(todayReport);
        } else {
            getTodayReportDAO().AddTodaySale(new TodayReport((long) 0, todayReport.getinfo()));
        }
    }
}
