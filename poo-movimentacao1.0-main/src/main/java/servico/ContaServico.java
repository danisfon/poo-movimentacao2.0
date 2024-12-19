package servico;

import dao.ContaDAO;
import dao.GenericoDAO;
import entidade.Conta;

public class ContaServico implements ServicoBase<Conta>{

    ContaDAO dao = new ContaDAO();

    @Override
    public Conta inserir(Conta conta) {
        return dao.inserir(conta);
    }

    @Override
    public void excluir(Long id) {
        if (dao.buscarPorId(id) != null) {
            dao.excluir(id);
        }
    }

    @Override
	public Conta alterar(Conta conta) {
        return null;
    }

    @Override
    public GenericoDAO<Conta> getDAO() {
        throw new UnsupportedOperationException("");
    }

    public Conta buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    // 3.8 Limite de Operações por Dia: Definir um limite máximo de 10 operações (saque, depósito, pagamento, Pix) por dia.
	public boolean validarLimiteOperacoes(Long id) {
        int totalOperacoes = dao.operacoesPorDia(id);
        if (totalOperacoes >= 10) {
            return false;
        }
        return true; 
    }


    // 4.1 Limite de contas por cliente
    public boolean adicionarConta(Long id) {
        int totalContas = dao.contarPorConta(id);
        if (totalContas >= 3) {
            return false;
        }
        return true;
    }

}
