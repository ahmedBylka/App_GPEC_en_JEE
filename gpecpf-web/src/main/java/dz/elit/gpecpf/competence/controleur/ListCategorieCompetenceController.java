/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
package dz.elit.gpecpf.competence.controleur;

import dz.elit.gpecpf.commun.controller.Imprimer;
import dz.elit.gpecpf.commun.reporting.engine.Reporting;
import dz.elit.gpecpf.commun.util.AbstractController;
import dz.elit.gpecpf.commun.util.MyUtil;
import dz.elit.gpecpf.gestion_des_competences.service.CategorieCompetenceFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;
import otherEntity.Categoriecompetence;

/**
 *
 * @author Dell
 *//*
@ManagedBean
@ViewScoped
public class ListCategorieCompetenceController extends AbstractController implements Serializable{
    
    @EJB
    private CategorieCompetenceFacade catFacade;
    
    @ManagedProperty(value = "#{imprimer}")
    private Imprimer ctrImprimer;
    private List<Categoriecompetence> listCat;
    
    //Les variables de recherche
    private String code;
    private String libelle;

    public ListCategorieCompetenceController() {
    }
    
    @Override //PostConstruct
    protected void initController() 
    {    
        findList()  ;  
    }

    public ListCategorieCompetenceController(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    
    private void findList() {
       listCat = catFacade.findAllOrderByAttribut("code");
        rechercher();
    }
    public void rechercher() {
        listCat= catFacade.findByCodeLibelle(code, libelle);
        if (listCat.isEmpty() || listCat.size() < 1) {
            MyUtil.addInfoMessage(MyUtil.getBundleAdmin("msg_resultat_recherche_null"));
        }
    }
    
       public void remove(Categoriecompetence catComp) 
    {
     try {
            catFacade.remove(catComp);
              MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Utilisateur supprimé");
             findList();
         } catch (Exception ex) 
           {
             ex.printStackTrace();
             MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
           }   
    }
    
    public void download() throws SQLException, IOException 
    {
        String rapportLien = "/dz/elit/harmo/commun/reporting/source/listUtilisateur.jasper";
        InputStream rapport = getClass().getResourceAsStream(rapportLien);
        String SUBREPORT_DIR = getClass().getResource("/dz/elit/harmo/commun/reporting/source/Entete/").getFile();
        String rapportNom = "rapportNom";
        String entreprisFr = "entreprisFr";
        String entreprisAr = "entreprisAr";
        String iSoRapport = "iSoRapport";
        InputStream urlLogo = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/images-login/logo.png");
        Map parametres = new HashMap();
        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listCat);
        parametres.put("rapportNom", rapportNom);
        parametres.put("entreprisFr", entreprisFr);
        parametres.put("entreprisAr", entreprisAr);
        parametres.put("iSoRapport", iSoRapport);
        parametres.put("iMgDir", urlLogo);
        parametres.put("SUBREPORT_DIR", SUBREPORT_DIR);
        ctrImprimer.setData(data);
        ctrImprimer.setParametres(parametres);
        ctrImprimer.setRapport(rapport);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("width", 350);
        options.put("contentHeight", 90);
        options.put("contentWidth", 310);
        RequestContext.getCurrentInstance().openDialog("/pages/commun/download.xhtml", options, null);
    }
      public String telecharger() throws IOException, JRException {

        Map<String, String> param = new HashMap<>();
        param.put("rapportNom", "Test");

        Reporting.printEtat(getClass().getResourceAsStream("/dz/elit/gpecpf/reporting/source/test.jasper"),
                param, new JRBeanCollectionDataSource(listCat));
        return "";

    }
    public void creerRapportUnique() throws JRException, FileNotFoundException {

        String rapportLien = "/reporting/source/listUtilisateur.jasper";
        InputStream rapport = getClass().getResourceAsStream(rapportLien);
        String rapportNom = "Test";
        String entreprisFr = "Elit";
        String entreprisAr = "شركة";
        String iSoRapport = "iSoRapport";
        String SUBREPORT_DIR = getClass().getResource("/dz/elit/harmo/commun/reporting/source/Entete/").getFile();
        InputStream urlLogo = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/images-login/logo.png");
        Map parametres = new HashMap();
        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listCat);
        parametres.put("rapportNom", rapportNom);
        parametres.put("entreprisFr", entreprisFr);
        parametres.put("entreprisAr", entreprisAr);
        parametres.put("iSoRapport", iSoRapport);
        parametres.put("iMgDir", urlLogo);
        parametres.put("SUBREPORT_DIR", SUBREPORT_DIR);

        Reporting.downloadReportPdf(rapport, data, parametres);

    }
    
    // getter && setter 

    public CategorieCompetenceFacade getCatFacade() {
        return catFacade;
    }

    public void setCatFacade(CategorieCompetenceFacade catFacade) {
        this.catFacade = catFacade;
    }

    public Imprimer getCtrImprimer() {
        return ctrImprimer;
    }

    public void setCtrImprimer(Imprimer ctrImprimer) {
        this.ctrImprimer = ctrImprimer;
    }

    public List<Categoriecompetence> getListCat() {
        return listCat;
    }

    public void setListCat(List<Categoriecompetence> listCat) {
        this.listCat = listCat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}
*/