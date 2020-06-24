/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.gpecpf.competence.controleur;

import dz.elit.gpecpf.administration.entity.Prefixcodification;
import dz.elit.gpecpf.administration.service.AdminPrefixCodificationFacade;
import dz.elit.gpecpf.commun.exception.MyException;
import dz.elit.gpecpf.commun.util.AbstractController;
import dz.elit.gpecpf.commun.util.MyUtil;
import dz.elit.gpecpf.competence.entity.Domainecompetence;
import dz.elit.gpecpf.competence.service.DomaineCompetenceFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Kaci Ahmed
 */
@ManagedBean
@ViewScoped
public class AddDomaineCompetenceController extends AbstractController implements Serializable {
   
   @EJB
   DomaineCompetenceFacade domaineFacade;
   
   @EJB
    private AdminPrefixCodificationFacade prefFacade;
    
    private  List<Prefixcodification> listPrefix;
    
    private Domainecompetence domaine;
    
    private List<Domainecompetence> lstDomPere;
    private Domainecompetence domPereSelected;
    private Domainecompetence domPereAff;
    
    private String codePere;
    private String libPere;
    
     public AddDomaineCompetenceController() {
    }

    public void addDomPereConst()
    {  
    }
    
    @Override//@PostConstruct
    protected void initController() {
            initAddDomaineCompetence();      
    }
    private void initAddDomaineCompetence() {
      domaine=new Domainecompetence();
      chercherPrefix();
      domPereSelected=new Domainecompetence(); 
      domPereAff=null;
      lstDomPere=new ArrayList<>();
      lstDomPere=domaineFacade.findAllOrderByAttribut("code");
    }
    public void chercherPrefix()
    {
        listPrefix =new ArrayList<>();
        listPrefix=prefFacade.findAllOrderByAttribut("id");
        if(!listPrefix.isEmpty())
        domaine.setCode(listPrefix.get(0).getDomcomp());
    }
    private boolean isExisteCode(String code) 
    {
        Domainecompetence domaine2 = domaineFacade.findByCode(code);
        if(domaine2 == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public void create() {
        try {  
            domaine.setCode(domaine.getCode().toUpperCase());
            if (isExisteCode(domaine.getCode())) {
            MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_existe_code"));//Erreur inconu   
            }else{
                    domaineFacade.create(domaine);
                    if(domPereSelected != null && domPereSelected.getCode()!=null)
                    {
                        domaineFacade.edit(domPereSelected);
                    }
                    initAddDomaineCompetence();
                     MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));//"Domaine enregistré avec succè");
                 }
            } catch (MyException ex) {
                MyUtil.addErrorMessage(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
            }
        
    }
  
    public void chercherDomPere(){
        lstDomPere=domaineFacade.findByCodeLibelle(codePere, libPere);
    }
    
    public void addDomPereForDom(){
        if(domPereSelected != null && domPereSelected.getCode()!=null)
        {
            domaine.addDomPere(domPereSelected);
            domPereAff=new Domainecompetence();
            domPereAff=domPereSelected;
        }else{
            if(domPereSelected== null && domaine.getIddommere()!=null)
            {
                Domainecompetence dp=domaine.getIddommere();
                dp.getDomainecompetenceCollection().remove(domaine);
                domaine.setIddommere(domPereSelected);
                
            }
        }
    }
    public void viderDompere(){
        domPereSelected=null;
        domPereAff=null;
    }
            
       // getter est setter

    public Domainecompetence getDomaine() {
        return domaine;
    }

    public void setDomaine(Domainecompetence domaine) {
        this.domaine = domaine;
    }

    public DomaineCompetenceFacade getDomaineFacade() {
        return domaineFacade;
    }

    public void setDomaineFacade(DomaineCompetenceFacade domaineFacade) {
        this.domaineFacade = domaineFacade;
    }

    public List<Domainecompetence> getLstDomPere() {
        return lstDomPere;
    }

    public void setLstDomPere(List<Domainecompetence> lstDomPere) {
        this.lstDomPere = lstDomPere;
    }

    public Domainecompetence getDomPereSelected() {
        return domPereSelected;
    }

    public void setDomPereSelected(Domainecompetence domPereSelected) {
        this.domPereSelected = domPereSelected;
    }
    
    public String getCodePere() {
        return codePere;
    }

    public void setCodePere(String codePere) {
        this.codePere = codePere;
    }

    public String getLibPere() {
        return libPere;
    }

    public void setLibPere(String libPere) {
        this.libPere = libPere;
    }

    public Domainecompetence getDomPereAff() {
        return domPereAff;
    }

    public void setDomPereAff(Domainecompetence domPereAff) {
        this.domPereAff = domPereAff;
    }
    

}
