package ru.popov.logssystemapp.service;

import ru.popov.logssystemapp.model.Radiostation;

public interface RadiostationService {

    Radiostation findRadioStationByNumber(String number);
}
