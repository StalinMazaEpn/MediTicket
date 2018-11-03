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
import org.mediticket.entities.Rol;

/**
 *
 * @author josuericardo
 */
public class RolJpaController extends ConnectionProperties implements Serializable {

    public RolJpaController(){}
    public RolJpaController(String stringDeConexion) {
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

    public void create(Rol rol) throws PreexistingEntityException, Exception {
        if (rol.getRolusuarioCollection() == null) {
            rol.setRolusuarioCollection(new ArrayList<Rolusuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Rolusuario> attachedRolusuarioCollection = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioCollectionRolusuarioToAttach : rol.getRolusuarioCollection()) {
                rolusuarioCollectionRolusuarioToAttach = em.getReference(rolusuarioCollectionRolusuarioToAttach.getClass(), rolusuarioCollectionRolusuarioToAttach.getRolusuarioPK());
                attachedRolusuarioCollection.add(rolusuarioCollectionRolusuarioToAttach);
            }
            rol.setRolusuarioCollection(attachedRolusuarioCollection);
            em.persist(rol);
            for (Rolusuario rolusuarioCollectionRolusuario : rol.getRolusuarioCollection()) {
                Rol oldRolOfRolusuarioCollectionRolusuario = rolusuarioCollectionRolusuario.getRol();
                rolusuarioCollectionRolusuario.setRol(rol);
                rolusuarioCollectionRolusuario = em.merge(rolusuarioCollectionRolusuario);
                if (oldRolOfRolusuarioCollectionRolusuario != null) {
                    oldRolOfRolusuarioCollectionRolusuario.getRolusuarioCollection().remove(rolusuarioCollectionRolusuario);
                    oldRolOfRolusuarioCollectionRolusuario = em.merge(oldRolOfRolusuarioCollectionRolusuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRol(rol.getIdrol()) != null) {
                throw new PreexistingEntityException("Rol " + rol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdrol());
            Collection<Rolusuario> rolusuarioCollectionOld = persistentRol.getRolusuarioCollection();
            Collection<Rolusuario> rolusuarioCollectionNew = rol.getRolusuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Rolusuario rolusuarioCollectionOldRolusuario : rolusuarioCollectionOld) {
                if (!rolusuarioCollectionNew.contains(rolusuarioCollectionOldRolusuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolusuario " + rolusuarioCollectionOldRolusuario + " since its rol field is not nullable.");
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
            rol.setRolusuarioCollection(rolusuarioCollectionNew);
            rol = em.merge(rol);
            for (Rolusuario rolusuarioCollectionNewRolusuario : rolusuarioCollectionNew) {
                if (!rolusuarioCollectionOld.contains(rolusuarioCollectionNewRolusuario)) {
                    Rol oldRolOfRolusuarioCollectionNewRolusuario = rolusuarioCollectionNewRolusuario.getRol();
                    rolusuarioCollectionNewRolusuario.setRol(rol);
                    rolusuarioCollectionNewRolusuario = em.merge(rolusuarioCollectionNewRolusuario);
                    if (oldRolOfRolusuarioCollectionNewRolusuario != null && !oldRolOfRolusuarioCollectionNewRolusuario.equals(rol)) {
                        oldRolOfRolusuarioCollectionNewRolusuario.getRolusuarioCollection().remove(rolusuarioCollectionNewRolusuario);
                        oldRolOfRolusuarioCollectionNewRolusuario = em.merge(oldRolOfRolusuarioCollectionNewRolusuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getIdrol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdrol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rolusuario> rolusuarioCollectionOrphanCheck = rol.getRolusuarioCollection();
            for (Rolusuario rolusuarioCollectionOrphanCheckRolusuario : rolusuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the Rolusuario " + rolusuarioCollectionOrphanCheckRolusuario + " in its rolusuarioCollection field has a non-nullable rol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
