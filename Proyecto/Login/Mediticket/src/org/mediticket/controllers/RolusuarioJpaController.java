/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.mediticket.controllers.exceptions.NonexistentEntityException;
import org.mediticket.controllers.exceptions.PreexistingEntityException;
import org.mediticket.entities.Rol;
import org.mediticket.entities.Rolusuario;
import org.mediticket.entities.RolusuarioPK;
import org.mediticket.entities.Usuario;

/**
 *
 * @author josuericardo
 */
public class RolusuarioJpaController extends ConnectionProperties implements Serializable {

    public RolusuarioJpaController(){}
    public RolusuarioJpaController(String stringDeConexion) {
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

    public void create(Rolusuario rolusuario) throws PreexistingEntityException, Exception {
        if (rolusuario.getRolusuarioPK() == null) {
            rolusuario.setRolusuarioPK(new RolusuarioPK());
        }
        rolusuario.getRolusuarioPK().setCedulausuario(rolusuario.getUsuario().getCedulausuario());
        rolusuario.getRolusuarioPK().setIdrol(rolusuario.getRol().getIdrol());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol = rolusuario.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getIdrol());
                rolusuario.setRol(rol);
            }
            Usuario usuario = rolusuario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getCedulausuario());
                rolusuario.setUsuario(usuario);
            }
            em.persist(rolusuario);
            if (rol != null) {
                rol.getRolusuarioCollection().add(rolusuario);
                rol = em.merge(rol);
            }
            if (usuario != null) {
                usuario.getRolusuarioCollection().add(rolusuario);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRolusuario(rolusuario.getRolusuarioPK()) != null) {
                throw new PreexistingEntityException("Rolusuario " + rolusuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rolusuario rolusuario) throws NonexistentEntityException, Exception {
        rolusuario.getRolusuarioPK().setCedulausuario(rolusuario.getUsuario().getCedulausuario());
        rolusuario.getRolusuarioPK().setIdrol(rolusuario.getRol().getIdrol());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolusuario persistentRolusuario = em.find(Rolusuario.class, rolusuario.getRolusuarioPK());
            Rol rolOld = persistentRolusuario.getRol();
            Rol rolNew = rolusuario.getRol();
            Usuario usuarioOld = persistentRolusuario.getUsuario();
            Usuario usuarioNew = rolusuario.getUsuario();
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getIdrol());
                rolusuario.setRol(rolNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getCedulausuario());
                rolusuario.setUsuario(usuarioNew);
            }
            rolusuario = em.merge(rolusuario);
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getRolusuarioCollection().remove(rolusuario);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getRolusuarioCollection().add(rolusuario);
                rolNew = em.merge(rolNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getRolusuarioCollection().remove(rolusuario);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getRolusuarioCollection().add(rolusuario);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RolusuarioPK id = rolusuario.getRolusuarioPK();
                if (findRolusuario(id) == null) {
                    throw new NonexistentEntityException("The rolusuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RolusuarioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolusuario rolusuario;
            try {
                rolusuario = em.getReference(Rolusuario.class, id);
                rolusuario.getRolusuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolusuario with id " + id + " no longer exists.", enfe);
            }
            Rol rol = rolusuario.getRol();
            if (rol != null) {
                rol.getRolusuarioCollection().remove(rolusuario);
                rol = em.merge(rol);
            }
            Usuario usuario = rolusuario.getUsuario();
            if (usuario != null) {
                usuario.getRolusuarioCollection().remove(rolusuario);
                usuario = em.merge(usuario);
            }
            em.remove(rolusuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rolusuario> findRolusuarioEntities() {
        return findRolusuarioEntities(true, -1, -1);
    }

    public List<Rolusuario> findRolusuarioEntities(int maxResults, int firstResult) {
        return findRolusuarioEntities(false, maxResults, firstResult);
    }

    private List<Rolusuario> findRolusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rolusuario.class));
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

    public Rolusuario findRolusuario(RolusuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rolusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rolusuario> rt = cq.from(Rolusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
