package cl.divap.modoaps.app.models.dao.region;

import cl.divap.modoaps.app.models.entity.Region;
import cl.divap.modoaps.app.models.entity.ServicioSalud;

import java.util.List;

public interface IRegionDao {

    public List<Region> findAll();

    public Region findRegionByRegionId(Long regionId);
}
