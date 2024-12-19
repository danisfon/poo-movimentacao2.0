package validar;

import java.time.LocalTime;
import dao.MovimentacaoDAO;
import entidade.Movimentacao;

public class ValidarMov {
	static MovimentacaoDAO daomov = new MovimentacaoDAO();	

    // 3.2 O saldo não pode ficar negativo. Verificar o saldo antes de fazer um saque, pagamento ou pix.
	public static boolean validarSaldoNegativo(double saldo, Movimentacao movimentacao) {
		if (saldo < movimentacao.getValorOperacao()) {
			return false;
		}
		return true;
	}

	// 3.3 Limite de R$ 300,00 para operações de pix.
	public static boolean validarLimite300(Movimentacao movimentacao) {
		if (movimentacao.getValorOperacao() > 300.00) {
			return false;
		}
		return true;
	}

	// 3.4 Limite diário de saques de R$ 5.000,00.
	public static boolean validarLimite5000(Movimentacao movimentacao) {
        double limiteDiario = daomov.validarLimiteDiarioSaque(movimentacao.getId(), movimentacao.getValorOperacao());
        if (limiteDiario <= 5000.00) {
			return true;
		} 
		return false;
    }

	// 3.5 Tarifa de Operação: R$ 5,00 para pagamentos e pix, R$ 2,00 para saques.
	public static Double aplicarTarifa(Movimentacao movimentacao, Double tarifa) {
		Double valorTotal = movimentacao.getValorOperacao() + tarifa;
		return valorTotal;
    }
	
	// 3.6 As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
	public static boolean validarHorarioPix() {
		LocalTime now = LocalTime.now();
		if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
			return false;
		}
		return true;
	}

	// 3.7 Alerta de saldo baixo: Notificar o cliente se o saldo ficar abaixo de R$ 100,00 após uma operação.
	public static void validarSaldoBaixoAlerta(double saldo) {
        if (saldo < 100.00) {
            System.out.println("ALERTA: Seu saldo está abaixo de R$100,00!");
        }
    }

	//3.9 Detecção de Fraudes: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
	public static boolean detectacaoDeFraude(Movimentacao movimentacao) {
		double calcGastos = daomov.calcularGastos(movimentacao.getId());
		if (movimentacao.getValorOperacao() > calcGastos * 2) {
			return false;
		} else {
			return true;
		}
	}
}
