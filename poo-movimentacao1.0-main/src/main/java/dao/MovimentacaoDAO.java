package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidade.Movimentacao;

public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {
	private ContaDAO contaDAO; 
	
	public MovimentacaoDAO() {
		super(Movimentacao.class);
        contaDAO = new ContaDAO(); 
    }

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

	public Double calcularSaldo(Long id) {
		return contaDAO.calcularSaldo(id);
	}

	public List<Movimentacao> buscarPorData(Long id, Date inicio, Date fim) {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Movimentacao> query = em.createQuery(
			"FROM Movimentacao m WHERE m.conta.id = :id_conta AND m.dataTransacao BETWEEN :inicio AND :fim",
			Movimentacao.class
		);
		query.setParameter("id_conta", id);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		List<Movimentacao> movimentacoes = query.getResultList();
		em.close();
		return movimentacoes;
	}
	
	public Double calcularGastos(Long id) {
		EntityManager em = emf.createEntityManager();
		Double gastos = em.createQuery(
				"SELECT SUM(m.valorOperacao) FROM Movimentacao m WHERE m.conta.id = :id_conta", Double.class)
				.setParameter("id_conta", id)
				.getSingleResult();
		em.close();
	
		return gastos != null ? gastos : 0.0;
	}
	

	public Double validarLimiteDiarioSaque(Long id, Double valor) {
		EntityManager em = emf.createEntityManager();
	

		Double totalDiario = em.createQuery(
				"SELECT SUM(m.valorOperacao) " +
						"FROM Movimentacao m " +
						"WHERE m.conta.id = :id_conta AND " +
						"      m.tipoTransacao = :tipo_operacao AND " +
						"      DATE(m.dataTransacao) = CURRENT_DATE",
				Double.class)

				.setParameter("id_conta", id)
				.setParameter("tipo_operacao", "SAQUE")
				.getSingleResult();
		em.close();
		// Double totalDiario = em.createQuery(
		// 		"SELECT SUM(m.valorOperacao) " +
		// 		"FROM Movimentacao m " +
		// 		"WHERE m.conta.id = :id_conta AND " +
		// 		"      m.tipoOperacao = :tipo_operacao AND " +
		// 		"      DATE(m.dataOperacao) = CURRENT_DATE", Double.class)

				
		// 		.setParameter("id_conta", id)
		// 		.setParameter("tipo_operacao", "SAQUE") 
		// 		.getSingleResult();
		// em.close();
	
		if (totalDiario == null) {
			totalDiario = 0.0;
		}
			
		double totalAtualizado = totalDiario + valor;
	
		return totalAtualizado;
	}
	
	
}
