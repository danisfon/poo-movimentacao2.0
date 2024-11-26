package controle;

import java.util.Date;
import java.util.List;

import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;
import servico.MovimentacaoServico;

public class MovimentacaoControle {
	
	MovimentacaoServico servico = new MovimentacaoServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
		return servico.inserir(movimentacao);
	}

	public Movimentacao realizarSaque(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		return servico.realizarSaque(movimentacao, conta, cliente);
	}

	public Movimentacao realizarDeposito(Movimentacao movimentacao, Cliente cliente) {
		return servico.realizarDeposito(movimentacao, cliente);
	}

	public Movimentacao realizarPagamento(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		return servico.realizarPagamento(movimentacao, conta, cliente);
	}

	public Movimentacao realizarPix(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		return servico.realizarPix(movimentacao, conta, cliente);
	}
	
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
        return servico.consultarExtrato(id, inicio, fim);
    }

	public double consultarSaldo(Long id) {
		return servico.consultarSaldo(id);
	}
}
