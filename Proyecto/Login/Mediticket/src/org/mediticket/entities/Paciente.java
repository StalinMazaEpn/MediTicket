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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PACIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p")
    , @NamedQuery(name = "Paciente.findByCedulapaciente", query = "SELECT p FROM Paciente p WHERE p.cedulapaciente = :cedulapaciente")
    , @NamedQuery(name = "Paciente.findByNombre", query = "SELECT p FROM Paciente p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Paciente.findByApellido", query = "SELECT p FROM Paciente p WHERE p.apellido = :apellido")
    , @NamedQuery(name = "Paciente.findByTelefono", query = "SELECT p FROM Paciente p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Paciente.findByDireccion", query = "SELECT p FROM Paciente p WHERE p.direccion = :direccion")})
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CEDULAPACIENTE")
    private String cedulapaciente;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedulapaciente")
    private Collection<Turno> turnoCollection;
    @JoinColumn(name = "IDHISTORIAL", referencedColumnName = "IDHISTORIAL")
    @ManyToOne(optional = false)
    private Historial idhistorial;

    public Paciente() {
    }

    public Paciente(String cedulapaciente) {
        this.cedulapaciente = cedulapaciente;
    }

    public Paciente(String cedulapaciente, String nombre, String apellido, String telefono, String direccion) {
        this.cedulapaciente = cedulapaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getCedulapaciente() {
        return cedulapaciente;
    }

    public void setCedulapaciente(String cedulapaciente) {
        this.cedulapaciente = cedulapaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Collection<Turno> getTurnoCollection() {
        return turnoCollection;
    }

    public void setTurnoCollection(Collection<Turno> turnoCollection) {
        this.turnoCollection = turnoCollection;
    }

    public Historial getIdhistorial() {
        return idhistorial;
    }

    public void setIdhistorial(Historial idhistorial) {
        this.idhistorial = idhistorial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulapaciente != null ? cedulapaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.cedulapaciente == null && other.cedulapaciente != null) || (this.cedulapaciente != null && !this.cedulapaciente.equals(other.cedulapaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mediticket.entities.Paciente[ cedulapaciente=" + cedulapaciente + " ]";
    }
    
}
