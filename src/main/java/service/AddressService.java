package service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Address;

@Service
public class AddressService {
	private static ArrayList<Address> last10 = new ArrayList<Address>();
	
	@Autowired
	GeoLocationService gls;
	
	public ArrayList<Address> getLast10(){
		return last10;
	}
	
	private void addToLast10(Address address){
		if(last10.size() == 10){
			last10.remove(0);
		}
		last10.add(address);
	}

	public Address getAddress(String latlng){
		Address address = gls.getAddress(latlng);
		addToLast10(address);
		return address;
	}

}
