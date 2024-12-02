package ru.popov.logssystemapp.dao;

import ru.popov.logssystemapp.model.Battery;

import java.time.LocalDate;
import java.util.List;

public interface BatteryDAO {
    void saveBattery(Battery battery);
    void updateBattery(Battery battery);
    Battery findBatteryById(Long battery_id);
    List findAllBattery();
    void deleteBattery(Battery battery);
    List findBatteryByGroup(String group);
    List findBatteryByLastDateChange(LocalDate lastDateChange);
    List findBatteryByCondition(String condition);
    Battery findBatteryByCode(String code);
}
