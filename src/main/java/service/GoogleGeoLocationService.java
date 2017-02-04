package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import model.Address;

@Service
public class GoogleGeoLocationService implements GeoLocationService {

	@Override
	public Address getAddress(String latlng) {
		
		Address address = new Address();
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" +latlng+ "&sensor=true");
			System.out.println(url);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			// Reading data from url
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String out = "";
			String line="";
			System.out.println("Output from Server .... \n");
			while ((line = br.readLine()) != null) {
				out+=line;
			}
			
			//Parse the json output
			JSONObject json = new JSONObject(out);
			System.out.println(out);
			JSONArray results=json.getJSONArray("results");
			if(results == null || results.length() == 0)
				return address;
			JSONObject rec = results.getJSONObject(0);
			JSONArray address_components=rec.getJSONArray("address_components");
			for(int i=0;i<address_components.length();i++){
				JSONObject rec1 = address_components.getJSONObject(i);
				JSONArray types=rec1.getJSONArray("types");
				String comp=types.getString(0);
				if(comp.equals("street_number")){
					address.setStreet_number(rec1.getString("long_name"));
				}
				else if(comp.equals("route")){
					address.setStreet_name(rec1.getString("long_name"));
				}
				else if(comp.equals("locality")){
					address.setCity(rec1.getString("long_name"));
				}
				else if(comp.equals("administrative_area_level_1")){
					address.setState(rec1.getString("long_name"));
				}
				else if(comp.equals("postal_code")){
					address.setZip(rec1.getString("long_name"));
				}
			}
			
			String[] parts = latlng.split(",");
			address.setLatitude(parts[0]);
			address.setLongitude(parts[1]);
			SimpleDateFormat f = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			address.setLookup_time(f.format(new Date()));
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null)
				conn.disconnect();
		}
	
		return address;
	}

}
