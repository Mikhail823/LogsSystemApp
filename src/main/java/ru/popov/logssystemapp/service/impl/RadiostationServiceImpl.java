package ru.popov.logssystemapp.service.impl;

import ru.popov.logssystemapp.dao.FactoryDAO;
import ru.popov.logssystemapp.model.Radiostation;
import ru.popov.logssystemapp.service.RadiostationService;

public class RadiostationServiceImpl implements RadiostationService {
    @Override
    public Radiostation findRadioStationByNumber(String number) {
        return FactoryDAO.getInstance().getRadioStationDAO().findRadioStationByNumber(number);
    }
}
