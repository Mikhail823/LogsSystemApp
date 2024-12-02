package ru.popov.logssystemapp.service;

import ru.popov.logssystemapp.model.Employees;

import java.util.List;

public interface EmployeesService {
    List<Employees> findEmployeesAll();
    List<String> getAllNameEmployees();
    Employees findEmployeeByName(String name);
    void saveEmployee(Employees employees);
}
