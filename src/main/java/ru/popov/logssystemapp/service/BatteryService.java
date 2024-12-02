package ru.popov.logssystemapp.service;

import ru.popov.logssystemapp.model.Battery;

import java.util.List;

public interface BatteryService {
    List<Battery> findBatteryAll();
    Battery findBatteryByCode(String code);
}
