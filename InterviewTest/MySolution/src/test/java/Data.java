
public class Data {
	private String phone;
	private String activationDate;
	private String deactivationDate;
	private String resultDate;

	public Data(String phone, String activationDate, String deactivationDate) {
		
		this.phone = phone;
		this.activationDate = activationDate;
		this.deactivationDate = deactivationDate;
	}

	public Data(String phone, String resultDate) {
		this.phone = phone;
		this.resultDate = resultDate;
	}

	public String getActivationDate() {
		return this.activationDate;
	}

	public String getDeactivationDate() {
		return this.deactivationDate;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getResultDate() {
		return this.resultDate;
	}

	
}
