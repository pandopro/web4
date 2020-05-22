package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import DAO.TodayReportDAO;
import model.DailyReport;
import model.TodayReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;
    private DailyReportDao dailyReportDao;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    private DailyReportDao getDailyReportDao() {
        if (dailyReportDao == null) {
            dailyReportDao = new DailyReportDao(sessionFactory.openSession());
        } else {
            dailyReportDao.setSession(sessionFactory.openSession());
        }
        return dailyReportDao;
    }

    public List<DailyReport> getAllDailyReports() {
        return getDailyReportDao().getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return getDailyReportDao().getLastReportDAO();
    }

    public boolean setEndDayReportDAO() {
        TodayReportService todayReportService = TodayReportService.getInstance();
        List<TodayReport> list = todayReportService.finalReport();
        DailyReport newReport;
        long earnings = 0;
        long count = 0;
        if (list.size() > 0) {
            earnings = list.stream().mapToLong(a -> a.getPrice()).sum();
            count = list.stream().count();
        }
        newReport = new DailyReport(earnings, count);
        return getDailyReportDao().setEndDayReportDAO(newReport);
    }

    public void clear() {
        getDailyReportDao().clear();
        new CarDao(sessionFactory.openSession()).clear();
        new TodayReportDAO(sessionFactory.openSession()).clear();
    }
}
