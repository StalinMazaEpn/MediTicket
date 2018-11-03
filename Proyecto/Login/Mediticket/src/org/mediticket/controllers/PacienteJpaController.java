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
import org.mediticket.entities.Historial;
import org.mediticket.entities.Turno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.mediticket.controllers.exceptions.IllegalOrphanException;
import org.mediticket.controllers.exceptions.NonexistentEntityException;
import org.mediticket.controllers.exceptions.PreexistingEntityException;
import org.mediticket.entities.Paciente;

/**
 *
 * @author josuericardo
 */
public class PacienteJpaController implements Serializable {

    public PacienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paciente paciente) throws PreexistingEntityException, Exception {
        if (paciente.getTurnoCollection() == null) {
            paciente.setTurnoCollection(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historial idhistorial = paciente.getIdhistorial();
            if (idhistorial != null) {
                idhistorial = em.getReference(idhistorial.getClass(), idhistorial.getIdhistorial());
                paciente.setIdhistorial(idhistorial);
            }
            Collection<Turno> attachedTurnoCollection = new ArrayList<Turno>();
            for (Turno turnoCollectionTurnoToAttach : paciente.getTurnoCollection()) {
                turnoCollectionTurnoToAttach = em.getReference(turnoCollectionTurnoToAttach.getClass(), turnoCollectionTurnoToAttach.getIdturno());
                attachedTurnoCollection.add(turnoCollectionTurnoToAttach);
            }
            paciente.setTurnoCollection(attachedTurnoCollection);
            em.persist(paciente);
            if (idhistorial != null) {
                idhistorial.getPacienteCollection().add(paciente);
                idhistorial = em.merge(idhistorial);
            }
            for (Turno turnoCollectionTurno : paciente.getTurnoCollection()) {
                Paciente oldCedulapacienteOfTurnoCollectionTurno = turnoCollectionTurno.getCedulapaciente();
                turnoCollectionTurno.setCedulapaciente(paciente);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
                if (oldCedulapacienteOfTurnoCollectionTurno != null) {
                    oldCedulapacienteOfTurnoCollectionTurno.getTurnoCollection().remove(turnoCollectionTurno);
                    oldCedulapacienteOfTurnoCollectionTurno = em.merge(oldCedulapacienteOfTurnoCollectionTurno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPaciente(paciente.getCedulapaciente()) != null) {
                throw new PreexistingEntityException("Paciente " + paciente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paciente paciente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente persistentPaciente = em.find(Paciente.class, paciente.getCedulapaciente());
            Historial idhistorialOld = persistentPaciente.getIdhistorial();
            Historial idhistorialNew = paciente.getIdhistorial();
            Collection<Turno> turnoCollectionOld = persistentPaciente.getTurnoCollection();
            Collection<Turno> turnoCollectionNew = paciente.getTurnoCollection();
            List<String> illegalOrphanMessages = null;
            for (Turno turnoCollectionOldTurno : turnoCollectionOld) {
                if (!turnoCollectionNew.contains(turnoCollectionOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnoCollectionOldTurno + " since its cedulapaciente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idhistorialNew != null) {
                idhistorialNew = em.getReference(idhistorialNew.getClass(), idhistorialNew.getIdhistorial());
                paciente.setIdhistorial(idhistorialNew);
            }
            Collection<Turno> attachedTurnoCollectionNew = new ArrayList<Turno>();
            for (Turno turnoCollectionNewTurnoToAttach : turnoCollectionNew) {
                turnoCollectionNewTurnoToAttach = em.getReference(turnoCollectionNewTurnoToAttach.getClass(), turnoCollectionNewTurnoToAttach.getIdturno());
                attachedTurnoCollectionNew.add(turnoCollectionNewTurnoToAttach);
            }
            turnoCollectionNew = attachedTurnoCollectionNew;
            paciente.setTurnoCollection(turnoCollectionNew);
            paciente = em.merge(paciente);
            if (idhistorialOld != null && !idhistorialOld.equals(idhistorialNew)) {
                idhistorialOld.getPacienteCollection().remove(paciente);
                idhistorialOld = em.merge(idhistorialOld);
            }
            if (idhistorialNew != null && !idhistorialNew.equals(idhistorialOld)) {
                idhistorialNew.getPacienteCollection().add(paciente);
                idhistorialNew = em.merge(idhistorialNew);
            }
            for (Turno turnoCollectionNewTurno : turnoCollectionNew) {
                if (!turnoCollectionOld.contains(turnoCollectionNewTurno)) {
                    Paciente oldCedulapacienteOfTurnoCollectionNewTurno = turnoCollectionNewTurno.getCedulapaciente();
                    turnoCollectionNewTurno.setCedulapaciente(paciente);
                    turnoCollectionNewTurno = em.merge(turnoCollectionNewTurno);
                    if (oldCedulapacienteOfTurnoCollectionNewTurno != null && !oldCedulapacienteOfTurnoCollectionNewTurno.equals(paciente)) {
                        oldCedulapacienteOfTurnoCollectionNewTurno.getTurnoCollection().remove(turnoCollectionNewTurno);
                        oldCedulapacienteOfTurnoCollectionNewTurno = em.merge(oldCedulapacienteOfTurnoCollectionNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = paciente.getCedulapaciente();
                if (findPaciente(id) == null) {
                    throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente paciente;
            try {
                paciente = em.getReference(Paciente.class, id);
                paciente.getCedulapaciente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Turno> turnoCollectionOrphanCheck = paciente.getTurnoCollection();
            for (Turno turnoCollectionOrphanCheckTurno : turnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Turno " + turnoCollectionOrphanCheckTurno + " in its turnoCollection field has a non-nullable cedulapaciente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Historial idhistorial = paciente.getIdhistorial();
            if (idhistorial != null) {
                idhistorial.getPacienteCollection().remove(paciente);
                idhistorial = em.merge(idhistorial);
            }
            em.remove(paciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paciente> findPacienteEntities() {
        return findPacienteEntities(true, -1, -1);
    }

    public List<Paciente> findPacienteEntities(int maxResults, int firstResult) {
        return findPacienteEntities(false, maxResults, firstResult);
    }

    private List<Paciente> findPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paciente.class));
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

    public Paciente findPaciente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paciente> rt = cq.from(Paciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
