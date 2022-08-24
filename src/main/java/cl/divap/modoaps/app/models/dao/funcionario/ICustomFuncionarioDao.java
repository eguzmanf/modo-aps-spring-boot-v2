package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface ICustomFuncionarioDao {

    public Page<Funcionario> findAllCriteriaApiFuncionarios(Pageable pageable, HttpSession session);

    public void updateContratosEnabledFalse(Long id);
}
