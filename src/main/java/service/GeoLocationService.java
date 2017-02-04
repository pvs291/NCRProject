package service;

import model.Address;

public interface GeoLocationService {
	public Address getAddress(String latlng);
}
