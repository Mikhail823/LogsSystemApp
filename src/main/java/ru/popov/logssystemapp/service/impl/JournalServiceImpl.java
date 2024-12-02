package ru.popov.logssystemapp.service.impl;



import ru.popov.logssystemapp.dao.FactoryDAO;
import ru.popov.logssystemapp.model.Journal;
import ru.popov.logssystemapp.service.JournalService;

import java.time.LocalDate;
import java.util.List;

public class JournalServiceImpl implements JournalService {
    @Override
    public void save(Journal j) {
        FactoryDAO.getInstance().getJournalDAO().save(j);
    }

    @Override
    public void update(Journal j){
        FactoryDAO.getInstance().getJournalDAO().update(j);
    }

    @Override
    public List getAllDataJournal(){
        return FactoryDAO.getInstance().getJournalDAO().getAllDataJournal();
    }

    @Override
    public void delete(Journal j) {
        FactoryDAO.getInstance().getJournalDAO().delete(j);
    }

    @Override
    public Journal findById(String name) {
        return FactoryDAO.getInstance().getJournalDAO().findJournalById(name);
    }

    @Override
    public List getListJournalByDate(LocalDate date) {
        return FactoryDAO.getInstance().getJournalDAO().getAllDataJournalByDate(date);
    }

    @Override
    public void deleteById(Integer id) {
        FactoryDAO.getInstance().getJournalDAO().deleteById(id);
    }
}
