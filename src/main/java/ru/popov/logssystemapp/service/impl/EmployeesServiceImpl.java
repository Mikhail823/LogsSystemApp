package ru.popov.logssystemapp.service.impl;

import ru.popov.logssystemapp.dao.FactoryDAO;
import ru.popov.logssystemapp.model.Employees;
import ru.popov.logssystemapp.service.EmployeesService;

import java.util.List;

public class EmployeesServiceImpl implements EmployeesService {
    @Override
    public List findEmployeesAll() {
        return FactoryDAO.getInstance().getEmployeeDAO().findAllEmployee();
    }

    @Override
    public List getAllNameEmployees(){
        return FactoryDAO.getInstance().getEmployeeDAO().findAllNameEmployee();
    }

    @Override
    public Employees findEmployeeByName(String name) {
        return FactoryDAO.getInstance().getEmployeeDAO().findEmployeeByName(name);
    }

    @Override
    public void saveEmployee(Employees employees) {
        FactoryDAO.getInstance().getEmployeeDAO().save(employees);
    }
}
