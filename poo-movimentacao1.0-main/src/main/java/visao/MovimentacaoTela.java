package visao;

import java.util.Date;

import controle.MovimentacaoControle;
import dao.ClienteDAO;
import dao.ContaDAO;
import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) {

		Conta conta = new Conta();
		MovimentacaoControle controle = new MovimentacaoControle();
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("Depósito de 500,00 no dia 03/10/24");
		movimentacao.setTipoTransacao("depósito");
		movimentacao.setValorOperacao(500.);
	}

}
