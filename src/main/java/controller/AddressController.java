package controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Address;
import service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	AddressService as;
	
	@RequestMapping("/last10")
	public ArrayList<Address> getLast10(){
		return as.getLast10();
	}
	
	@RequestMapping(value = "{latlng:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getAddress(@PathVariable("latlng") String latlng){
		try {
			//Input validation
			String[] parts = latlng.split(",");
			Float.parseFloat(parts[0]);
			Float.parseFloat(parts[1]);
			
			return new ResponseEntity<>(as.getAddress(latlng), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Invalid lat long... Try like this 33.969601,-84.100033", HttpStatus.BAD_REQUEST);
		}
	}
	
}
