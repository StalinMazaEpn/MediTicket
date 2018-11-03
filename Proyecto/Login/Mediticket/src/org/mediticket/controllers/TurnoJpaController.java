/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mediticket.controllers.exceptions.NonexistentEntityException;
import org.mediticket.controllers.exceptions.PreexistingEntityException;
import org.mediticket.entities.Areas;
import org.mediticket.entities.Paciente;
import org.mediticket.entities.Turno;
import org.mediticket.entities.Usuario;

/**
 *
 * @author josuericardo
 */
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areas idarea = turno.getIdarea();
            if (idarea != null) {
                idarea = em.getReference(idarea.getClass(), idarea.getIdarea());
                turno.setIdarea(idarea);
            }
            Paciente cedulapaciente = turno.getCedulapaciente();
            if (cedulapaciente != null) {
                cedulapaciente = em.getReference(cedulapaciente.getClass(), cedulapaciente.getCedulapaciente());
                turno.setCedulapaciente(cedulapaciente);
            }
            Usuario cedulausuario = turno.getCedulausuario();
            if (cedulausuario != null) {
                cedulausuario = em.getReference(cedulausuario.getClass(), cedulausuario.getCedulausuario());
                turno.setCedulausuario(cedulausuario);
            }
            em.persist(turno);
            if (idarea != null) {
                idarea.getTurnoCollection().add(turno);
                idarea = em.merge(idarea);
            }
            if (cedulapaciente != null) {
                cedulapaciente.getTurnoCollection().add(turno);
                cedulapaciente = em.merge(cedulapaciente);
            }
            if (cedulausuario != null) {
                cedulausuario.getTurnoCollection().add(turno);
                cedulausuario = em.merge(cedulausuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTurno(turno.getIdturno()) != null) {
                throw new PreexistingEntityException("Turno " + turno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno persistentTurno = em.find(Turno.class, turno.getIdturno());
            Areas idareaOld = persistentTurno.getIdarea();
            Areas idareaNew = turno.getIdarea();
            Paciente cedulapacienteOld = persistentTurno.getCedulapaciente();
            Paciente cedulapacienteNew = turno.getCedulapaciente();
            Usuario cedulausuarioOld = persistentTurno.getCedulausuario();
            Usuario cedulausuarioNew = turno.getCedulausuario();
            if (idareaNew != null) {
                idareaNew = em.getReference(idareaNew.getClass(), idareaNew.getIdarea());
                turno.setIdarea(idareaNew);
            }
            if (cedulapacienteNew != null) {
                cedulapacienteNew = em.getReference(cedulapacienteNew.getClass(), cedulapacienteNew.getCedulapaciente());
                turno.setCedulapaciente(cedulapacienteNew);
            }
            if (cedulausuarioNew != null) {
                cedulausuarioNew = em.getReference(cedulausuarioNew.getClass(), cedulausuarioNew.getCedulausuario());
                turno.setCedulausuario(cedulausuarioNew);
            }
            turno = em.merge(turno);
            if (idareaOld != null && !idareaOld.equals(idareaNew)) {
                idareaOld.getTurnoCollection().remove(turno);
                idareaOld = em.merge(idareaOld);
            }
            if (idareaNew != null && !idareaNew.equals(idareaOld)) {
                idareaNew.getTurnoCollection().add(turno);
                idareaNew = em.merge(idareaNew);
            }
            if (cedulapacienteOld != null && !cedulapacienteOld.equals(cedulapacienteNew)) {
                cedulapacienteOld.getTurnoCollection().remove(turno);
                cedulapacienteOld = em.merge(cedulapacienteOld);
            }
            if (cedulapacienteNew != null && !cedulapacienteNew.equals(cedulapacienteOld)) {
                cedulapacienteNew.getTurnoCollection().add(turno);
                cedulapacienteNew = em.merge(cedulapacienteNew);
            }
            if (cedulausuarioOld != null && !cedulausuarioOld.equals(cedulausuarioNew)) {
                cedulausuarioOld.getTurnoCollection().remove(turno);
                cedulausuarioOld = em.merge(cedulausuarioOld);
            }
            if (cedulausuarioNew != null && !cedulausuarioNew.equals(cedulausuarioOld)) {
                cedulausuarioNew.getTurnoCollection().add(turno);
                cedulausuarioNew = em.merge(cedulausuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = turno.getIdturno();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getIdturno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            Areas idarea = turno.getIdarea();
            if (idarea != null) {
                idarea.getTurnoCollection().remove(turno);
                idarea = em.merge(idarea);
            }
            Paciente cedulapaciente = turno.getCedulapaciente();
            if (cedulapaciente != null) {
                cedulapaciente.getTurnoCollection().remove(turno);
                cedulapaciente = em.merge(cedulapaciente);
            }
            Usuario cedulausuario = turno.getCedulausuario();
            if (cedulausuario != null) {
                cedulausuario.getTurnoCollection().remove(turno);
                cedulausuario = em.merge(cedulausuario);
            }
            em.remove(turno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
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

    public Turno findTurno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
