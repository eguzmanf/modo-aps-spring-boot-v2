package cl.divap.modoaps.app.models.dao.region;

import cl.divap.modoaps.app.models.entity.Region;

import java.util.List;

public interface IRegionDao {

    public List<Region> findAll();
}
