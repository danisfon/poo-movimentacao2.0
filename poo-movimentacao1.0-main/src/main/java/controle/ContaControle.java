package controle;

import entidade.Conta;
import servico.ContaServico;

public class ContaControle {
	
	ContaServico servico = new ContaServico();
		
	public Conta inserir(Conta conta) {
		return servico.inserir(conta);
	}

    public void excluir(Conta conta) {
        servico.excluir(conta);
    }

    public Conta buscarPorId(Long id) {
        return servico.buscarPorId(id);
    }

    public boolean adicionarConta(Long id){
        return servico.adicionarConta(id);
    }
}
