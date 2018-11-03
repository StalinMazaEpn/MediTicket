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
import org.mediticket.entities.Paciente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.mediticket.controllers.exceptions.IllegalOrphanException;
import org.mediticket.controllers.exceptions.NonexistentEntityException;
import org.mediticket.controllers.exceptions.PreexistingEntityException;
import org.mediticket.entities.Historial;

/**
 *
 * @author josuericardo
 */
public class HistorialJpaController implements Serializable {

    public HistorialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historial historial) throws PreexistingEntityException, Exception {
        if (historial.getPacienteCollection() == null) {
            historial.setPacienteCollection(new ArrayList<Paciente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Paciente> attachedPacienteCollection = new ArrayList<Paciente>();
            for (Paciente pacienteCollectionPacienteToAttach : historial.getPacienteCollection()) {
                pacienteCollectionPacienteToAttach = em.getReference(pacienteCollectionPacienteToAttach.getClass(), pacienteCollectionPacienteToAttach.getCedulapaciente());
                attachedPacienteCollection.add(pacienteCollectionPacienteToAttach);
            }
            historial.setPacienteCollection(attachedPacienteCollection);
            em.persist(historial);
            for (Paciente pacienteCollectionPaciente : historial.getPacienteCollection()) {
                Historial oldIdhistorialOfPacienteCollectionPaciente = pacienteCollectionPaciente.getIdhistorial();
                pacienteCollectionPaciente.setIdhistorial(historial);
                pacienteCollectionPaciente = em.merge(pacienteCollectionPaciente);
                if (oldIdhistorialOfPacienteCollectionPaciente != null) {
                    oldIdhistorialOfPacienteCollectionPaciente.getPacienteCollection().remove(pacienteCollectionPaciente);
                    oldIdhistorialOfPacienteCollectionPaciente = em.merge(oldIdhistorialOfPacienteCollectionPaciente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorial(historial.getIdhistorial()) != null) {
                throw new PreexistingEntityException("Historial " + historial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historial historial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historial persistentHistorial = em.find(Historial.class, historial.getIdhistorial());
            Collection<Paciente> pacienteCollectionOld = persistentHistorial.getPacienteCollection();
            Collection<Paciente> pacienteCollectionNew = historial.getPacienteCollection();
            List<String> illegalOrphanMessages = null;
            for (Paciente pacienteCollectionOldPaciente : pacienteCollectionOld) {
                if (!pacienteCollectionNew.contains(pacienteCollectionOldPaciente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paciente " + pacienteCollectionOldPaciente + " since its idhistorial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Paciente> attachedPacienteCollectionNew = new ArrayList<Paciente>();
            for (Paciente pacienteCollectionNewPacienteToAttach : pacienteCollectionNew) {
                pacienteCollectionNewPacienteToAttach = em.getReference(pacienteCollectionNewPacienteToAttach.getClass(), pacienteCollectionNewPacienteToAttach.getCedulapaciente());
                attachedPacienteCollectionNew.add(pacienteCollectionNewPacienteToAttach);
            }
            pacienteCollectionNew = attachedPacienteCollectionNew;
            historial.setPacienteCollection(pacienteCollectionNew);
            historial = em.merge(historial);
            for (Paciente pacienteCollectionNewPaciente : pacienteCollectionNew) {
                if (!pacienteCollectionOld.contains(pacienteCollectionNewPaciente)) {
                    Historial oldIdhistorialOfPacienteCollectionNewPaciente = pacienteCollectionNewPaciente.getIdhistorial();
                    pacienteCollectionNewPaciente.setIdhistorial(historial);
                    pacienteCollectionNewPaciente = em.merge(pacienteCollectionNewPaciente);
                    if (oldIdhistorialOfPacienteCollectionNewPaciente != null && !oldIdhistorialOfPacienteCollectionNewPaciente.equals(historial)) {
                        oldIdhistorialOfPacienteCollectionNewPaciente.getPacienteCollection().remove(pacienteCollectionNewPaciente);
                        oldIdhistorialOfPacienteCollectionNewPaciente = em.merge(oldIdhistorialOfPacienteCollectionNewPaciente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historial.getIdhistorial();
                if (findHistorial(id) == null) {
                    throw new NonexistentEntityException("The historial with id " + id + " no longer exists.");
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
            Historial historial;
            try {
                historial = em.getReference(Historial.class, id);
                historial.getIdhistorial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Paciente> pacienteCollectionOrphanCheck = historial.getPacienteCollection();
            for (Paciente pacienteCollectionOrphanCheckPaciente : pacienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Historial (" + historial + ") cannot be destroyed since the Paciente " + pacienteCollectionOrphanCheckPaciente + " in its pacienteCollection field has a non-nullable idhistorial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(historial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historial> findHistorialEntities() {
        return findHistorialEntities(true, -1, -1);
    }

    public List<Historial> findHistorialEntities(int maxResults, int firstResult) {
        return findHistorialEntities(false, maxResults, firstResult);
    }

    private List<Historial> findHistorialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historial.class));
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

    public Historial findHistorial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historial.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historial> rt = cq.from(Historial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
