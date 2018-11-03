/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author josuericardo
 */
@Embeddable
public class RolusuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDROL")
    private int idrol;
    @Basic(optional = false)
    @Column(name = "CEDULAUSUARIO")
    private String cedulausuario;

    public RolusuarioPK() {
    }

    public RolusuarioPK(int idrol, String cedulausuario) {
        this.idrol = idrol;
        this.cedulausuario = cedulausuario;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getCedulausuario() {
        return cedulausuario;
    }

    public void setCedulausuario(String cedulausuario) {
        this.cedulausuario = cedulausuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idrol;
        hash += (cedulausuario != null ? cedulausuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolusuarioPK)) {
            return false;
        }
        RolusuarioPK other = (RolusuarioPK) object;
        if (this.idrol != other.idrol) {
            return false;
        }
        if ((this.cedulausuario == null && other.cedulausuario != null) || (this.cedulausuario != null && !this.cedulausuario.equals(other.cedulausuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mediticket.entities.RolusuarioPK[ idrol=" + idrol + ", cedulausuario=" + cedulausuario + " ]";
    }
    
}
