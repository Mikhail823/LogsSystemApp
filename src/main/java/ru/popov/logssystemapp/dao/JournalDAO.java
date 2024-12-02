package ru.popov.logssystemapp.dao;

import ru.popov.logssystemapp.model.Journal;

import java.time.LocalDate;
import java.util.List;

public interface JournalDAO {
    void save(Journal j);
    void update(Journal j);
    List getAllDataJournal();
    void delete(Journal j);
    Journal findJournalById(String name);
    List getAllDataJournalByDate(LocalDate date);
    void deleteById(Integer id);
}
