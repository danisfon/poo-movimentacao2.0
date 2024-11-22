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
		Movimentacao movimentacaoBanco = daomov.inserir(movimentacao);
		return movimentacaoBanco;
	}

	public Movimentacao debito(Movimentacao movimentacao, Conta conta){
		double saldo = daomov.calcularSaldo(conta.getId());
		validarSaldoNegativo(saldo, movimentacao);
		double valorFinal = movimentacao.getValorOperacao();
        movimentacao.setValorOperacao(-valorFinal);
		Movimentacao result = inserir(movimentacao);
        saldo = daomov.calcularSaldo(conta.getId());
		validarSaldoBaixo(saldo);
        return result;

		//valida e continuar depois
	}

	//-----
	public Movimentacao realizarSaque(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidarCPF.validarCpf(cliente.getCpf());
		double saldo = daomov.calcularSaldo(conta.getId());
		validarSaldoNegativo(saldo, movimentacao);
		validarValorParaSaque(movimentacao);
		aplicarTarifa(movimentacao, 2.00);
		Movimentacao result = inserir(movimentacao);
		saldo = daomov.calcularSaldo(conta.getId()); 
		validarSaldoBaixo(saldo);
		return result;
	}

	//-----
	public Movimentacao realizarDeposito(Movimentacao movimentacao,Cliente cliente) {
		ValidarCPF.validarCpf(cliente.getCpf());
		return inserir(movimentacao);
	}
	
	//-----
	public Movimentacao realizarPagamento(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidarCPF.validarCpf(cliente.getCpf());
		double saldo = daomov.calcularSaldo(conta.getId());
		validarSaldoNegativo(saldo, movimentacao);
		aplicarTarifa(movimentacao, 5.00);
		validarSaldoBaixo(saldo);
		return inserir(movimentacao);
	}

	//-----
	public Movimentacao realizarPix(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidarCPF.validarCpf(cliente.getCpf());
		validarHorarioPix();
		double saldo = daomov.calcularSaldo(conta.getId());
		validarValorParaOperacaoPix(movimentacao);
		aplicarTarifa(movimentacao, 5.00);
		validarSaldoBaixo(saldo);
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
	public boolean validarValorParaOperacaoPix(Movimentacao movimentacao) {
		if (movimentacao.getValorOperacao() > 300.00) {
			return false;
		}
		return true;
	}

	// 3.4 Limite diário de saques de R$ 5.000,00.
	public boolean validarValorParaSaque(Movimentacao movimentacao) {
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
	public void validarSaldoBaixo(double saldo) {
        if (saldo < 100.00) {
            System.out.println("ALERTA: Seu saldo está abaixo de R$100,00!");
        }
    }

	//3.10 e 3.11 Permitir a consulta de extrato
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
		return daomov.buscarPorData(id, inicio, fim);
	}

}
