package servico;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import dao.MovimentacaoDAO;
import entidade.Movimentacao;
import entidade.Cliente;
import entidade.Conta;
import validar.ValidarCPF;

public class MovimentacaoServico {
	MovimentacaoDAO daomov = new MovimentacaoDAO();	
	
	public Movimentacao inserir(Movimentacao movimentacao) {
		movimentacao.setDescricao("Operação de "+movimentacao.getTipoTransacao());
		movimentacao.setDataTransacao(new Date());

		return daomov.inserir(movimentacao);
	}

	//-----
	public Movimentacao realizarDeposito(Movimentacao movimentacao,Cliente cliente) {
		detectacaoDeFraude(movimentacao);
		ValidarCPF.validarCpf(cliente.getCpf());

		return inserir(movimentacao);
	}
	
	//-----
	public Movimentacao realizarSaque(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		double saldo = daomov.calcularSaldo(conta.getId());

		ValidarCPF.validarCpf(cliente.getCpf());
		detectacaoDeFraude(movimentacao);
		validarSaldoNegativo(saldo, movimentacao);
		validarLimite5000(movimentacao);

		aplicarTarifa(movimentacao, 2.00);
		validarSaldoBaixoAlerta(saldo);

		return inserir(movimentacao);
	}
	
	//-----
	public Movimentacao realizarPagamento(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		double saldo = daomov.calcularSaldo(conta.getId());

		ValidarCPF.validarCpf(cliente.getCpf());
		detectacaoDeFraude(movimentacao);
		validarSaldoNegativo(saldo, movimentacao);

		aplicarTarifa(movimentacao, 5.00);
		validarSaldoBaixoAlerta(saldo);

		return inserir(movimentacao);
	}

	//-----
	public Movimentacao realizarPix(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		double saldo = daomov.calcularSaldo(conta.getId());

		ValidarCPF.validarCpf(cliente.getCpf());
		detectacaoDeFraude(movimentacao);
		validarHorarioPix();
		validarLimite300(movimentacao);

		aplicarTarifa(movimentacao, 5.00);
		validarSaldoBaixoAlerta(saldo);

		return inserir(movimentacao);
	}

	public double consultarSaldo(Long id) {
		return daomov.calcularSaldo(id);
	}


	// 3.2 O saldo não pode ficar negativo. Verificar o saldo antes de fazer um saque, pagamento ou pix.
	public boolean validarSaldoNegativo(double saldo, Movimentacao movimentacao) {
		if (saldo < movimentacao.getValorOperacao()) {
			return false;
		}
		if (saldo < 100.00) {
			return false;
		}
		return true;
	}

	// 3.3 Limite de R$ 300,00 para operações de pix.
	public boolean validarLimite300(Movimentacao movimentacao) {
		if (movimentacao.getValorOperacao() > 300.00) {
			return false;
		}
		return true;
	}

	// 3.4 Limite diário de saques de R$ 5.000,00.
	public boolean validarLimite5000(Movimentacao movimentacao) {
        if (movimentacao.getValorOperacao() > 5000.00) {
            return false;
        }
		return true;
    }

	// 3.5 Tarifa de Operação: R$ 5,00 para pagamentos e pix, R$ 2,00 para saques.
	public Double aplicarTarifa(Movimentacao movimentacao, Double tarifa) {
		Double valorTotal = movimentacao.getValorOperacao() + tarifa;
		return valorTotal;
    }
	
	// 3.6 As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
	public boolean validarHorarioPix() {
		LocalTime now = LocalTime.now();
		if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
			return false;
		}
		return true;
	}

	// 3.7 Alerta de saldo baixo: Notificar o cliente se o saldo ficar abaixo de R$ 100,00 após uma operação.
	public void validarSaldoBaixoAlerta(double saldo) {
        if (saldo < 100.00) {
            System.out.println("ALERTA: Seu saldo está abaixo de R$100,00!");
        }
    }

	//3.10 e 3.11 Permitir a consulta de extrato
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
		return daomov.buscarPorData(id, inicio, fim);
	}

	//3.9 Detecção de Fraudes: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
	public boolean detectacaoDeFraude(Movimentacao movimentacao) {
        double calcGastos = daomov.calcularGastos(movimentacao.getId());
        if (calcGastos == 0) {
			return true;
		} else if (movimentacao.getValorOperacao() > calcGastos * 2) {
            return false;
        } else {
			return false;
		}
    }
}
