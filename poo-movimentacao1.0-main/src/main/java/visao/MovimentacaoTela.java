package visao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controle.ContaControle;
import controle.MovimentacaoControle;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) {

		MovimentacaoControle controle = new MovimentacaoControle();
		ContaControle controleConta = new ContaControle();
		Conta conta = controleConta.buscarPorId(2L);
		Movimentacao movimentacao = new Movimentacao();
		//double saldo = controle.consultarSaldo(conta.getId());

		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("depósito de 1000,00");
		movimentacao.setTipoTransacao("depósito");
		movimentacao.setValorOperacao(1000.);
		movimentacao.setConta(conta);

		if (movimentacao.getTipoTransacao() == "saque") {
			controle.realizarSaque(movimentacao, conta);
		} else if (movimentacao.getTipoTransacao() == "depósito") {
			controle.realizarDeposito(movimentacao);
		} else if (movimentacao.getTipoTransacao() == "pagamento") {
			controle.realizarPagamento(movimentacao, conta);
		} else if (movimentacao.getTipoTransacao() == "pix") {
			controle.realizarPix(movimentacao, conta);
		} else if (movimentacao.getTipoTransacao() == "débito") {
			controle.debito(movimentacao, conta);
		}
		
		
	}

}
