package cl.divap.modoaps.app.models.dao.contrato;

import cl.divap.modoaps.app.models.entity.Contrato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IContratoDao extends CrudRepository<Contrato, Long> {
    //

    @Query("select c from Contrato c join fetch c.funcionario where c.id=?1")
    public Contrato fetchByIdWithFuncionario(Long id);
}
