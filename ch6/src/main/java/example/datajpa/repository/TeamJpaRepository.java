package example.datajpa.repository;

import example.datajpa.entity.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    public Optional<Team> findById(Long id) {

/*
        //이렇게 안해도됨 em.find()메서드 있음;;
        Team findTeam = em.createQuery("select t from Team t where t.id = :id", Team.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(findTeam);
*/

        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    public Long count() {
        return em.createQuery("select count(t) from Team t", Long.class)
                .getSingleResult();
    }

}
