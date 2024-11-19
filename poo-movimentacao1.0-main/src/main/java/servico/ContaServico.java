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

    // 3.8 VALIDAR LIMITE DE OPERAÇÕES POR DIA 
	public boolean validarLimiteOperacoes(String cpf) {
        int totalOperacoes = dao.operacoesPorDia(cpf);
        if (totalOperacoes >= 10) {
            return false;
        }
        return true; 
    }

    public boolean adicionarConta(Long id) {
        int totalContas = dao.contarPorConta(id);
        if (totalContas >= 3) {
            return false;
        }
        return true;
    }
}
