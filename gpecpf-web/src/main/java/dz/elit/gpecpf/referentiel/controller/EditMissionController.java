package dz.elit.gpecpf.referentiel.controller;

import dz.elit.gpecpf.poste.service.MissionFacade;
import dz.elit.gpecpf.commun.exception.MyException;
import dz.elit.gpecpf.commun.util.AbstractController;
import dz.elit.gpecpf.commun.util.MyUtil;
import dz.elit.gpecpf.poste.entity.Mission;
import dz.elit.gpecpf.poste.entity.Activite;
import dz.elit.gpecpf.poste.service.ActiviteFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Nadir Ben Mohand
 */
@ManagedBean
@ViewScoped
public class EditMissionController extends AbstractController implements Serializable {

    @EJB
    private MissionFacade missionFacade;
	@EJB
    private ActiviteFacade activiteFacade;

    private Mission mission;
	
	private List<Activite> listActivites;
    private List<Activite> listActivitesSelected;

    private String code;
    private String libelle;
	private String description;

    /**
     * Creates a new instance of AddProfilController
     */
    public EditMissionController() {
    }

    @Override//@PostConstruct
    protected void initController() {
        initAddMission();
        mission = new Mission();
		listActivites = activiteFacade.findAllOrderByAttribut("code");
        String id = MyUtil.getRequestParameter("id");
        if (id != null) {
            mission = missionFacade.find(Integer.parseInt(id));
			listActivites.removeAll(mission.getListActivites());
        }
    }

	public void addActivitesForMission() {
        if(!listActivitesSelected.isEmpty()) {
			mission.addListActivites(listActivitesSelected);
			listActivites.removeAll(listActivitesSelected);
            listActivitesSelected = new ArrayList<>();
        }
    }
	
	public void removeActiviteForMission(Activite activite) {
		mission.removeActivite(activite);
		listActivites.add(activite);
	}
	
    public void edit() {
        try {
            missionFacade.edit(mission);
            MyUtil.addInfoMessage(MyUtil.getBundleCommun("msg_operation_effectue_avec_succes"));
            initAddMission();
        } catch (MyException ex) {
            ex.printStackTrace();
            MyUtil.addErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            MyUtil.addErrorMessage(MyUtil.getBundleCommun("msg_erreur_inconu"));//Erreur inconu
        }
    }

    private void initAddMission() {
        mission = new Mission();
        listActivites = new ArrayList();
        listActivitesSelected = new ArrayList<>();
    }

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public List<Activite> getListActivites() {
		return listActivites;
	}

	public void setListActivites(List<Activite> listActivites) {
		this.listActivites = listActivites;
	}

	public List<Activite> getListActivitesSelected() {
		return listActivitesSelected;
	}

	public void setListActivitesSelected(List<Activite> listActivitesSelected) {
		this.listActivitesSelected = listActivitesSelected;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public MissionFacade getMissionFacade() {
		return missionFacade;
	}

	public void setMissionFacade(MissionFacade missionFacade) {
		this.missionFacade = missionFacade;
	}

	public ActiviteFacade getActiviteFacade() {
		return activiteFacade;
	}

	public void setActiviteFacade(ActiviteFacade activiteFacade) {
		this.activiteFacade = activiteFacade;
	}
	
}