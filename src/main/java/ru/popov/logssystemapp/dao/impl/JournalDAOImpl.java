package ru.popov.logssystemapp.dao.impl;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import ru.popov.logssystemapp.dialog.AlertSQLException;
import ru.popov.logssystemapp.dao.JournalDAO;
import ru.popov.logssystemapp.dialog.AlertSucceeded;
import ru.popov.logssystemapp.model.Journal;
import ru.popov.logssystemapp.util.CreateSession;

import java.time.LocalDate;
import java.util.List;

public class JournalDAOImpl implements JournalDAO {
    private CreateSession createSession = new CreateSession();
    private Session session = null;
    @Override
    public void save(Journal j) {
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.persist(j);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка сохранения: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
    }

    @Override
    public void update(Journal j) {
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.persist(j);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка обновления: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
    }

    @Override
    public List getAllDataJournal() {
        List<Journal> journal = null;
        try {
            session = createSession.openCurrentSession();
            journal = loadAll(Journal.class, session);
            session.close();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка получения данных: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
        return journal;
    }

    @Override
    public void delete(Journal j) {
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.delete(j);
            System.out.println(j.getName());
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка обновления: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
    }

    @Override
    public Journal findJournalById(String name) {
        Journal journal = null;
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Journal b where b.name = :name"
                    )
                    .setParameter("name", name);

            journal = (Journal) query.getSingleResult();
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
        return journal;
    }


    private static <T> List<T> loadAll(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }
    @Override
    public List getAllDataJournalByDate(LocalDate date) {
        List journal = null;
        try {
            session = createSession.openCurrentSession();
            String sql = "select j from Journal j where j.dateReceipt = :date";
            Query query = session.createQuery(sql).setParameter("date", date);
            journal = (List) query.getResultList();

            session.close();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка получения данных: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
        return journal;
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "delete from Journal where id = :id";
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();

            System.out.println("DELETE by Journal " + id);
            Journal journal = session.get(Journal.class, id);
            session.remove(journal);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка обновления: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()){
                createSession.closeCurrentSession();
            }
        }
    }
}
