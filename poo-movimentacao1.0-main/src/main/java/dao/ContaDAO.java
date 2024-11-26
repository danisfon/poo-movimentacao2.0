package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entidade.Conta;

public class ContaDAO extends GenericoDAO<Conta> {

   public ContaDAO() {
        super(Conta.class);
    }


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");




    public int operacoesPorDia(Long id) {
        EntityManager em = emf.createEntityManager();
        Long conta = em.createQuery(
            "SELECT COUNT(m) FROM Movimentacao m WHERE m.conta.id = :id_conta AND DATE(m.dataTransacao) = CURRENT_DATE", Long.class)
            .setParameter("id_conta", id)
            .getSingleResult();
        em.close();
        return conta.intValue();
    }
    
    public int contarPorConta(Long id) {
        EntityManager em = emf.createEntityManager();
        Long conta = em.createQuery(
            "SELECT COUNT(c) FROM Conta c WHERE c.cliente.id = :id_cliente", Long.class)
            .setParameter("id_cliente", id).getSingleResult();
        em.close();
        return conta.intValue();
    }

    public Double calcularSaldo(Long id) {
        EntityManager em = emf.createEntityManager();
        Double calculo = em.createQuery(
            "SELECT COALESCE(SUM(m.valorOperacao), 0.0) FROM Movimentacao m WHERE m.conta.id = :id_conta", Double.class)
            .setParameter("id_conta", id).getSingleResult();
        return calculo;
    }
}
