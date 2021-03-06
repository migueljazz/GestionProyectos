/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.bean;

import gp.dao.BusquedaInversionDAO;
import gp.dao.ListasGeneralesDAO;
import gp.dao.RegistroInversionDAO;
import gp.dao.ReporteInversionDAO;
import gp.daoImpl.BusquedaInversionDaoImpl;
import gp.daoImpl.ListasGeneralesDaoImpl;
import gp.daoImpl.RegistroInversionDaoImpl;
import gp.daoImpl.ReporteInversionDaoImpl;
import gp.exporter.RealizarReporte;
import gp.model.Ejecucion;
import gp.model.ExpedienteTecnico;
import gp.model.GraficasMontosAnios;
import gp.model.MostrarDesdeDependencias;
import gp.model.MostrarEjecucion2;
import gp.model.MostrarTablaEjecucion;
import gp.model.NuevosDocumentos;
import gp.model.RIdatosEjecucion;
import gp.model.RIdatosProyecto;
import gp.model.Usuario;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author OGPL
 */
@ManagedBean
@ViewScoped
public class ReporteInversion {

    private String codProy;
    private String etapa;
    private List<String> listaetapas;
    private RegistroInversionDAO rid;
    private ReporteInversionDAO rinvD;
    private List<RIdatosProyecto> listaDatos;
    private List<RIdatosEjecucion> listaEjecucion;
    private String aniodeduadic;
    private List<String> aniosdeduadic;
    private String anio;
    private List<String> anios;
    private BusquedaInversionDAO bid;
    private boolean mostrar;
    private boolean mostrar2;
    private boolean mostrar1;
    private String componente;
    private List<String> componentes;
    private List<MostrarTablaEjecucion> listaTabEjec;
    private List<Ejecucion> listaTabEjec2;
    private List<MostrarEjecucion2> listaMostrarEjecu;
    private List<NuevosDocumentos> listaDA1;
    private List<NuevosDocumentos> listaDA2;
    private BigDecimal suma;
    private BigDecimal suma2;
    private BigDecimal suma3;
    private BigDecimal suma4;
    private BigDecimal suma5;
    private BigDecimal suma6;
    private BigDecimal suma7;
    private BigDecimal suma8;
    private BigDecimal suma9;
    private List<String> aniosDedAdic;
    private List<ExpedienteTecnico> listaExpedientes;
    private String anioDedAdic;
    private String codigoProyecto;
    private String nombreProyecto;
    private String tipo_origen;
    private String origen;
    private List<String> listaOrigenes;
    private ListasGeneralesDAO lgd;
    private List<MostrarDesdeDependencias> listaDesdeOrig;
    private BarChartModel barModel;
    private List<GraficasMontosAnios> lgma;
    private final FacesContext faceContext;
    private final Usuario usu;

    public ReporteInversion() {
        faceContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(true);
        usu = (Usuario) session.getAttribute("sesionUsuario");
        lgma = new ArrayList<GraficasMontosAnios>();
        listaDesdeOrig = new ArrayList<MostrarDesdeDependencias>();
        listaOrigenes = new ArrayList<String>();
        lgd = new ListasGeneralesDaoImpl();
        listaExpedientes = new ArrayList<ExpedienteTecnico>();
        listaMostrarEjecu = new ArrayList<MostrarEjecucion2>();
        listaetapas = new ArrayList<String>();
        listaDA1 = new ArrayList<NuevosDocumentos>();
        listaDA2 = new ArrayList<NuevosDocumentos>();
        aniosDedAdic = new ArrayList<String>();
        listaDatos = new ArrayList<RIdatosProyecto>();
        rid = new RegistroInversionDaoImpl();
        rinvD = new ReporteInversionDaoImpl();
        componentes = new ArrayList<String>();
        listaTabEjec = new ArrayList<MostrarTablaEjecucion>();
        listaTabEjec2 = new ArrayList<Ejecucion>();
        listaEjecucion = new ArrayList<RIdatosEjecucion>();
        bid = new BusquedaInversionDaoImpl();
        mostrar = false;
        mostrar2 = false;
        mostrar1 = false;
        llenarComponentes();

    }

