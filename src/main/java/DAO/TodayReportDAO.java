package DAO;


import model.TodayReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TodayReportDAO {
    private Session session;

    public TodayReportDAO(Session session) {
        this.session = session;
    }

    public List<TodayReport> reportDao() {
        Transaction transaction = session.beginTransaction();
        List<TodayReport> todayReport = session.createQuery("FROM TodayReport").list();
        transaction.commit();
        session.close();
        return todayReport;
    }

    public boolean AddTodaySale(TodayReport todayReport) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(todayReport);

        } catch (Exception e) {
            e.printStackTrace();
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

    public void clear() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("delete from TodayReport").executeUpdate();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {

            transaction.commit();
            session.close();
        }
    }
}
