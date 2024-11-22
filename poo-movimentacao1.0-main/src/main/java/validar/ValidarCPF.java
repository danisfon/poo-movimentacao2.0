package validar;

public class ValidarCPF {


	// 3.1 - Validar o CPF no momento de fazer uma transação (saque, depósito, pagamento ou Pix).
	private static boolean validar(String cpf) {
	
		if (cpf.length() != 11) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += (cpf.charAt(i) - '0') * (10 - i);
		}

		int primeiroV = (soma * 10) % 11;
		if (primeiroV == 10) {
			primeiroV = 0;
		}
		if (primeiroV != (cpf.charAt(9) - '0')) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += (cpf.charAt(i) - '0') * (11 - i);
		}
		int segundoV = (soma * 10) % 11;
		if (segundoV == 10) {
			segundoV = 0;
		}
		if (segundoV != (cpf.charAt(10) - '0')) {
			return false;
		}
		return true;
	}


	public static void validarCpf(String cpf) {
		if(!validar(cpf)){
			throw new IllegalArgumentException("CPF inválido");
		}
	} 
}

