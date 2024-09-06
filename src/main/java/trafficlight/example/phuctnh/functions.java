package trafficlight.example.phuctnh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.Gson;

import object.Location;
import object.MyException;


public class functions {


    // Mã lỗi
    public static final int ERROR_NOMINATIM_REQUEST = 1;
    public static final int ERROR_OSRM_REQUEST = 2;
    public static final int ERROR_JSON_PARSING = 3;
    public static final int ERROR_NO_INTERSECTION_FOUND = 4;
    SQL_functions sqlFunctions = new SQL_functions();
    // <-------Tìm theo tên đường------>
    public ArrayList<Location> search_name(String road1, String road2) throws MyException {
        ArrayList<Location> res = new ArrayList<>();
        ArrayList<Location> list1 = getPlace(road1);
        ArrayList<Location> list2 = getPlace(road2);
        String id = "", street1="", street2="", lat="", lon="", status="";
        street1 = list1.get(0).getDisplay_name();
        street2 = list2.get(0).getDisplay_name();
        if (list1.isEmpty() || list2.isEmpty()) {

            throw new MyException(ERROR_NO_INTERSECTION_FOUND, "No intersection found with given road names.");
        }

        IntersectionData intersectionData = getIntersections(list1.get(0).getLat(), list1.get(0).getLon(), list2.get(0).getLat(), list2.get(0).getLon());
        ArrayList<Location> list4 = getIntersectionCoordinates(intersectionData.getSteps(), list1.get(0).getDisplay_name(), list2.get(0).getDisplay_name());
        res.addAll(list4);

            id = res.get(res.size()-1).getDisplay_name();
            lat = res.get(res.size()-1).getLat();
            lon = res.get(res.size()-1).getLon();

        sqlFunctions.Insert_Cluster(id, street1, street2, lat, lon, "","", "active" );
        return res;
    }

