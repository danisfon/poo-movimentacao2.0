package dao;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidade.Movimentacao;

public class MovimentacaoDAO {
	private ContaDAO contaDAO; 
	
	public MovimentacaoDAO() {
        contaDAO = new ContaDAO(); 
    }

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

	public Movimentacao inserir(Movimentacao movimentacao) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(movimentacao);
		em.getTransaction().commit();
		em.close();
		return movimentacao;
	}

	public Movimentacao alterar(Movimentacao movimentacao) {
		Movimentacao movimentacaoBanco = null;
		if (movimentacao.getId() != null) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			movimentacaoBanco = buscarPorId(movimentacao.getId());

			if (movimentacaoBanco != null) {
				movimentacaoBanco.setDescricao(movimentacao.getDescricao());
				em.merge(movimentacaoBanco);
			}

			em.getTransaction().commit();
			em.close();
		}
		return movimentacaoBanco;
	}

	public void excluir(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Movimentacao movimentacao = em.find(Movimentacao.class, id);
		if (movimentacao != null) {
			em.remove(movimentacao);
		}
		em.getTransaction().commit();
		em.close();
	}

	public List<Movimentacao> listarTodos() {
		EntityManager em = emf.createEntityManager();
		List<Movimentacao> movimentacoes = em.createQuery("from Movimentacao").getResultList();
		em.close();
		return movimentacoes;
	}

	public Movimentacao buscarPorId(Long id) {
		EntityManager em = emf.createEntityManager();
		Movimentacao movimentacao = em.find(Movimentacao.class, id);
		em.close();
		return movimentacao;
	}

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
	
}
