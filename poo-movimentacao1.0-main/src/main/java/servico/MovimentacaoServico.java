package servico;

import java.util.Date;
import java.util.List;

import dao.MovimentacaoDAO;
import entidade.Movimentacao;
import entidade.Cliente;
import entidade.Conta;
import validar.ValidarMov;

public class MovimentacaoServico {
	MovimentacaoDAO daomov = new MovimentacaoDAO();	
	
	public Movimentacao inserir(Movimentacao movimentacao) {
		return daomov.inserir(movimentacao);
	}

	public Movimentacao realizarDeposito(Movimentacao movimentacao,Cliente cliente) {
		if (ValidarMov.detectacaoDeFraude(movimentacao)){
			System.out.println("Operação inválida. O sistema detectou uma movimentação incomum!");
			return null;
		} else {
			return inserir(movimentacao);
		}
	}
	
	public Movimentacao realizarSaque(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidarMov.aplicarTarifa(movimentacao, 2.00);
		double saldo = daomov.calcularSaldo(conta.getId());
		ValidarMov.validarSaldoBaixoAlerta(saldo);

		if(saldo == 0.0){
			System.out.println("Operação inválida. Seu saldo é 0.0!");
			return null;
		} else if (ValidarMov.detectacaoDeFraude(movimentacao)) {
			System.out.println("Operação inválida. O sistema detectou uma movimentação incomum!");
			return null;
		} else if (!ValidarMov.validarSaldoNegativo(saldo, movimentacao)) {
			System.out.println("Operação inválida. Seu saldo está negativo!");
			return null;
		} else if (!ValidarMov.validarLimite5000(movimentacao)) {
			System.out.println("Operação inválida. Você ultrapassou o limite diário de R$ 5.000,00");
			return null;
		}else {
			return inserir(movimentacao);
		}
	}
	
	public Movimentacao realizarPagamento(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidarMov.aplicarTarifa(movimentacao, 5.00);
		double saldo = daomov.calcularSaldo(conta.getId());
		ValidarMov.validarSaldoBaixoAlerta(saldo);

		if (ValidarMov.detectacaoDeFraude(movimentacao)){
			System.out.println("Operação inválida. O sistema detectou uma movimentação incomum!");
			return null;
		} else if (!ValidarMov.validarSaldoNegativo(saldo, movimentacao)) {
			System.out.println("Operação inválida. Seu saldo está negativo!");
			return null;
		} else {
			return inserir(movimentacao);
		}
	}

	public Movimentacao realizarPix(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidarMov.aplicarTarifa(movimentacao, 5.00);
		double saldo = daomov.calcularSaldo(conta.getId());
		ValidarMov.validarSaldoBaixoAlerta(saldo);

		if (ValidarMov.detectacaoDeFraude(movimentacao)){
			System.out.println("Operação inválida. O sistema detectou uma movimentação incomum!");
			return null;
		} else if (!ValidarMov.validarLimite300(movimentacao)){
			System.out.println("Operação inválida. Você excedeu o limite de R$ 300,00!");
			return null;
		} else if (!ValidarMov.validarHorarioPix()){
			System.out.println("Operação inválida. As operações de Pix só podem ser realizadas entre 06:00 e 22:00.!");
			return null;
		} else {
			return inserir(movimentacao);
		}
	}

	public double consultarSaldo(Long id) {
		return daomov.calcularSaldo(id);
	}

	//3.10 e 3.11 Permitir a consulta de extrato
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
		return daomov.buscarPorData(id, inicio, fim);
	}
}
