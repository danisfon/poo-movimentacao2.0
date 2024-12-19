package visao;

import java.util.Date;
import controle.ContaControle;
import controle.MovimentacaoControle;
import dao.ContaDAO;
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
		ContaDAO contaDAO = new ContaDAO();

		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("realizando um saque de 10000 reais");
		movimentacao.setTipoTransacao("saque");
		movimentacao.setValorOperacao(10000.00);
		movimentacao.setConta(conta);

		if (movimentacao.getTipoTransacao() == "saque") {
			controle.realizarSaque(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "deposito") {
			controle.realizarDeposito(movimentacao, cliente);
		} else if (movimentacao.getTipoTransacao() == "pagamento") {
			controle.realizarPagamento(movimentacao, conta, cliente);
		} else if (movimentacao.getTipoTransacao() == "pix") {
			controle.realizarPix(movimentacao, conta, cliente);
		} else {
			System.out.println("OPERAÇÃO INVÁLIDA!");
		}
		

		Long id = 1L;
		Double saldo = contaDAO.calcularSaldo(id);
		System.out.println("O saldo da sua conta " + id + " é de R$ " + saldo);

	}
}
