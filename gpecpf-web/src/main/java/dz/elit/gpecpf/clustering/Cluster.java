package dz.elit.gpecpf.clustering;

/**
 *
 * @author laidani.youcef
 */
public class Cluster {

	private String host;
	private String statut;

	public Cluster() {
	}

	public Cluster(String host, String statut) {
		this.host = host;
		this.statut = statut;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}
