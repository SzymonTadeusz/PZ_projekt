package pl.edu.wat.wcy.model.entities;
public class Country {
		private int countryID;
		private String countryName;
		private String countryAbbrev;
		
		public Country(){}
		
		public Country(String name, String abbr)
		{
			this.setCountryAbbrev(abbr);
			this.setCountryName(name);
		}
		
		public int getCountryID() {
			return countryID;
		}
		public void setCountryID(int countryID) {
			this.countryID = countryID;
		}
		public String getCountryName() {
			return countryName;
		}
		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}
		public String getCountryAbbrev() {
			return countryAbbrev;
		}
		public void setCountryAbbrev(String countryAbbrev) {
			this.countryAbbrev = countryAbbrev;
		}
		
		
}
