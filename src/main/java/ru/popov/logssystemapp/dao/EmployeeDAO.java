package ru.popov.logssystemapp.dao;

import ru.popov.logssystemapp.model.Employees;

import java.util.List;

public interface EmployeeDAO {
    void save(Employees employees);
    void updateEmployee(Employees employees);
    Employees findEmployeeById(Long employee_id);
    Employees findEmployeeByName(String name);
    List findAllNameEmployee();
    List findAllEmployee();
    void deleteEmployee(Employees employees);
    List findBatteryByOrganization(String organization);

}
