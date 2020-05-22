package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public DailyReport getLastReportDAO() {
        Transaction transaction = session.beginTransaction();
        long id = (long) session.createQuery("select count (distinct id) from DailyReport").uniqueResult();
        DailyReport lastDailyReport = session.get(DailyReport.class, id);
        transaction.commit();
        session.close();
        return lastDailyReport;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public boolean setEndDayReportDAO(DailyReport dailyReport) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(dailyReport);
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
            session.createQuery("delete from DailyReport").executeUpdate();
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            transaction.commit();
            session.close();
        }
        return true;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
