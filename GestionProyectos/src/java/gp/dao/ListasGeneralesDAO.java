/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.dao;

import gp.model.Expedientes;
import gp.model.HistorialMontos;
import gp.model.Resoluciones;
import java.util.List;

/**
 *
 * @author OGPL
 */
public interface ListasGeneralesDAO {
    public List<String> getExpedientes();
    public List<String> getInformes();
    public List<String> getResoluciones();
    public List<HistorialMontos> getMontosHistorial(String codigo);
    public HistorialMontos getMontoViable(String codigo);
}