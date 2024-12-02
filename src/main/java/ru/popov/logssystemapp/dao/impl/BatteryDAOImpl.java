package ru.popov.logssystemapp.dao.impl;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import ru.popov.logssystemapp.dialog.AlertSQLException;
import ru.popov.logssystemapp.dao.BatteryDAO;
import ru.popov.logssystemapp.model.Battery;
import ru.popov.logssystemapp.model.Journal;
import ru.popov.logssystemapp.util.CreateSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatteryDAOImpl implements BatteryDAO {

    private CreateSession createSession = new CreateSession();
    private Session session = null;

    @Override
    public void saveBattery(Battery battery){

        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.save(battery);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка сохранения: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
    }

    @Override
    public void updateBattery(Battery battery){
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.update(battery);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка обновления: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public Battery findBatteryById(Long battery_id){
        Battery battery= null;
        try {
            session = createSession.openCurrentSession();
            battery = (Battery) session.load(Battery.class, battery_id);
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка поиска по ID: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return battery;
    }

    @Override
    public List findAllBattery(){
        List batteries = new ArrayList<Battery>();

        try {
            session = createSession.openCurrentSession();
            batteries = loadAll(Battery.class, session);
        } catch (Exception e) {
           AlertSQLException.sqlException("Ошибка при получении данных: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return batteries;
    }

    @Override
    public void deleteBattery(Battery battery){
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.delete(battery);
            session.getTransaction().commit();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка при удалении: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List findBatteryByGroup(String group){
        List batteries = new ArrayList<Battery>();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Battery b where b.groupbattery = :group"
                    )
                    .setParameter(1, group);
            batteries = (List<Battery>) query.getResultList();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return batteries;
    }

    @Override
    public List findBatteryByLastDateChange(LocalDate lastDateChange){
        List batteries = new ArrayList<Battery>();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Battery b where b.lastDate = :lastDateChange"
                    )
                    .setParameter(1, lastDateChange);
            batteries = (List<Battery>) query.getResultList();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return batteries;
    }

    @Override
    public List findBatteryByCondition(String condition) {
        List batteries = new ArrayList<Battery>();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Battery b where b.lastDate = :condition"
                    )
                    .setParameter(1, condition);
            batteries = (List<Battery>) query.getResultList();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return batteries;
    }

    @Override
    public Battery findBatteryByCode(String code){
        Battery battery = null;
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Battery b where b.code = :code"
                    )
                    .setParameter("code", code);

            battery = (Battery) query.getSingleResult();
            session.getTransaction().commit();
            session.close();
        }catch (Exception e){
            AlertSQLException.sqlException(e.getMessage());
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return battery;
    }

    private static <T> List<T> loadAll(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }
}