    public void llenarListaAniosEjecucion() {
        System.out.println("El anio es: " + anio);
        listaTabEjec2.clear();
        try {
            listaTabEjec2 = rinvD.getEjecucionXanios(codProy, anio);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void llenarListaDesdeOrigen() {
        try {
            listaDesdeOrig = rinvD.getListaDesdeDepes(lgd.getIdDepencencia(origen));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String sumaEjecucionFinal() {
        suma9 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaDesdeOrig.size()) {
            suma9 = suma9.add(BigDecimal.valueOf(listaDesdeOrig.get(i).getMontoTotal()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma9);
    }

    public void imprimirReporteDependencia() {
        try {
            RealizarReporte reporte = new RealizarReporte();
            reporte.reportePoryectosxNombre2(usu, String.valueOf(lgd.getIdDepencencia(origen)), "Reporte11");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void llenarOrigen() {
        try {
            listaOrigenes = lgd.getDependencias(tipo_origen);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> coincidencias(String query) {

        List<String> cadena = new ArrayList<String>();
        try {
            cadena = rid.getCoincidencias(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return cadena;
    }

    public String sumaEjecucion1() {
        suma = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec.size()) {
            suma = suma.add(BigDecimal.valueOf(listaTabEjec.get(i).getMonto()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma);
    }

    public String sumaEjecucion2() {
        suma2 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec2.size()) {
            suma2 = suma2.add(BigDecimal.valueOf(listaTabEjec2.get(i).getMonto()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma2);
    }

    public String sumaEjecucion3() {
        suma3 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec2.size()) {
            suma3 = suma3.add(BigDecimal.valueOf(listaTabEjec2.get(i).getC1E()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma3);
    }

    public String sumaEjecucion4() {
        suma4 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec2.size()) {
            System.out.println("Numero: " + listaTabEjec2.get(i).getC2E());
            suma4 = suma4.add(BigDecimal.valueOf(listaTabEjec2.get(i).getC2E()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma4);
    }

    public String sumaEjecucion5() {
        suma5 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec2.size()) {
            suma5 = suma5.add(BigDecimal.valueOf(listaTabEjec2.get(i).getC3E()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma5);
    }

    public String sumaEjecucion6() {
        suma6 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec2.size()) {
            suma6 = suma6.add(BigDecimal.valueOf(listaTabEjec2.get(i).getC4E()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma6);
    }

    public String sumaEjecucion7() {
        suma7 = new BigDecimal(0.0);
        int i = 0;
        System.out.println("-----");
        while (i < listaTabEjec2.size()) {
            System.out.println("Numero: " + listaTabEjec2.get(i).getC5E());
            suma7 = suma7.add(BigDecimal.valueOf(listaTabEjec2.get(i).getC5E()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma7);
    }

    public String sumaEjecucion8() {
        suma8 = new BigDecimal(0.0);
        int i = 0;
        while (i < listaTabEjec2.size()) {
            suma8 = suma8.add(BigDecimal.valueOf(listaTabEjec2.get(i).getC6E()));
            i++;
        }
        return new DecimalFormat("###,###.###").format(suma8);
    }

    public void llenarComponentes() {
        componentes.clear();
        componentes.add("EXPEDIENTE TÉCNICO");
        componentes.add("INFRAESTRUCTURA");
        componentes.add("EQUIPAMIENTO Y MOBILIARIO");
        componentes.add("SUPERVISION");
        componentes.add("CAPACITACION");
        componentes.add("OTROS");

    }
    /*
     PARA LAS GRÁFICAS
     */

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        ChartSeries montos = new ChartSeries();
        montos.setLabel("Años");
        this.lgma = rinvD.getAniosMontos(codProy);
        int i = 0;
        while (i < lgma.size()) {
            montos.set(lgma.get(i).getAnio(), lgma.get(i).getMonto());
            i++;
        }
        model.addSeries(montos);
        return model;
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("GRÁFICA MONTOS");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Años");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Montos");
        yAxis.setMin(0);
        yAxis.setMax(100000000);
    }
    /*
     PARA LAS GRÁFICAS
     */

    public void mostrar() {
        listaDatos.clear();
        listaTabEjec.clear();
        listaTabEjec2.clear();
        try {
            mostrar = true;
            System.out.println("listadatos");
            listaDatos = rinvD.getDatosProyEjecu(codProy);
            codigoProyecto = String.valueOf(listaDatos.get(0).getProyectoCodigo());
            nombreProyecto = listaDatos.get(0).getNombreProyecto();
            System.out.println("ejecucionanios");
            anios = bid.getEjecucionAnios(codProy);
            System.out.println("listaetapas");
            listaetapas = rid.getListaEtapas(codProy);
            System.out.println("montos por aaños");
            listaTabEjec = rinvD.getEjecu1(codProy);
            this.listaExpedientes = rinvD.getExpediente(codProy);
            createBarModel();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getLocalizedMessage() + " " + e.getCause());
            e.printStackTrace();
        }
    }

    public void imprimirEjecucionPorAnio() {
        try {
            RealizarReporte report = new RealizarReporte();
            report.reporteEjecucionPorAnio("Reporte7", usu, codProy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirEjecucionPorMes() {
        try {
            RealizarReporte report = new RealizarReporte();
            report.reporteEjecucionPorMes("Reporte8", usu, codProy, anio, this.nombreProyecto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirDeductivosAdicionales() {
        try {
            RealizarReporte report = new RealizarReporte();
            report.reporteAdicionalesDeductivos("Reporte9", usu, codProy, this.nombreProyecto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirExpedientesTecn() {
        try {
            RealizarReporte report = new RealizarReporte();
            report.reporteExpedientesTecnicos("Reporte10", usu, codProy, this.nombreProyecto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void imprimirReportePorDependencia() {
        try {

        } catch (Exception e) {

        }
    }

    public void mostrar2() {
        listaDA1.clear();
        listaDA2.clear();
        try {
            listaDA1 = rinvD.getAdicionales(codProy, etapa);
            listaDA2 = rinvD.getDeductivos(codProy, etapa);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCodProy() {
        return codProy;
    }

    public void setCodProy(String codProy) {
        this.codProy = codProy;
    }

    public RegistroInversionDAO getRid() {
        return rid;
    }

    public void setRid(RegistroInversionDAO rid) {
        this.rid = rid;
    }

    public List<RIdatosProyecto> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<RIdatosProyecto> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public List<RIdatosEjecucion> getListaEjecucion() {
        return listaEjecucion;
    }

    public void setListaEjecucion(List<RIdatosEjecucion> listaEjecucion) {
        this.listaEjecucion = listaEjecucion;
    }

    public List<String> getAnios() {
        return anios;
    }

    public void setAnios(List<String> anios) {
        this.anios = anios;
    }

    public BusquedaInversionDAO getBid() {
        return bid;
    }

    public void setBid(BusquedaInversionDAO bid) {
        this.bid = bid;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public boolean isMostrar2() {
        return mostrar2;
    }

    public void setMostrar2(boolean mostrar2) {
        this.mostrar2 = mostrar2;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public List<String> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes;
    }

    public List<MostrarTablaEjecucion> getListaTabEjec() {
        return listaTabEjec;
    }

    public void setListaTabEjec(List<MostrarTablaEjecucion> listaTabEjec) {
        this.listaTabEjec = listaTabEjec;
    }

    public List<Ejecucion> getListaTabEjec2() {
        return listaTabEjec2;
    }

    public void setListaTabEjec2(List<Ejecucion> listaTabEjec2) {
        this.listaTabEjec2 = listaTabEjec2;
    }

    public ReporteInversionDAO getRinvD() {
        return rinvD;
    }

    public void setRinvD(ReporteInversionDAO rinvD) {
        this.rinvD = rinvD;
    }

    public boolean isMostrar1() {
        return mostrar1;
    }

    public void setMostrar1(boolean mostrar1) {
        this.mostrar1 = mostrar1;
    }

    public List<String> getAniosDedAdic() {
        return aniosDedAdic;
    }

    public void setAniosDedAdic(List<String> aniosDedAdic) {
        this.aniosDedAdic = aniosDedAdic;
    }

    public String getAnioDedAdic() {
        return anioDedAdic;
    }

    public void setAnioDedAdic(String anioDedAdic) {
        this.anioDedAdic = anioDedAdic;
    }

    public List<NuevosDocumentos> getListaDA1() {
        return listaDA1;
    }

    public void setListaDA1(List<NuevosDocumentos> listaDA1) {
        this.listaDA1 = listaDA1;
    }

    public List<NuevosDocumentos> getListaDA2() {
        return listaDA2;
    }

    public void setListaDA2(List<NuevosDocumentos> listaDA2) {
        this.listaDA2 = listaDA2;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public List<String> getListaetapas() {
        return listaetapas;
    }

    public void setListaetapas(List<String> listaetapas) {
        this.listaetapas = listaetapas;
    }

    public BigDecimal getSuma() {
        return suma;
    }

    public void setSuma(BigDecimal suma) {
        this.suma = suma;
    }

    public BigDecimal getSuma2() {
        return suma2;
    }

    public void setSuma2(BigDecimal suma2) {
        this.suma2 = suma2;
    }

    public String getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public List<MostrarEjecucion2> getListaMostrarEjecu() {
        return listaMostrarEjecu;
    }

    public void setListaMostrarEjecu(List<MostrarEjecucion2> listaMostrarEjecu) {
        this.listaMostrarEjecu = listaMostrarEjecu;
    }

    public String getAniodeduadic() {
        return aniodeduadic;
    }

    public void setAniodeduadic(String aniodeduadic) {
        this.aniodeduadic = aniodeduadic;
    }

    public List<String> getAniosdeduadic() {
        return aniosdeduadic;
    }

    public void setAniosdeduadic(List<String> aniosdeduadic) {
        this.aniosdeduadic = aniosdeduadic;
    }

    public List<ExpedienteTecnico> getListaExpedientes() {
        return listaExpedientes;
    }

    public void setListaExpedientes(List<ExpedienteTecnico> listaExpedientes) {
        this.listaExpedientes = listaExpedientes;
    }

    public String getTipo_origen() {
        return tipo_origen;
    }

    public void setTipo_origen(String tipo_origen) {
        this.tipo_origen = tipo_origen;
    }

    public List<String> getListaOrigenes() {
        return listaOrigenes;
    }

    public void setListaOrigenes(List<String> listaOrigenes) {
        this.listaOrigenes = listaOrigenes;
    }

    public ListasGeneralesDAO getLgd() {
        return lgd;
    }

    public void setLgd(ListasGeneralesDAO lgd) {
        this.lgd = lgd;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public List<MostrarDesdeDependencias> getListaDesdeOrig() {
        return listaDesdeOrig;
    }

    public void setListaDesdeOrig(List<MostrarDesdeDependencias> listaDesdeOrig) {
        this.listaDesdeOrig = listaDesdeOrig;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public List<GraficasMontosAnios> getLgma() {
        return lgma;
    }

    public void setLgma(List<GraficasMontosAnios> lgma) {
        this.lgma = lgma;
    }

    public BigDecimal getSuma3() {
        return suma3;
    }

    public void setSuma3(BigDecimal suma3) {
        this.suma3 = suma3;
    }

    public BigDecimal getSuma4() {
        return suma4;
    }

    public void setSuma4(BigDecimal suma4) {
        this.suma4 = suma4;
    }

    public BigDecimal getSuma5() {
        return suma5;
    }

    public void setSuma5(BigDecimal suma5) {
        this.suma5 = suma5;
    }

    public BigDecimal getSuma6() {
        return suma6;
    }

    public void setSuma6(BigDecimal suma6) {
        this.suma6 = suma6;
    }

    public BigDecimal getSuma7() {
        return suma7;
    }

    public void setSuma7(BigDecimal suma7) {
        this.suma7 = suma7;
    }

    public BigDecimal getSuma8() {
        return suma8;
    }

    public void setSuma8(BigDecimal suma8) {
        this.suma8 = suma8;
    }

    public BigDecimal getSuma9() {
        return suma9;
    }

    public void setSuma9(BigDecimal suma9) {
        this.suma9 = suma9;
    }

}
