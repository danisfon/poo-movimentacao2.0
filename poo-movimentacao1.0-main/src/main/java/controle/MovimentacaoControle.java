package controle;

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
}
