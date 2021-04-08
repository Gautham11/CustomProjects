package what3words;

import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.request.Coordinates;
import com.what3words.javawrapper.response.ConvertTo3WA;
import com.what3words.javawrapper.response.ConvertToCoordinates;

public class What3Words {
	
	public static void main(String args[]) {
		What3WordsV3 api = new What3WordsV3("M36SBS5M");
		Coordinates coordinates = new Coordinates(51.79405263698188, -0.07720858064658123);
		ConvertTo3WA words = api.convertTo3wa(coordinates).execute();
		
		ConvertToCoordinates coordinatesfromApi = api.convertToCoordinates("grew.settle.lands").execute();
		        
		System.out.println("co-ordinates: " + coordinatesfromApi);
		
		double lat = coordinatesfromApi.getCoordinates().getLat();
		double lon = coordinatesfromApi.getCoordinates().getLng();
		System.out.println("Longitude is " + lon +" Latitude is " + lat);
	}

}
