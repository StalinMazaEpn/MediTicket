/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josuericardo
 */
@Entity
@Table(name = "HISTORIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historial.findAll", query = "SELECT h FROM Historial h")
    , @NamedQuery(name = "Historial.findByIdhistorial", query = "SELECT h FROM Historial h WHERE h.idhistorial = :idhistorial")
    , @NamedQuery(name = "Historial.findByDiagnostico", query = "SELECT h FROM Historial h WHERE h.diagnostico = :diagnostico")
    , @NamedQuery(name = "Historial.findByProblema", query = "SELECT h FROM Historial h WHERE h.problema = :problema")
    , @NamedQuery(name = "Historial.findByReceta", query = "SELECT h FROM Historial h WHERE h.receta = :receta")})
public class Historial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDHISTORIAL")
    private Integer idhistorial;
    @Basic(optional = false)
    @Column(name = "DIAGNOSTICO")
    private String diagnostico;
    @Basic(optional = false)
    @Column(name = "PROBLEMA")
    private String problema;
    @Basic(optional = false)
    @Column(name = "RECETA")
    private String receta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idhistorial")
    private Collection<Paciente> pacienteCollection;

    public Historial() {
    }

    public Historial(Integer idhistorial) {
        this.idhistorial = idhistorial;
    }

    public Historial(Integer idhistorial, String diagnostico, String problema, String receta) {
        this.idhistorial = idhistorial;
        this.diagnostico = diagnostico;
        this.problema = problema;
        this.receta = receta;
    }

    public Integer getIdhistorial() {
        return idhistorial;
    }

    public void setIdhistorial(Integer idhistorial) {
        this.idhistorial = idhistorial;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    @XmlTransient
    public Collection<Paciente> getPacienteCollection() {
        return pacienteCollection;
    }

    public void setPacienteCollection(Collection<Paciente> pacienteCollection) {
        this.pacienteCollection = pacienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistorial != null ? idhistorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historial)) {
            return false;
        }
        Historial other = (Historial) object;
        if ((this.idhistorial == null && other.idhistorial != null) || (this.idhistorial != null && !this.idhistorial.equals(other.idhistorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mediticket.entities.Historial[ idhistorial=" + idhistorial + " ]";
    }
    
}
