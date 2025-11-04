package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.ICampusRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.CampusQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampusRepository implements ICampusRepository {

    @Autowired
    private CampusQueries campusQueries;

    @Override
    public List<Campus> findAll() {
        return campusQueries.findAll();
    }

    @Override
    public Campus findById(int id) {
        return campusQueries.findById(id);
    }

    @Override
    public Campus save(Campus campus) {
        if (campus.getId() == null || campus.getId() == 0) {
            return campusQueries.insert(campus);
        } else {
            return campusQueries.update(campus);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return campusQueries.deleteById(id);
    }

    @Override
    public List<Campus> findActive() {
        return campusQueries.findActive();
    }

    @Override
    public List<Campus> findByInstitution(int institutionId) {
        return campusQueries.findByInstitution(institutionId);
    }
}
