package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFuncionarioDao extends PagingAndSortingRepository<Funcionario, Long> {

    @Query("select f from Funcionario f left join fetch f.contratos c where f.id=?1")
    public Funcionario fetchByIdWithContratos(Long id);

}
