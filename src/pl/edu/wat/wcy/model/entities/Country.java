package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Country {
		private int countryID;
		private String countryName;
		private String countryAbbrev;
		private Set<Warehouse> warehouses = new HashSet<Warehouse>();
		
		public Country(){}
		
		public Country(String name, String abbr)
		{
			this.setCountryAbbrev(abbr);
			this.setCountryName(name);
		}
		
		@Override
		public String toString() {
			return (this.countryName + " (" + this.countryAbbrev + ")");
		};
		
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

		public Set<Warehouse> getWarehouses() {
			return warehouses;
		}

		public void setWarehouses(Set<Warehouse> warehouses) {
			this.warehouses = warehouses;
		}
		
		public void addToWarehouses(Warehouse t)
		{
			this.getWarehouses().add(t);
		}
		
		public void removeFromWarehouses(Warehouse t)
		{
			this.getWarehouses().remove(t);
		}
		
		
}
