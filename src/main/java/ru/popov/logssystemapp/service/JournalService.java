package ru.popov.logssystemapp.service;

import ru.popov.logssystemapp.model.Journal;

import java.time.LocalDate;
import java.util.List;

public interface JournalService {
    void save(Journal j);
    void update(Journal j);
    List getAllDataJournal();
    void delete(Journal j);
    Journal findById(String name);
    List getListJournalByDate(LocalDate date);
    void deleteById(Integer id);
}