    // <----------Tìm tên đường trả về tọa độ đường----------->
    public ArrayList<Location> getPlace(String locationName) throws MyException {
        ArrayList<Location> list = new ArrayList<>();
        try {
            String encodedLocationName = URLEncoder.encode(locationName.toLowerCase(), "UTF-8");
            String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedLocationName + "&bounded=1";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {

                throw new MyException(ERROR_NOMINATIM_REQUEST, "Failed to fetch data from Nominatim API.");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            Gson gson = new Gson();
            Location[] locations = gson.fromJson(response.toString(), Location[].class);
            for (Location loc : locations) {
                if (!loc.getDisplay_name().toLowerCase().contains("hẻm") && !loc.getDisplay_name().matches(".*\\d+/\\d+.*"))
                    list.add(loc);
            }
        } catch (Exception e) {

            throw new MyException(ERROR_JSON_PARSING, "Error parsing JSON data from Nominatim API.");
        }
        return list;
    }

    // <--------Xử lý API từ OSRM---------->
    public IntersectionData getIntersections(String startLat, String startLon, String endLat, String endLon) throws MyException {
        ArrayList<Step> steps = new ArrayList<>();
        ArrayList<Maneuver> maneuvers = new ArrayList<>();
        try {
            String urlString = "https://router.project-osrm.org/route/v1/driving/" + startLon + "," + startLat + ";" + endLon + "," + endLat + "?steps=true";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {

                throw new MyException(ERROR_OSRM_REQUEST, "Failed to fetch data from OSRM API.");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            Gson gson = new Gson();
            OSRMResponse osrmResponse = gson.fromJson(response.toString(), OSRMResponse.class);
            if (osrmResponse != null && osrmResponse.routes != null) {
                for (Route route : osrmResponse.routes) {
                    for (Leg leg : route.legs) {
                        steps.addAll(leg.steps); // Thêm các bước vào danh sách
                        for (Step step : leg.steps) {
                            if (step.maneuver != null) {
                                maneuvers.add(step.maneuver);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

            throw new MyException(ERROR_JSON_PARSING, "Error parsing JSON data from OSRM API.");
        }
        return new IntersectionData(steps, maneuvers);
    }

    // <-----------Lấy các type: "turn", "end of road"------------>
    public ArrayList<Location> getIntersectionCoordinates(ArrayList<Step> steps, String road1, String road2) {
        ArrayList<Location> intersectionCoordinates = new ArrayList<>();
        String fullroad1 = CatTen(road1);
        String previousName = "";
        String currentName = "";

        for (Step step : steps) {
            String type = step.maneuver != null ? step.maneuver.type : null;
            currentName = step.name;
            if ("depart".equals(type)) {
                previousName = currentName;
                double lat = step.maneuver.location.get(1);
                double lon = step.maneuver.location.get(0);
            }

            if ("turn".equals(type) || "end of road".equals(type)) {
                if (!previousName.equals(currentName)) {
                    double lat = step.maneuver.location.get(1);
                    double lon = step.maneuver.location.get(0);
                    Location location = new Location(String.valueOf(lon), String.valueOf(lat));
                    String intersectionName = DinhDangTenDuong(previousName + fullroad1) + "_" + Xoadaucach_LayKiTuDau(currentName);
                    location.setDisplay_name(intersectionName);
                    intersectionCoordinates.add(location);
                }
                previousName = currentName;
            }
        }
        return intersectionCoordinates;
    }

//    public String getAddressDetails(double lat, double lon) {
//        String addressDetails = "";
//        try {
//            String urlString = "https://nominatim.openstreetmap.org/reverse?lat=" + lat + "&lon=" + lon + "&format=json&addressdetails=1";
//            logger.log(Level.INFO, "Fetching address details from Nominatim API: {0}", urlString);
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                response.append(line);
//            }
//            br.close();
//            Gson gson = new Gson();
//            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
//            JsonObject address = jsonObject.getAsJsonObject("address");
//            if (address != null) {
//                String city = address.has("city") ? address.get("city").getAsString() : "";
//                String district = address.has("district") ? address.get("district").getAsString() : "";
//                String county = address.has("county") ? address.get("county").getAsString() : "";
//                addressDetails = city + "_" + district + "_" + county;
//            }
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error fetching address details.", e);
//        }
//        return addressDetails;
//    }
    // <------Dat ten theo quy tac------>

    public String CatTen(String road)
    {
        int roadEndIndex = road.indexOf(",");
        String res = road.substring(roadEndIndex, road.length()-1);
        return res;
    }

    public String DinhDangTenDuong(String road1) {
        String city = "", district = "", ward = "", intersectionName = "";

        int road1EndIndex = road1.indexOf(",");
        String road1Name = road1.substring(0, road1EndIndex+1);

        if (road1.contains("Thành phố") || road1.contains("TP")) {
            int cityStartIndex = road1.indexOf("Thành phố");
            if (cityStartIndex == -1) {
                cityStartIndex = road1.indexOf("TP");
            }
            city = road1.substring(cityStartIndex, road1.indexOf(",", cityStartIndex)).trim();

        }
        else
        {
            city ="";
        }

        if (road1.contains("Quận") || road1.contains("Huyện")) {
            int districtStartIndex = road1.indexOf("Quận");
            if (districtStartIndex == -1) {
                districtStartIndex = road1.indexOf("Huyện");
            }
            district = road1.substring(districtStartIndex, road1.indexOf(",", districtStartIndex)).trim();

        }
        else
        {
            district ="";
        }

        if (road1.contains("Phường") || road1.contains("Xã")) {
            int wardStartIndex = road1.indexOf("Phường");
            if (wardStartIndex == -1) {
                wardStartIndex = road1.indexOf("Xã");
            }
            ward = road1.substring(wardStartIndex, road1.indexOf(",", wardStartIndex)).trim();

        }
        else
        {
            ward ="";
        }


        if(city == "")
        {
            intersectionName = district + "_" + ward + "_" + road1Name;
        }
        else if(district == "")
        {
            intersectionName = city + "_" + ward + "_" + road1Name;
        }
        else if(ward == "")
        {
            intersectionName = city + "_" + district + "_" +  road1Name;
        }
        else  intersectionName = city + "_" + district + "_" + ward + "_" + road1Name;

        String res = Xoadaucach_LayKiTuDau(intersectionName).toUpperCase();
        // System.out.println(res);
        return res;
    }

    public String Xoadaucach_LayKiTuDau(String place) {
        StringBuilder result = new StringBuilder();
        String[] parts = place.split("_");

        for (String part : parts) {
            String[] words = part.split(" ");
            for (String word : words) {
                if (word.length() > 0) { // Kiểm tra độ dài của từ
                    result.append(word.substring(0, 1));
                }
            }
            result.append("_");
        }

        // Xóa ký tự '_' cuối cùng nếu có
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }


    //<----------------------Nhan kich ban tu server----------------->
    public void GetScript()
    {

    }


}

class OSRMResponse {
    ArrayList<Route> routes;
}

class Route {
    ArrayList<Leg> legs;
}

class Leg {
    ArrayList<Step> steps;
}

class Step {
    Maneuver maneuver;
    String name;
}

class Maneuver {
    ArrayList<Double> location;
    String type;
}
class IntersectionData {
    private ArrayList<Step> steps;
    private ArrayList<Maneuver> maneuvers;

    public IntersectionData(ArrayList<Step> steps, ArrayList<Maneuver> maneuvers) {
        this.steps = steps;
        this.maneuvers = maneuvers;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public ArrayList<Maneuver> getManeuvers() {
        return maneuvers;
    }
}



