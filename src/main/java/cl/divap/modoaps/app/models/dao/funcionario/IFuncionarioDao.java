package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFuncionarioDao extends PagingAndSortingRepository<Funcionario, Long> {

    @Query("select f from Funcionario f left join fetch f.contratos c where f.id=?1")
    public Funcionario fetchByIdWithContratos(Long id);

    @Query("select f from Funcionario f left join fetch f.contratos c where f.id=?1 and c.servicioSalud.id=?2")
    public Funcionario fetchByIdByIdServicioWithContratosRoleServicio(Long id, Long idServicio);

    @Query("select f from Funcionario f left join fetch f.contratos c where f.id=?1 and c.servicioSalud.id=?2 and c.comuna.codigoComuna=?3")
    public Funcionario fetchByIdByIdComunaByIdServicioWithContratosRoleComuna(Long id, Long idServicio, Long idComuna);

    @Query("SELECT f FROM Funcionario f LEFT JOIN FETCH f.contratos c WHERE f.id=?1 AND c.servicioSalud.id IN (13, 14)")
    public Funcionario fetchByIdWithContratosRoleLaGranja(Long id);

}
