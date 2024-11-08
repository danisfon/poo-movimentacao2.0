package visao;

import java.text.SimpleDateFormat;
import java.util.Date;

import controle.MovimentacaoControle;
import dao.ClienteDAO;
import dao.ContaDAO;
import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) {


		ClienteDAO clienteDao = new ClienteDAO();
		ContaDAO contaDao = new ContaDAO();

		Cliente cliente = new Cliente();
		cliente.setCpf("13136951905");
		cliente.setNome("Daniele");
		clienteDao.inserir(cliente);

		Conta conta = new Conta();
		conta.setCliente(cliente);
		conta.setDataAbertura(new Date());
		contaDao.inserir(conta);

		MovimentacaoControle controle = new MovimentacaoControle();
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("Depósito de 500,00 no dia 03/10/24");
		movimentacao.setTipoTransacao("depósito");
		movimentacao.setValorOperacao(500.);
		
		switch(movimentacao.getTipoTransacao()){
			case "saque":
				controle.realizarSaque(movimentacao);
				break;
		}
	}

}
