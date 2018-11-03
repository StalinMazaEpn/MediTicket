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
import org.mediticket.entities.Rolusuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.mediticket.controllers.exceptions.IllegalOrphanException;
import org.mediticket.controllers.exceptions.NonexistentEntityException;
import org.mediticket.controllers.exceptions.PreexistingEntityException;
import org.mediticket.entities.Turno;
import org.mediticket.entities.Usuario;

/**
 *
 * @author josuericardo
 */
public class UsuarioJpaController extends ConnectionProperties implements Serializable {

    public UsuarioJpaController(){}
    public UsuarioJpaController(String stringDeConexion) {
        super(stringDeConexion);
    }
    /*private EntityManagerFactory emf = null;*/

    public EntityManager getEntityManager() {
        EntityManagerFactory emf;
        Map properties =  super.retornarPropiedadesDeConexion();
        /*en nombre de unididad de persistencia, las prpiedades*/
        emf = Persistence.createEntityManagerFactory("MediticketPU", properties);
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getRolusuarioCollection() == null) {
            usuario.setRolusuarioCollection(new ArrayList<Rolusuario>());
        }
        if (usuario.getTurnoCollection() == null) {
            usuario.setTurnoCollection(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Rolusuario> attachedRolusuarioCollection = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioCollectionRolusuarioToAttach : usuario.getRolusuarioCollection()) {
                rolusuarioCollectionRolusuarioToAttach = em.getReference(rolusuarioCollectionRolusuarioToAttach.getClass(), rolusuarioCollectionRolusuarioToAttach.getRolusuarioPK());
                attachedRolusuarioCollection.add(rolusuarioCollectionRolusuarioToAttach);
            }
            usuario.setRolusuarioCollection(attachedRolusuarioCollection);
            Collection<Turno> attachedTurnoCollection = new ArrayList<Turno>();
            for (Turno turnoCollectionTurnoToAttach : usuario.getTurnoCollection()) {
                turnoCollectionTurnoToAttach = em.getReference(turnoCollectionTurnoToAttach.getClass(), turnoCollectionTurnoToAttach.getIdturno());
                attachedTurnoCollection.add(turnoCollectionTurnoToAttach);
            }
            usuario.setTurnoCollection(attachedTurnoCollection);
            em.persist(usuario);
            for (Rolusuario rolusuarioCollectionRolusuario : usuario.getRolusuarioCollection()) {
                Usuario oldUsuarioOfRolusuarioCollectionRolusuario = rolusuarioCollectionRolusuario.getUsuario();
                rolusuarioCollectionRolusuario.setUsuario(usuario);
                rolusuarioCollectionRolusuario = em.merge(rolusuarioCollectionRolusuario);
                if (oldUsuarioOfRolusuarioCollectionRolusuario != null) {
                    oldUsuarioOfRolusuarioCollectionRolusuario.getRolusuarioCollection().remove(rolusuarioCollectionRolusuario);
                    oldUsuarioOfRolusuarioCollectionRolusuario = em.merge(oldUsuarioOfRolusuarioCollectionRolusuario);
                }
            }
            for (Turno turnoCollectionTurno : usuario.getTurnoCollection()) {
                Usuario oldCedulausuarioOfTurnoCollectionTurno = turnoCollectionTurno.getCedulausuario();
                turnoCollectionTurno.setCedulausuario(usuario);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
                if (oldCedulausuarioOfTurnoCollectionTurno != null) {
                    oldCedulausuarioOfTurnoCollectionTurno.getTurnoCollection().remove(turnoCollectionTurno);
                    oldCedulausuarioOfTurnoCollectionTurno = em.merge(oldCedulausuarioOfTurnoCollectionTurno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getCedulausuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCedulausuario());
            Collection<Rolusuario> rolusuarioCollectionOld = persistentUsuario.getRolusuarioCollection();
            Collection<Rolusuario> rolusuarioCollectionNew = usuario.getRolusuarioCollection();
            Collection<Turno> turnoCollectionOld = persistentUsuario.getTurnoCollection();
            Collection<Turno> turnoCollectionNew = usuario.getTurnoCollection();
            List<String> illegalOrphanMessages = null;
            for (Rolusuario rolusuarioCollectionOldRolusuario : rolusuarioCollectionOld) {
                if (!rolusuarioCollectionNew.contains(rolusuarioCollectionOldRolusuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolusuario " + rolusuarioCollectionOldRolusuario + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Rolusuario> attachedRolusuarioCollectionNew = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioCollectionNewRolusuarioToAttach : rolusuarioCollectionNew) {
                rolusuarioCollectionNewRolusuarioToAttach = em.getReference(rolusuarioCollectionNewRolusuarioToAttach.getClass(), rolusuarioCollectionNewRolusuarioToAttach.getRolusuarioPK());
                attachedRolusuarioCollectionNew.add(rolusuarioCollectionNewRolusuarioToAttach);
            }
            rolusuarioCollectionNew = attachedRolusuarioCollectionNew;
            usuario.setRolusuarioCollection(rolusuarioCollectionNew);
            Collection<Turno> attachedTurnoCollectionNew = new ArrayList<Turno>();
            for (Turno turnoCollectionNewTurnoToAttach : turnoCollectionNew) {
                turnoCollectionNewTurnoToAttach = em.getReference(turnoCollectionNewTurnoToAttach.getClass(), turnoCollectionNewTurnoToAttach.getIdturno());
                attachedTurnoCollectionNew.add(turnoCollectionNewTurnoToAttach);
            }
            turnoCollectionNew = attachedTurnoCollectionNew;
            usuario.setTurnoCollection(turnoCollectionNew);
            usuario = em.merge(usuario);
            for (Rolusuario rolusuarioCollectionNewRolusuario : rolusuarioCollectionNew) {
                if (!rolusuarioCollectionOld.contains(rolusuarioCollectionNewRolusuario)) {
                    Usuario oldUsuarioOfRolusuarioCollectionNewRolusuario = rolusuarioCollectionNewRolusuario.getUsuario();
                    rolusuarioCollectionNewRolusuario.setUsuario(usuario);
                    rolusuarioCollectionNewRolusuario = em.merge(rolusuarioCollectionNewRolusuario);
                    if (oldUsuarioOfRolusuarioCollectionNewRolusuario != null && !oldUsuarioOfRolusuarioCollectionNewRolusuario.equals(usuario)) {
                        oldUsuarioOfRolusuarioCollectionNewRolusuario.getRolusuarioCollection().remove(rolusuarioCollectionNewRolusuario);
                        oldUsuarioOfRolusuarioCollectionNewRolusuario = em.merge(oldUsuarioOfRolusuarioCollectionNewRolusuario);
                    }
                }
            }
            for (Turno turnoCollectionOldTurno : turnoCollectionOld) {
                if (!turnoCollectionNew.contains(turnoCollectionOldTurno)) {
                    turnoCollectionOldTurno.setCedulausuario(null);
                    turnoCollectionOldTurno = em.merge(turnoCollectionOldTurno);
                }
            }
            for (Turno turnoCollectionNewTurno : turnoCollectionNew) {
                if (!turnoCollectionOld.contains(turnoCollectionNewTurno)) {
                    Usuario oldCedulausuarioOfTurnoCollectionNewTurno = turnoCollectionNewTurno.getCedulausuario();
                    turnoCollectionNewTurno.setCedulausuario(usuario);
                    turnoCollectionNewTurno = em.merge(turnoCollectionNewTurno);
                    if (oldCedulausuarioOfTurnoCollectionNewTurno != null && !oldCedulausuarioOfTurnoCollectionNewTurno.equals(usuario)) {
                        oldCedulausuarioOfTurnoCollectionNewTurno.getTurnoCollection().remove(turnoCollectionNewTurno);
                        oldCedulausuarioOfTurnoCollectionNewTurno = em.merge(oldCedulausuarioOfTurnoCollectionNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getCedulausuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCedulausuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rolusuario> rolusuarioCollectionOrphanCheck = usuario.getRolusuarioCollection();
            for (Rolusuario rolusuarioCollectionOrphanCheckRolusuario : rolusuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Rolusuario " + rolusuarioCollectionOrphanCheckRolusuario + " in its rolusuarioCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Turno> turnoCollection = usuario.getTurnoCollection();
            for (Turno turnoCollectionTurno : turnoCollection) {
                turnoCollectionTurno.setCedulausuario(null);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
