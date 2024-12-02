package ru.popov.logssystemapp.dao.impl;

import jakarta.persistence.Query;
import org.hibernate.Session;

import ru.popov.logssystemapp.dialog.AlertSQLException;
import ru.popov.logssystemapp.dao.RadiostationDAO;
import ru.popov.logssystemapp.model.Battery;
import ru.popov.logssystemapp.model.Radiostation;
import ru.popov.logssystemapp.util.CreateSession;

import java.util.ArrayList;
import java.util.List;

public class RadiostationDAOImpl implements RadiostationDAO {

    private CreateSession createSession = new CreateSession();
    private Session session = null;

    @Override
    public void saveRadioStation(Radiostation radiostation){

        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.save(radiostation);
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
    public void updateRadioStation(Radiostation radiostation){
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.update(radiostation);
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
    public Radiostation findRadioStationById(Long radioStation_id){
        Radiostation radiostation = null;
        try {
            session = createSession.openCurrentSession();
            radiostation = (Radiostation) session.load(Radiostation.class, radioStation_id);
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка поиска по ID: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return radiostation;
    }

    @Override
    public List findAllRadioStation(){
        List radiostations = new ArrayList<Radiostation>();
        String sql  = "SELECT * FROM Radiostation";
        try {
            session = createSession.openCurrentSession();
            radiostations = session.createNativeQuery(sql).list();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка при получении данных: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return radiostations;
    }

    @Override
    public void deleteRadioStation(Radiostation radiostation) {
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.delete(radiostation);
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
    public List findRadioStationByZavNumber(String zavNumber){
        List radiostations = new ArrayList<Radiostation>();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Radiostation b where b.zavNumber = :zavNumber"
                    )
                    .setParameter(1, zavNumber);
            radiostations = (List<Battery>) query.getResultList();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return radiostations;
    }

    @Override
    public List findRadioStationByBrNumber(String brNumber){
        List radiostations = new ArrayList<Radiostation>();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Radiostation b where b.brNumber = :brNumber"
                    )
                    .setParameter(1, brNumber);
            radiostations = (List<Battery>) query.getResultList();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return radiostations;
    }

    @Override
    public Radiostation findRadioStationByNumber(String number) {
        Radiostation radiostation = new Radiostation();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Radiostation b where b.zavNumber = :number"
                    )
                    .setParameter("number", number);
            radiostation= (Radiostation) query.getSingleResult();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка поиска по number: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return radiostation;
    }
}
