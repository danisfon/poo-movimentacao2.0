package servico;

import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {

    ContaDAO dao = new ContaDAO();

    public Conta inserir(Conta conta) {
        return dao.inserir(conta);
    }

    public void excluir(Conta conta) {
        if (dao.buscarPorId(conta.getId()) != null) {
            dao.excluir(conta.getId());
        }
    }

    public Conta buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}
