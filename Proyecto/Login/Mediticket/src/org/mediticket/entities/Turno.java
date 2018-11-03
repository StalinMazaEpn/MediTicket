/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josuericardo
 */
@Entity
@Table(name = "TURNO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t")
    , @NamedQuery(name = "Turno.findByIdturno", query = "SELECT t FROM Turno t WHERE t.idturno = :idturno")
    , @NamedQuery(name = "Turno.findByFechaasignacion", query = "SELECT t FROM Turno t WHERE t.fechaasignacion = :fechaasignacion")})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTURNO")
    private Integer idturno;
    @Basic(optional = false)
    @Column(name = "FECHAASIGNACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaasignacion;
    @JoinColumn(name = "IDAREA", referencedColumnName = "IDAREA")
    @ManyToOne(optional = false)
    private Areas idarea;
    @JoinColumn(name = "CEDULAPACIENTE", referencedColumnName = "CEDULAPACIENTE")
    @ManyToOne(optional = false)
    private Paciente cedulapaciente;
    @JoinColumn(name = "CEDULAUSUARIO", referencedColumnName = "CEDULAUSUARIO")
    @ManyToOne
    private Usuario cedulausuario;

    public Turno() {
    }

    public Turno(Integer idturno) {
        this.idturno = idturno;
    }

    public Turno(Integer idturno, Date fechaasignacion) {
        this.idturno = idturno;
        this.fechaasignacion = fechaasignacion;
    }

    public Integer getIdturno() {
        return idturno;
    }

    public void setIdturno(Integer idturno) {
        this.idturno = idturno;
    }

    public Date getFechaasignacion() {
        return fechaasignacion;
    }

    public void setFechaasignacion(Date fechaasignacion) {
        this.fechaasignacion = fechaasignacion;
    }

    public Areas getIdarea() {
        return idarea;
    }

    public void setIdarea(Areas idarea) {
        this.idarea = idarea;
    }

    public Paciente getCedulapaciente() {
        return cedulapaciente;
    }

    public void setCedulapaciente(Paciente cedulapaciente) {
        this.cedulapaciente = cedulapaciente;
    }

    public Usuario getCedulausuario() {
        return cedulausuario;
    }

    public void setCedulausuario(Usuario cedulausuario) {
        this.cedulausuario = cedulausuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idturno != null ? idturno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.idturno == null && other.idturno != null) || (this.idturno != null && !this.idturno.equals(other.idturno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mediticket.entities.Turno[ idturno=" + idturno + " ]";
    }
    
}
