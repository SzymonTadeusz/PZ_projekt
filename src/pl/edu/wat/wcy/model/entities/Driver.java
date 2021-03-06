package pl.edu.wat.wcy.model.entities;

public class Driver {

	private int driverID;
	private Vehicle driverOf;
	private String firstName;
	private String surname;
	private String idNumber;
	private String licenseNumber;

	public Driver() {
	}

	public Driver(String n, String sn, String idNr, String licNr) {
		this.setFirstName(n);
		this.setSurname(sn);
		this.setIdNumber(idNr);
		this.setLicenseNumber(licNr);
	}
	
	public Driver(String n, String sn, String idNr, String licNr, Vehicle v) {
		this.setFirstName(n);
		this.setSurname(sn);
		this.setIdNumber(idNr);
		this.setLicenseNumber(licNr);
		this.setDriverOf(v);
	}

	@Override
	public String toString() {
		return ("[" +this.firstName + " " + this.surname + ", PESEL:" + this.idNumber + ", DrvLicNr: " + this.licenseNumber + "]");
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Vehicle getDriverOf() {
		return driverOf;
	}

	public void setDriverOf(Vehicle driverOf) {
		this.driverOf = driverOf;
		if(this.driverOf!=null && this.driverOf != driverOf)
		driverOf.setCurrentDriver(this);
	}

}
