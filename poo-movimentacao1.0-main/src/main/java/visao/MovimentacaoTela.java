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
		Conta conta = controleConta.buscarPorId(1L);
		Movimentacao movimentacao = new Movimentacao();

		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("teste daniele");
		movimentacao.setTipoTransacao("saque");
		movimentacao.setValorOperacao(1000.00);
		movimentacao.setConta(conta);

		if (movimentacao.getTipoTransacao() == "saque") {
			controle.realizarSaque(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "depósito") {
			controle.realizarDeposito(movimentacao, cliente);
		} else if (movimentacao.getTipoTransacao() == "pagamento") {
			controle.realizarPagamento(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "pix") {
			controle.realizarPix(movimentacao, conta, cliente);
		} else {
			System.out.println("OPERAÇÃO INVÁLIDA!");
		}
		
	}
}
