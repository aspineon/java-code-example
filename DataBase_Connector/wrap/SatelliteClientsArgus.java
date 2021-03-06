package pay.database.wrap;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="SATELLITE_CLIENTS_ARGUS")
public class SatelliteClientsArgus {
	@Id
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator",sequenceName="GEN_SATELLITE_CLIENTS_ARGUS_ID")
	@Column(name="ID")
	private int id;
	
	@Column(name="ID_ORDER")
	private Integer idOrder;

	@Lob
	@Column(name="DATA_FIELD")
	private byte[] data;
	
	@Column(name="DATE_WRITE")
	private Date dateWrite;

	@Column(name="STATE_OF_EXCHANGE",nullable=true)
	private Integer stateOfExchange;
	
	@Column(name="SPEC_TP",nullable=true)
	private Integer specTp;
	
	@Column(name="RET_CODE",nullable=true)
	private Integer retCode;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Date getDateWrite() {
		return dateWrite;
	}

	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}

	public Integer getStateOfExchange() {
		return stateOfExchange;
	}

	public void setStateOfExchange(Integer stateOfExchange) {
		this.stateOfExchange = stateOfExchange;
	}

	public Integer getSpecTp() {
		return specTp;
	}

	public void setSpecTp(Integer specTp) {
		this.specTp = specTp;
	}

	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}
}
