package controle;

import java.util.Date;
import java.util.List;

import entidade.Conta;
import entidade.Movimentacao;
import servico.MovimentacaoServico;

public class MovimentacaoControle {
	
	MovimentacaoServico servico = new MovimentacaoServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
		return servico.inserir(movimentacao);
	}

	public Movimentacao realizarSaque(Movimentacao movimentacao) {
		return servico.realizarSaque(movimentacao);
	}

	public Movimentacao realizarDeposito(Movimentacao movimentacao) {
		return servico.realizarDeposito(movimentacao);
	}

	public Movimentacao realizarPagamento(Movimentacao movimentacao) {
		return servico.realizarPagamento(movimentacao);
	}

	public Movimentacao realizarPix(Movimentacao movimentacao) {
		return servico.realizarPix(movimentacao);
	}

	public Movimentacao debito(Movimentacao movimentacao, Conta conta){
		return null;
	}
	
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
        return null;
    }

	public double consultarSaldo(Long id) {
		return 0.0;
	}
}
