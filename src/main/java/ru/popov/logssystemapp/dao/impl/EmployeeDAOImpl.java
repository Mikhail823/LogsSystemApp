package ru.popov.logssystemapp.dao.impl;

import jakarta.persistence.Query;
import org.hibernate.Session;
import ru.popov.logssystemapp.dialog.AlertSQLException;
import ru.popov.logssystemapp.dao.EmployeeDAO;
import ru.popov.logssystemapp.model.Battery;
import ru.popov.logssystemapp.model.Employees;
import ru.popov.logssystemapp.util.CreateSession;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private CreateSession createSession = new CreateSession();
    private Session session = null;

    @Override
    public void save(Employees employees) {

        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.persist(employees);
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
    public void updateEmployee(Employees employees){
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.update(employees);
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
    public Employees findEmployeeById(Long employee_id){
        Employees employees = null;
        try {
            session = createSession.openCurrentSession();
            employees = (Employees) session.load(Employees.class, employee_id);
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка поиска по ID: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public Employees findEmployeeByName(String name){
        Employees employees = new Employees();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Employees b where b.name = :nameEmployee"
                    )
                    .setParameter("nameEmployee", name);
            employees = (Employees) query.getSingleResult();
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
        return employees;
    }

    @Override
    public List findAllEmployee(){
        List employees = new ArrayList<Employees>();
        String sql  = "SELECT * FROM Employees";
        try {
            session = createSession.openCurrentSession();
            employees = session.createNativeQuery(sql).list();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка при получении данных: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public List findAllNameEmployee(){
        List employees = new ArrayList<Employees>();
        String sql  = "SELECT name_empolee FROM Employees ORDER BY name_empolee ASC";
        try {
            session = createSession.openCurrentSession();
            employees = session.createNativeQuery(sql).list();
        } catch (Exception e) {
            AlertSQLException.sqlException("Ошибка при получении данных: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }

    @Override
    public void deleteEmployee(Employees employees) {
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            session.delete(employees);
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
    public List findBatteryByOrganization(String organization) {
        List employees = new ArrayList<Employees>();
        try {
            session = createSession.openCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(
                            " select b "
                                    + " from Employees b where b.organ = :organization"
                    )
                    .setParameter(1, organization);
            employees = (List<Battery>) query.getResultList();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employees;
    }
}
