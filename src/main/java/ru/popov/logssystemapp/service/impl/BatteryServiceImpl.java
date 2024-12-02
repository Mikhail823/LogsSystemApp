package ru.popov.logssystemapp.service.impl;
import ru.popov.logssystemapp.service.BatteryService;
import ru.popov.logssystemapp.model.Battery;
import ru.popov.logssystemapp.dao.FactoryDAO;

import java.util.List;


public class BatteryServiceImpl implements BatteryService {
    @Override
    public List<Battery> findBatteryAll() {
        return FactoryDAO.getInstance().getBatteryDAO().findAllBattery();
    }

    @Override
    public Battery findBatteryByCode(String code) {
        return FactoryDAO.getInstance().getBatteryDAO().findBatteryByCode(code);
    }
}
