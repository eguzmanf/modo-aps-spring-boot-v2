package cl.divap.modoaps.app.models.dao.descuentoRetiro;

import cl.divap.modoaps.app.models.entity.DescuentoRetiro;

import java.util.List;

public interface ICustomDescuentoRetiroDao {

    public List<DescuentoRetiro> findAllDescuentoRetiro();
}
