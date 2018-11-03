/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mediticket.entities.Turno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.mediticket.controllers.exceptions.IllegalOrphanException;
import org.mediticket.controllers.exceptions.NonexistentEntityException;
import org.mediticket.controllers.exceptions.PreexistingEntityException;
import org.mediticket.entities.Areas;

/**
 *
 * @author josuericardo
 */
public class AreasJpaController implements Serializable {

    public AreasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Areas areas) throws PreexistingEntityException, Exception {
        if (areas.getTurnoCollection() == null) {
            areas.setTurnoCollection(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Turno> attachedTurnoCollection = new ArrayList<Turno>();
            for (Turno turnoCollectionTurnoToAttach : areas.getTurnoCollection()) {
                turnoCollectionTurnoToAttach = em.getReference(turnoCollectionTurnoToAttach.getClass(), turnoCollectionTurnoToAttach.getIdturno());
                attachedTurnoCollection.add(turnoCollectionTurnoToAttach);
            }
            areas.setTurnoCollection(attachedTurnoCollection);
            em.persist(areas);
            for (Turno turnoCollectionTurno : areas.getTurnoCollection()) {
                Areas oldIdareaOfTurnoCollectionTurno = turnoCollectionTurno.getIdarea();
                turnoCollectionTurno.setIdarea(areas);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
                if (oldIdareaOfTurnoCollectionTurno != null) {
                    oldIdareaOfTurnoCollectionTurno.getTurnoCollection().remove(turnoCollectionTurno);
                    oldIdareaOfTurnoCollectionTurno = em.merge(oldIdareaOfTurnoCollectionTurno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAreas(areas.getIdarea()) != null) {
                throw new PreexistingEntityException("Areas " + areas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Areas areas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areas persistentAreas = em.find(Areas.class, areas.getIdarea());
            Collection<Turno> turnoCollectionOld = persistentAreas.getTurnoCollection();
            Collection<Turno> turnoCollectionNew = areas.getTurnoCollection();
            List<String> illegalOrphanMessages = null;
            for (Turno turnoCollectionOldTurno : turnoCollectionOld) {
                if (!turnoCollectionNew.contains(turnoCollectionOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnoCollectionOldTurno + " since its idarea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Turno> attachedTurnoCollectionNew = new ArrayList<Turno>();
            for (Turno turnoCollectionNewTurnoToAttach : turnoCollectionNew) {
                turnoCollectionNewTurnoToAttach = em.getReference(turnoCollectionNewTurnoToAttach.getClass(), turnoCollectionNewTurnoToAttach.getIdturno());
                attachedTurnoCollectionNew.add(turnoCollectionNewTurnoToAttach);
            }
            turnoCollectionNew = attachedTurnoCollectionNew;
            areas.setTurnoCollection(turnoCollectionNew);
            areas = em.merge(areas);
            for (Turno turnoCollectionNewTurno : turnoCollectionNew) {
                if (!turnoCollectionOld.contains(turnoCollectionNewTurno)) {
                    Areas oldIdareaOfTurnoCollectionNewTurno = turnoCollectionNewTurno.getIdarea();
                    turnoCollectionNewTurno.setIdarea(areas);
                    turnoCollectionNewTurno = em.merge(turnoCollectionNewTurno);
                    if (oldIdareaOfTurnoCollectionNewTurno != null && !oldIdareaOfTurnoCollectionNewTurno.equals(areas)) {
                        oldIdareaOfTurnoCollectionNewTurno.getTurnoCollection().remove(turnoCollectionNewTurno);
                        oldIdareaOfTurnoCollectionNewTurno = em.merge(oldIdareaOfTurnoCollectionNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = areas.getIdarea();
                if (findAreas(id) == null) {
                    throw new NonexistentEntityException("The areas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areas areas;
            try {
                areas = em.getReference(Areas.class, id);
                areas.getIdarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Turno> turnoCollectionOrphanCheck = areas.getTurnoCollection();
            for (Turno turnoCollectionOrphanCheckTurno : turnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Areas (" + areas + ") cannot be destroyed since the Turno " + turnoCollectionOrphanCheckTurno + " in its turnoCollection field has a non-nullable idarea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(areas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Areas> findAreasEntities() {
        return findAreasEntities(true, -1, -1);
    }

    public List<Areas> findAreasEntities(int maxResults, int firstResult) {
        return findAreasEntities(false, maxResults, firstResult);
    }

    private List<Areas> findAreasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Areas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Areas findAreas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Areas.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Areas> rt = cq.from(Areas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
