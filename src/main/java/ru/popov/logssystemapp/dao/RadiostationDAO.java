package ru.popov.logssystemapp.dao;

import ru.popov.logssystemapp.model.Radiostation;

import java.util.List;

public interface RadiostationDAO {
    void saveRadioStation(Radiostation radiostation);
    void updateRadioStation(Radiostation radiostation);
    Radiostation findRadioStationById(Long radioStation_id);
    List findAllRadioStation();
    void deleteRadioStation(Radiostation radiostation);
    List findRadioStationByZavNumber(String zavNumber);
    List findRadioStationByBrNumber(String brNumber);
    Radiostation findRadioStationByNumber(String number);
}
