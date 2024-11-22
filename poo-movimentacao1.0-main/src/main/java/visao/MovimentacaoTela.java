package visao;

import java.util.Date;
import controle.ContaControle;
import controle.MovimentacaoControle;
import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) {

		MovimentacaoControle controle = new MovimentacaoControle();
		ContaControle controleConta = new ContaControle();
		Cliente cliente = new Cliente();
		Conta conta = controleConta.buscarPorId(2L);
		Movimentacao movimentacao = new Movimentacao();
		//double saldo = controle.consultarSaldo(conta.getId());

		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("depósito de 1000,00");
		movimentacao.setTipoTransacao("depósito");
		movimentacao.setValorOperacao(1000.);
		movimentacao.setConta(conta);

		if (movimentacao.getTipoTransacao() == "saque") {
			controle.realizarSaque(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "depósito") {
			controle.realizarDeposito(movimentacao, cliente);
		} else if (movimentacao.getTipoTransacao() == "pagamento") {
			controle.realizarPagamento(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "pix") {
			controle.realizarPix(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "débito") {
			controle.debito(movimentacao, conta);
		}
		
	}
}
