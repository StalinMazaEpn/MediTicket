/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediticket.controllers;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author josuericardo
 */
public class ConnectionProperties {
    private String stringDeConexion;
    public ConnectionProperties(){
    }
    public ConnectionProperties(String stringDeConexion){
        this.stringDeConexion = stringDeConexion;
    }
    protected Map retornarPropiedadesDeConexion(){
        Map properties = new HashMap();
        /*antes de la coma simpre debe estar, despues puede variar*/
        properties.put("javax.persistence.jdbc.driver","com.microsoft.sqlserver.jdbc.SQLServerDriver");
        /*se borro el localhost ya que el servidor se utilizara con la direccion IP*/
        properties.put("javax.persistence.jdbc.url","jdbc:sqlserver://"+stringDeConexion+":49846;databaseName=MEDITICKET2" );
        properties.put("javax.persistence.jdbc.user", "dave");
        properties.put("javax.persistence.jdbc.password", "dave");
        return properties;
    }
}
