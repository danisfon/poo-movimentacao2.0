package servico;

import java.time.LocalTime;
import java.util.Date;

import dao.ClienteDAO;
import dao.ContaDAO;
import dao.MovimentacaoDAO;
import entidade.Movimentacao;
import entidade.Cliente;
import entidade.Conta;

public class MovimentacaoServico {
	MovimentacaoDAO daomov = new MovimentacaoDAO();
	ClienteDAO daocli = new ClienteDAO();
	ContaDAO daocont = new ContaDAO();
	
	
	public Movimentacao inserir(Movimentacao movimentacao) {
		movimentacao.setDescricao("Operação de "+movimentacao.getTipoTransacao());
		movimentacao.setDataTransacao(new Date());
		Movimentacao movimentacaoBanco = daomov.inserir(movimentacao);
		return movimentacaoBanco;
	}

	public Movimentacao realizarSaque(Movimentacao movimentacao) {
		return null;
	}

	public Movimentacao realizarDeposito(Movimentacao movimentacao) {
		return null;
	}
	
	public Movimentacao realizarPagamento(Movimentacao movimentacao) {
		return null;
	}

	public Movimentacao consultarSaldo(Movimentacao movimentacao) {
		return null;
	}

	public Movimentacao realizarPix(Movimentacao movimentacao) {
		return null;
	}

	
	// 3.2 VALIDAR SALDO NEGATIVO
	public boolean validarSaldoNegativo(double saldo, Movimentacao movimentacao) {
		if (saldo < movimentacao.getValorOperacao()) {
			return false;
		}
		if (saldo < 100.00) {
			return false;
		}
		return true;
	}

	// 3.3 LIMITE DE 300 REAIS PARA OPERAÇÃO PIX
	public boolean validarValorParaOperacaoPix(Movimentacao movimentacao) {
		if (movimentacao.getValorOperacao() > 300.00) {
			return false;
		}
		return true;
	}

	// 3.4 LIMITE DE 5000 REAIS PARA SACAR
	public boolean validarValorParaSaque(Movimentacao movimentacao) {
        if (movimentacao.getValorOperacao() > 5000.00) {
            return false;
        }
		return true;
    }

	// 3.5 APLICANDO TARIFA
	public Double aplicarTarifa(Movimentacao movimentacao, Double tarifa) {
		Double valorTotal = movimentacao.getValorOperacao() + tarifa;
		return valorTotal;
    }
	
	// 3.6 VALIDAR HORARIO DA OPERAÇÃO
	public boolean validarHorarioPix(Movimentacao movimentacao) {
		LocalTime now = LocalTime.now();
		if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
			return false;
		}
		return true;
	}

	// 3.7 NOTIFICAR O CLIENTE CASO O VALOR FIQUE < 100
	public void velidarSaldoBaixo(double saldo) {
        if (saldo < 100.00) {
            System.out.println("ALERTA: Seu saldo está abaixo de R$100,00!");
        }
    }

	// 3.8 VALIDAR LIMITE DE OPERAÇÕES POR DIA 
	public boolean validarLimiteOperacoes(String cpf) {
        int totalOperacoes = daomov.operacoesPorDia(cpf);
        if (totalOperacoes >= 10) {
            return false;
        }
        return true; 
    }

}
