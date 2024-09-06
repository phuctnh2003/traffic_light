package trafficlight.example.phuctnh;

import object.Cluster;
import object.Location;
import object.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class controller {
    functions functions = new functions();
    SQL_functions sql_functions = new SQL_functions();
    mqtt mqtt = new mqtt();

    // <--------Tim cụm đèn theo 2 tên đường---------->
    @GetMapping("/search/name")
    public ArrayList<Location> Search_Name(
            @RequestParam(required = false) String street1,
            @RequestParam(required = false) String street2) throws MyException {
        if (street1 == null || street1.isEmpty() || street2 == null || street2.isEmpty()) {
            throw new MyException(400, "Invalid input: wrong parameter");
        }
        return functions.search_name(street1, street2);
    }
    // <--------Tim cụm đèn theo mã định danh---------->
    @GetMapping("/search/id")
    public ResponseEntity<String> getClusterData(@RequestParam String id) throws MyException
    {
        if (id == null || id.isEmpty()) {
            throw new MyException(400, "Invalid input: wrong parameter");
        }
        return  sql_functions.Search_Cluster_By_ID(id);
    }
    // <--------Gửi dữ liệu cụm đèn cho web---------->
    @GetMapping("/send")
    public ResponseEntity<String> Send_data(@RequestParam(required = false) String id) {
        if (id == null || id.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: wrong parameter");
        }

        return sql_functions.getClusterData(id);
    }


    // <---------------Xóa kich ban cho cum den--------------->
    @PostMapping("/script/delete")
    public ResponseEntity<String> deleteScript(
            @RequestParam String cluster_id,
            @RequestParam String name) throws MyException {
        if (cluster_id == null || cluster_id.isEmpty() && name == null || name.isEmpty()) {
            throw new MyException(400, "Invalid input: wrong parameter");
        }
        try {
            boolean deleted = sql_functions.deleteScript(cluster_id, name);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Script deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Script not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting script: " + e.getMessage());
        }
    }

    // <---------------Them kich ban cho cum den--------------->


    @PostMapping("/script/add")
    public ResponseEntity<String> addScript(
            @RequestParam(required = false) String cluster_id,
            @RequestParam(required = false) String name,
            @RequestBody String payload) throws MyException {
        if (cluster_id == null || cluster_id.isEmpty() && name == null || name.isEmpty()) {
            throw new MyException(400, "Invalid input: wrong parameter");
        }
        try {
            JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();

            // Lấy các trường từ JsonObject
            String red = jsonObject.get("red").getAsString();
            String green = jsonObject.get("green").getAsString();
            String yellow = jsonObject.get("yellow").getAsString();
            String status = jsonObject.get("status").getAsString();

            // Gọi phương thức để thêm kịch bản
            sql_functions.addScript(cluster_id, name, red, green, yellow, status);

            return ResponseEntity.status(HttpStatus.CREATED).body("Script added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding script: " + e.getMessage());
        }
    }

    // <---------------Chinh sua ban cho cum den--------------->

    @PostMapping("/script/update")
    public ResponseEntity<String> updateScript(
            @RequestParam(required = false) String cluster_id,
            @RequestParam(required = false) String name,
            @RequestBody String payload) throws MyException {
        if (cluster_id == null || cluster_id.isEmpty() && name == null || name.isEmpty()) {
            throw new MyException(400, "Invalid input: wrong parameter");
        }

        try {
            JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();

            // Lấy các trường từ JsonObject
            String red = jsonObject.get("red").getAsString();
            String green = jsonObject.get("green").getAsString();
            String yellow = jsonObject.get("yellow").getAsString();
            String status = jsonObject.get("status").getAsString();
            // Gọi phương thức để cập nhật kịch bản
            sql_functions.updateScript(cluster_id, name, red, green, yellow, status);

            return ResponseEntity.status(HttpStatus.OK).body("Script updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating script: " + e.getMessage());
        }
    }

    // <---------------Chinh sua trang thai cum den và gui script mqtt--------------->
    @GetMapping("/script_status/update")
    public ResponseEntity<String> updateScriptAndCurrentScript(
            @RequestParam(required = false) String cluster_id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String script) throws MyException {
        if (cluster_id == null || cluster_id.isEmpty() && name == null || name.isEmpty() && script == null || script.isEmpty()) {
            throw new MyException(400, "Invalid input: wrong parameter");
        }
        try {

            sql_functions.updateScriptAndCurrentScript(cluster_id, name);
            mqtt.connectToMqtt();
            mqtt.sendMessage("server_script", script, 0);
            return ResponseEntity.status(HttpStatus.OK).body("Script and current_script updated successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating script and current_script: " + e.getMessage());
        }
    }

    //<--------------------Gui mqtt kich ban thu cong------------------------>
    @GetMapping("/script_manual")
    public ResponseEntity<String> updateManual(
            @RequestParam(required = false) String cluster_id,
            @RequestParam(required = false) String manual) {
        return sql_functions.updateManualAndSendMqtt(cluster_id, manual);
    }

}
