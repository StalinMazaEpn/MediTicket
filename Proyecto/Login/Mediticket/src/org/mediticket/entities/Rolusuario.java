/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josuericardo
 */
@Entity
@Table(name = "ROLUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolusuario.findAll", query = "SELECT r FROM Rolusuario r")
    , @NamedQuery(name = "Rolusuario.findByIdrol", query = "SELECT r FROM Rolusuario r WHERE r.rolusuarioPK.idrol = :idrol")
    , @NamedQuery(name = "Rolusuario.findByCedulausuario", query = "SELECT r FROM Rolusuario r WHERE r.rolusuarioPK.cedulausuario = :cedulausuario")
    , @NamedQuery(name = "Rolusuario.findByFechacreacion", query = "SELECT r FROM Rolusuario r WHERE r.fechacreacion = :fechacreacion")})
public class Rolusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolusuarioPK rolusuarioPK;
    @Basic(optional = false)
    @Column(name = "FECHACREACION")
    private String fechacreacion;
    @JoinColumn(name = "IDROL", referencedColumnName = "IDROL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "CEDULAUSUARIO", referencedColumnName = "CEDULAUSUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Rolusuario() {
    }

    public Rolusuario(RolusuarioPK rolusuarioPK) {
        this.rolusuarioPK = rolusuarioPK;
    }

    public Rolusuario(RolusuarioPK rolusuarioPK, String fechacreacion) {
        this.rolusuarioPK = rolusuarioPK;
        this.fechacreacion = fechacreacion;
    }

    public Rolusuario(int idrol, String cedulausuario) {
        this.rolusuarioPK = new RolusuarioPK(idrol, cedulausuario);
    }

    public RolusuarioPK getRolusuarioPK() {
        return rolusuarioPK;
    }

    public void setRolusuarioPK(RolusuarioPK rolusuarioPK) {
        this.rolusuarioPK = rolusuarioPK;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolusuarioPK != null ? rolusuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolusuario)) {
            return false;
        }
        Rolusuario other = (Rolusuario) object;
        if ((this.rolusuarioPK == null && other.rolusuarioPK != null) || (this.rolusuarioPK != null && !this.rolusuarioPK.equals(other.rolusuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mediticket.entities.Rolusuario[ rolusuarioPK=" + rolusuarioPK + " ]";
    }
    
}
