package trafficlight.example.phuctnh;
import object.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.ArrayList;
public class SQL_functions {

    private static final String jdbcURL = "jdbc:postgresql://database.neosoft.local:5432/devdb";
    private static final String username = "neodb";
    private static final String password = "Neodb20#";
    mqtt mqtt = new mqtt();
    // Cluster Table Methods
    public void Create_Clusters() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS clusters (" +
                    "cluster_id VARCHAR(255) PRIMARY KEY, " +
                    "street_name1 VARCHAR(255), " +
                    "street_name2 VARCHAR(255), " +
                    "latitude VARCHAR(255), " +
                    "longitude VARCHAR(255), " +
                    "current_script VARCHAR(255), " +
                    "manual VARCHAR(3), " +
                    "cluster_status VARCHAR(255)" +
                    ")";
            statement.execute(table);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean Insert_Cluster(String cluster_id, String street_name1, String street_name2, String latitude, String longitude, String current_script, String manual, String cluster_status) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String insert = "INSERT INTO clusters(cluster_id, street_name1, street_name2, latitude, longitude, current_script, manual, cluster_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, cluster_id);
            preparedStatement.setString(2, street_name1);
            preparedStatement.setString(3, street_name2);
            preparedStatement.setString(4, latitude);
            preparedStatement.setString(5, longitude);
            preparedStatement.setString(6, current_script);
            preparedStatement.setString(7, manual);
            preparedStatement.setString(8, cluster_status);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Update_Cluster(String cluster_id, String street_name1, String street_name2, String latitude, String longitude, String current_script, String manual, String cluster_status) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String update = "UPDATE clusters SET street_name1 = ?, street_name2 = ?, latitude = ?, longitude = ?, current_script = ?, manual = ?, cluster_status = ? WHERE cluster_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, street_name1);
            preparedStatement.setString(2, street_name2);
            preparedStatement.setString(3, latitude);
            preparedStatement.setString(4, longitude);
            preparedStatement.setString(5, current_script);
            preparedStatement.setString(6, manual);
            preparedStatement.setString(7, cluster_status);
            preparedStatement.setString(8, cluster_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Cluster> Show_Clusters() {
        ArrayList<Cluster> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM clusters";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Cluster cluster = new Cluster();
                cluster.setCluster_id(resultSet.getString("cluster_id"));
                cluster.setStreet_name1(resultSet.getString("street_name1"));
                cluster.setStreet_name2(resultSet.getString("street_name2"));
                cluster.setLatitude(resultSet.getString("latitude"));
                cluster.setLongitude(resultSet.getString("longitude"));
                cluster.setCurrent_script(resultSet.getString("current_script"));
                cluster.setManual(resultSet.getString("manual"));
                cluster.setCluster_status(resultSet.getString("cluster_status"));
                list.add(cluster);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    //<--------------------------SCRIPTS METHODS--------------------------------->


    public void Create_Scripts() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS scripts (" +
                    "name VARCHAR(255), " +
                    "cluster_id VARCHAR(255), " +
                    "red TEXT, " +
                    "green TEXT, " +
                    "yellow TEXT, " +
                    "scripts_status VARCHAR(255), " +
                    "FOREIGN KEY (cluster_id) REFERENCES clusters(cluster_id)" +
                    ")";
            statement.execute(table);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean Insert_Script(String name,String cluster_id, String red, String green, String yellow, String scripts_status) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String insert = "INSERT INTO scripts (name, cluster_id, red, green, yellow, scripts_status) " + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, cluster_id);
            preparedStatement.setString(3, red);
            preparedStatement.setString(4, green);
            preparedStatement.setString(5, yellow);
            preparedStatement.setString(6, scripts_status);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean Update_Script(String name, String cluster_id, String red, String green, String yellow, String scripts_status) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String update = "UPDATE scripts SET cluster_id = ?, red = ?, green = ?, yellow = ?, scripts_status = ? WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, cluster_id);
            preparedStatement.setString(2, red);
            preparedStatement.setString(3, green);
            preparedStatement.setString(4, yellow);
            preparedStatement.setString(5, scripts_status);
            preparedStatement.setString(6, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Script> Show_Scripts() {
        ArrayList<Script> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM scripts";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Script script = new Script();
                script.setName(resultSet.getString("name"));
                script.setCluster_id(resultSet.getString("cluster_id"));
                script.setRed(resultSet.getString("red"));
                script.setGreen(resultSet.getString("green"));
                script.setYellow(resultSet.getString("yellow"));
                script.setScripts_status(resultSet.getString("scripts_status"));
                list.add(script);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //<--------------------------HISTORY METHODS--------------------------------->

    public void Create_History_Table() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS history (" +
                    "history_id SERIAL PRIMARY KEY, " +
                    "searchword TEXT, " +
                    "keyword TEXT, " +
                    "latitude VARCHAR(255), " +
                    "longitude VARCHAR(255), " +
                    "time TIMESTAMP" +
                    ")";
            statement.execute(table);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean Insert_History(String searchword, String keyword, String latitude, String longitude, Timestamp time) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String insert = "INSERT INTO history(searchword, keyword, latitude, longitude, time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, searchword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, latitude);
            preparedStatement.setString(4, longitude);
            preparedStatement.setTimestamp(5, time);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<History> Show_History() {
        ArrayList<History> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM history";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                History history = new History();
                history.setHistory_id(resultSet.getInt("history_id"));
                history.setSearchword(resultSet.getString("searchword"));
                history.setKeyword(resultSet.getString("keyword"));
                history.setLatitude(resultSet.getString("latitude"));
                history.setLongitude(resultSet.getString("longitude"));
                history.setTime(resultSet.getTimestamp("time"));
                list.add(history);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //<--------------------------SCHEDULE METHODS--------------------------------->



    public void Create_Schedule_Table() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS schedule (" +
                    "schedule_id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "date VARCHAR(255), " +
                    "\"start\" VARCHAR(255), " +
                    "\"end\"  VARCHAR(255)" +
                    ")";
            statement.execute(table);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean Insert_Schedule( String name, String date, String start, String end) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String insert = "INSERT INTO schedule( name, date, \"start\", \"end\") VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, start);
            preparedStatement.setString(4, end);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Schedule> Show_Schedule() {
        ArrayList<Schedule> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM schedule";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setSchedule_id(resultSet.getInt("schedule_id"));
                schedule.setName(resultSet.getString("name"));
                schedule.setDate(resultSet.getString("date"));
                schedule.setStart(resultSet.getString("start"));
                schedule.setEnd(resultSet.getString("end"));
                list.add(schedule);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //<--------------------------OTHER METHODS--------------------------------->

    //Xóa bảng
    public void Drop(String table) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String delete_table = "DROP TABLE " + table;
            statement.execute(delete_table);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    // Tìm kiêm theo mã định danh
    public ResponseEntity<String> Search_Cluster_By_ID(String clusterId) {
        JSONObject result = new JSONObject();  // Main result object
        JSONObject properties = new JSONObject();  // Properties object to hold other data
        JSONObject scriptJson = new JSONObject();  // To hold script details
        JSONObject scheduleJson = new JSONObject();  // To hold schedule details

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

            // Query to get data from clusters table
            String clusterQuery = "SELECT current_script, manual, cluster_status, latitude, longitude FROM clusters WHERE cluster_id = ?";
            PreparedStatement clusterStmt = connection.prepareStatement(clusterQuery);
            clusterStmt.setString(1, clusterId);
            ResultSet clusterRs = clusterStmt.executeQuery();

            if (!clusterRs.next()) {
                // Cluster ID not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cluster not found.");
            }

            // Add cluster details to the properties object
            properties.put("group", clusterId);
            properties.put("status", clusterRs.getString("cluster_status"));
            properties.put("ledon", clusterRs.getString("manual"));

            // Add latitude and longitude at the same level as 'properties'
            result.put("latitude", clusterRs.getString("latitude"));
            result.put("longitude", clusterRs.getString("longitude"));

            // Query to get data from scripts table
            String scriptsQuery = "SELECT name, red, green, yellow, scripts_status FROM scripts WHERE cluster_id = ?";
            PreparedStatement scriptsStmt = connection.prepareStatement(scriptsQuery);
            scriptsStmt.setString(1, clusterId);
            ResultSet scriptsRs = scriptsStmt.executeQuery();

            if (!scriptsRs.next()) {
                // No data found in the scripts table
                properties.put("script", new JSONObject()); // Ensure 'script' key is present
            } else {
                int scriptIndex = 1;
                do {
                    JSONObject script = new JSONObject();
                    script.put("name", scriptsRs.getString("name"));
                    script.put("scr_red", scriptsRs.getString("red"));
                    script.put("scr_green", scriptsRs.getString("green"));
                    script.put("scr_yellow", scriptsRs.getString("yellow"));
                    script.put("status", scriptsRs.getString("scripts_status"));
                    scriptJson.put(String.valueOf(scriptIndex), script);
                    scriptIndex++;
                } while (scriptsRs.next());
                properties.put("script", scriptJson);
            }

            // Query to get data from schedule table
            String scheduleQuery = "SELECT schedule_id, date, \"start\", \"end\", name FROM schedule WHERE name IN (SELECT name FROM scripts WHERE cluster_id = ?)";
            PreparedStatement scheduleStmt = connection.prepareStatement(scheduleQuery);
            scheduleStmt.setString(1, clusterId);
            ResultSet scheduleRs = scheduleStmt.executeQuery();

            if (!scheduleRs.next()) {
                // No data found in the schedule table
                properties.put("schedule", new JSONObject()); // Ensure 'schedule' key is present
            } else {
                int scheduleIndex = 1;
                do {
                    JSONObject schedule = new JSONObject();
                    schedule.put("schedule_id", scheduleRs.getString("schedule_id"));
                    schedule.put("date", scheduleRs.getString("date"));
                    schedule.put("start", scheduleRs.getString("start"));
                    schedule.put("end", scheduleRs.getString("end"));
                    schedule.put("script", scheduleRs.getString("name"));
                    scheduleJson.put(String.valueOf(scheduleIndex), schedule);
                    scheduleIndex++;
                } while (scheduleRs.next());
                properties.put("schedule", scheduleJson);
            }

            // Add the properties object to the main result
            result.put("properties", properties);

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accessing database.");
        }

        // Check if the properties object is empty
        if (result.getJSONObject("properties").isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found for cluster.");
        }

        // Return the result as a response
        return ResponseEntity.ok(result.toString());
    }



    // Trả về data cụm đèn
    public ResponseEntity<String> getClusterData(String clusterId) {
        JSONObject result = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject scriptJson = new JSONObject();
        JSONObject scheduleJson = new JSONObject();

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

            // Lấy dữ liệu từ bảng clusters
            String clusterQuery = "SELECT current_script, manual, cluster_status FROM clusters WHERE cluster_id = ?";
            PreparedStatement clusterStmt = connection.prepareStatement(clusterQuery);
            clusterStmt.setString(1, clusterId);
            ResultSet clusterRs = clusterStmt.executeQuery();

            if (!clusterRs.next()) {
                // Không tìm thấy clusterId trong bảng clusters
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cluster not found.");
            }

            properties.put("group", clusterId);
            properties.put("status", clusterRs.getString("cluster_status"));
            properties.put("ledon", clusterRs.getString("manual"));

            // Lấy dữ liệu từ bảng scripts
            String scriptsQuery = "SELECT name, red, green, yellow, scripts_status FROM scripts WHERE cluster_id = ?";
            PreparedStatement scriptsStmt = connection.prepareStatement(scriptsQuery);
            scriptsStmt.setString(1, clusterId);
            ResultSet scriptsRs = scriptsStmt.executeQuery();

            if (!scriptsRs.next()) {
                // Không tìm thấy dữ liệu trong bảng scripts
                properties.put("script", new JSONObject()); // Đảm bảo key 'script' có mặt
            } else {
                int scriptIndex = 1;
                do {
                    JSONObject script = new JSONObject();
                    script.put("name", scriptsRs.getString("name"));
                    script.put("scr_red", scriptsRs.getString("red"));
                    script.put("scr_green", scriptsRs.getString("green"));
                    script.put("scr_yellow", scriptsRs.getString("yellow"));
                    script.put("status", scriptsRs.getString("scripts_status"));
                    scriptJson.put(String.valueOf(scriptIndex), script);
                    scriptIndex++;
                } while (scriptsRs.next());
                properties.put("script", scriptJson);
            }

            // Lấy dữ liệu từ bảng schedule
            String scheduleQuery = "SELECT schedule_id, date, \"start\", \"end\", name FROM schedule WHERE name IN (SELECT name FROM scripts WHERE cluster_id = ?)";
            PreparedStatement scheduleStmt = connection.prepareStatement(scheduleQuery);
            scheduleStmt.setString(1, clusterId);
            ResultSet scheduleRs = scheduleStmt.executeQuery();

            if (!scheduleRs.next()) {
                // Không tìm thấy dữ liệu trong bảng schedule
                properties.put("schedule", new JSONObject()); // Đảm bảo key 'schedule' có mặt
            } else {
                int scheduleIndex = 1;
                do {
                    JSONObject schedule = new JSONObject();
                    schedule.put("schedule_id", scheduleRs.getString("schedule_id"));
                    schedule.put("date", scheduleRs.getString("date"));
                    schedule.put("start", scheduleRs.getString("start"));
                    schedule.put("end", scheduleRs.getString("end"));
                    schedule.put("script", scheduleRs.getString("name"));
                    scheduleJson.put(String.valueOf(scheduleIndex), schedule);
                    scheduleIndex++;
                } while (scheduleRs.next());
                properties.put("schedule", scheduleJson);
            }

            result.put("properties", properties);

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accessing database.");
        }

        if (result.getJSONObject("properties").isEmpty()) {
            // Không có dữ liệu trong properties
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found for cluster.");
        }

        return ResponseEntity.ok(result.toString());
    }



//// <-----------Cập nhật kịch bản cua cum đèn cái nào active thì cập nhật lại current_script
//
//
//public void updateCurrentScriptForCluster(String clusterId) {
//    try {
//        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
//
//        // Truy vấn để lấy kịch bản "active"
//        String selectQuery = "SELECT red, green, yellow FROM scripts WHERE cluster_id = ? AND scripts_status = 'active'";
//        PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
//        selectStmt.setString(1, clusterId);
//        ResultSet rs = selectStmt.executeQuery();
//
//        if (rs.next()) {
//            String red = rs.getString("red");
//            String green = rs.getString("green");
//            String yellow = rs.getString("yellow");
//
//            // Cập nhật current_script trong bảng clusters
//            String updateQuery = "UPDATE clusters SET current_script = ? WHERE cluster_id = ?";
//            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
//
//            // Giả sử cột current_script lưu trữ dưới dạng chuỗi kết hợp của red, green, yellow
//            String currentScript = "r" + red + ",g" + green + ",y" + yellow;
//
//            updateStmt.setString(1, currentScript);
//            updateStmt.setString(2, clusterId);
//
//            int rowsUpdated = updateStmt.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Successfully updated current_script for cluster_id: " + clusterId);
//            }
//        } else {
//            System.out.println("No active script found for cluster_id: " + clusterId);
//        }
//
//        connection.close();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}

    //<------------Xóa kịch bản------------>
    public boolean deleteScript(String clusterId, String name) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String deleteQuery = "DELETE FROM scripts WHERE cluster_id = ? AND name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, clusterId);
            preparedStatement.setString(2, name);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // <------------Thêm kịch bản------------>
    public void addScript(String clusterId, String name, String red, String green, String yellow, String status) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Truy vấn để thêm kịch bản mới
            String insertQuery = "INSERT INTO scripts (name, cluster_id, red, green, yellow, scripts_status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, name);
            insertStmt.setString(2, clusterId);
            insertStmt.setString(3, red);
            insertStmt.setString(4, green);
            insertStmt.setString(5, yellow);
            insertStmt.setString(6, status);

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Successfully inserted script for cluster_id: " + clusterId);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //<--------------Chinh sửa kịch bản------------>
    public void updateScript(String clusterId, String name, String red, String green, String yellow, String status) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Truy vấn để cập nhật kịch bản
            String updateQuery = "UPDATE scripts SET red = ?, green = ?, yellow = ?, scripts_status = ? WHERE cluster_id = ? AND name = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);

            updateStmt.setString(1, red);
            updateStmt.setString(2, green);
            updateStmt.setString(3, yellow);
            updateStmt.setString(4, status);
            updateStmt.setString(5, clusterId);  // Cluster ID tại vị trí thứ 5
            updateStmt.setString(6, name);       // Tên tại vị trí thứ 6

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Successfully updated script for cluster_id: " + clusterId);
            } else {
                System.out.println("No script found with the provided cluster_id and name.");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //<---------------------Update trạng thái của kịch bản khớp thành active những cái khác inactive và cập nhật current_script------------------->
    public void updateScriptAndCurrentScript(String clusterId, String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Cập nhật trạng thái của kịch bản
            String updateActiveQuery = "UPDATE scripts SET scripts_status = 'active' WHERE cluster_id = ? AND name = ?";
            try (PreparedStatement updateActiveStmt = connection.prepareStatement(updateActiveQuery)) {
                updateActiveStmt.setString(1, clusterId);
                updateActiveStmt.setString(2, name);
                updateActiveStmt.executeUpdate();
            }

            String updateInactiveQuery = "UPDATE scripts SET scripts_status = 'inactive' WHERE cluster_id = ? AND name <> ?";
            try (PreparedStatement updateInactiveStmt = connection.prepareStatement(updateInactiveQuery)) {
                updateInactiveStmt.setString(1, clusterId);
                updateInactiveStmt.setString(2, name);
                updateInactiveStmt.executeUpdate();
            }

            // Cập nhật current_script trong bảng clusters cho clusterId
            String selectQuery = "SELECT red, green, yellow FROM scripts WHERE cluster_id = ? AND scripts_status = 'active'";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, clusterId);
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    String redValue = rs.getString("red");
                    String greenValue = rs.getString("green");
                    String yellowValue = rs.getString("yellow");

                    String currentScript =    greenValue+"," + yellowValue+"," + redValue;

                    String updateQuery = "UPDATE clusters SET current_script = ? WHERE cluster_id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, currentScript);
                        updateStmt.setString(2, clusterId);
                        updateStmt.executeUpdate();
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //<---------------------Nhận kịch bản thủ công, cập nhật lên DB và gửi mqtt cho S3------------------->
    public ResponseEntity<String> updateManualAndSendMqtt(String clusterId, String manual) {
        if ("1,1,1".equals(manual) || "1,0,0".equals(manual) || "0,1,0".equals(manual)) {
            try {
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);

                // Update the 'manual' value in the database
                String updateQuery = "UPDATE clusters SET manual = ? WHERE cluster_id = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setString(1, manual);
                updateStmt.setString(2, clusterId);

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    // Successfully updated
                    System.out.println("Successfully updated manual for cluster_id: " + clusterId);
                    mqtt.connectToMqtt();
                    mqtt.sendMessage("server/script", manual, 0);

                    connection.close();
                    // Return success response
                    return ResponseEntity.status(HttpStatus.OK).body("Successfully updated manual for cluster_id: " + clusterId);
                } else {
                    connection.close();
                    // No rows updated, cluster ID not found
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cluster ID not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // Database error, return 500 Internal Server Error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating manual: " + e.getMessage());
            }
        } else {
            // Invalid 'manual' value, return 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid manual value. Accepted values are '1,1,1', '1,0,0', or '0,1,0'.");
        }
    }



    // <-------------------DATA--------------------->

    public void insertClusters() {
        String sql = "INSERT INTO clusters (cluster_id, street_name1, street_name2, latitude, longitude, current_script, manual, cluster_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Dữ liệu mẫu 1
            preparedStatement.setString(1, "cluster_001");
            preparedStatement.setString(2, "Nguyen Van Troi");
            preparedStatement.setString(3, "Le Loi");
            preparedStatement.setString(4, "10.762622");
            preparedStatement.setString(5, "106.660172");
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "active");
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 2
            preparedStatement.setString(1, "cluster_002");
            preparedStatement.setString(2, "Le Duan");
            preparedStatement.setString(3, "Hai Ba Trung");
            preparedStatement.setString(4, "10.780523");
            preparedStatement.setString(5, "106.700981");
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "active");
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 3
            preparedStatement.setString(1, "cluster_003");
            preparedStatement.setString(2, "Tran Hung Dao");
            preparedStatement.setString(3, "Nguyen Thai Hoc");
            preparedStatement.setString(4, "10.771365");
            preparedStatement.setString(5, "106.704267");
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "active");
            preparedStatement.executeUpdate();

// Dữ liệu mẫu 4
            preparedStatement.setString(1, "cluster_004");
            preparedStatement.setString(2, "Vo Van Tan");
            preparedStatement.setString(3, "Cach Mang Thang 8");
            preparedStatement.setString(4, "10.774485");
            preparedStatement.setString(5, "106.692940");
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "active");
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertScripts() {
        String sql = "INSERT INTO scripts (name, cluster_id, red, green, yellow, scripts_status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

//            // Dữ liệu mẫu 1
            preparedStatement.setString(1, "Default");
            preparedStatement.setString(2, "cluster_001");
            preparedStatement.setString(3, "20");
            preparedStatement.setString(4, "16");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

         //    Dữ liệu mẫu 2
            preparedStatement.setString(1, "Saturday");
            preparedStatement.setString(2, "cluster_001");
            preparedStatement.setString(3, "30");
            preparedStatement.setString(4, "20");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

         //    Dữ liệu mẫu 3
            preparedStatement.setString(1, "Sunday");
            preparedStatement.setString(2, "cluster_001");
            preparedStatement.setString(3, "22");
            preparedStatement.setString(4, "18");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

           //  Dữ liệu mẫu 4
            preparedStatement.setString(1, "Holiday");
            preparedStatement.setString(2, "cluster_002");
            preparedStatement.setString(3, "25");
            preparedStatement.setString(4, "20");
            preparedStatement.setString(5, "5");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();


            // Dữ liệu mẫu 5
            preparedStatement.setString(1, "Saturday");
            preparedStatement.setString(2, "cluster_002");
            preparedStatement.setString(3, "30");
            preparedStatement.setString(4, "20");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 6
            preparedStatement.setString(1, "Sunday");
            preparedStatement.setString(2, "cluster_002");
            preparedStatement.setString(3, "22");
            preparedStatement.setString(4, "18");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Default");
            preparedStatement.setString(2, "cluster_002");
            preparedStatement.setString(3, "20");
            preparedStatement.setString(4, "16");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 4
            preparedStatement.setString(1, "Night");
            preparedStatement.setString(2, "cluster_003");
            preparedStatement.setString(3, "15");
            preparedStatement.setString(4, "20");
            preparedStatement.setString(5, "5");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Default");
            preparedStatement.setString(2, "cluster_003");
            preparedStatement.setString(3, "20");
            preparedStatement.setString(4, "16");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();


// Dữ liệu mẫu 5
            preparedStatement.setString(1, "Weekday");
            preparedStatement.setString(2, "cluster_004");
            preparedStatement.setString(3, "25");
            preparedStatement.setString(4, "25");
            preparedStatement.setString(5, "5");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();

// Dữ liệu mẫu 6
            preparedStatement.setString(1, "Weekend");
            preparedStatement.setString(2, "cluster_004");
            preparedStatement.setString(3, "30");
            preparedStatement.setString(4, "20");
            preparedStatement.setString(5, "10");
            preparedStatement.setString(6, "active");
            preparedStatement.executeUpdate();


            preparedStatement.setString(1, "Default");
            preparedStatement.setString(2, "cluster_004");
            preparedStatement.setString(3, "20");
            preparedStatement.setString(4, "16");
            preparedStatement.setString(5, "3");
            preparedStatement.setString(6, "inactive");
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertHistory() {
        String sql = "INSERT INTO history (searchword, keyword, latitude, longitude, time) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Dữ liệu mẫu 1
            preparedStatement.setString(1, "traffic light");
            preparedStatement.setString(2, "Nguyen Van Troi");
            preparedStatement.setString(3, "10.762622");
            preparedStatement.setString(4, "106.660172");
            preparedStatement.setTimestamp(5, Timestamp.valueOf("2024-09-01 10:00:00"));
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 2
            preparedStatement.setString(1, "accident");
            preparedStatement.setString(2, "Le Duan");
            preparedStatement.setString(3, "10.780523");
            preparedStatement.setString(4, "106.700981");
            preparedStatement.setTimestamp(5, Timestamp.valueOf("2024-09-01 12:30:00"));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertSchedule() {
        String sql = "INSERT INTO schedule (name, date, \"start\", \"end\") " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            // Dữ liệu mẫu 1

            preparedStatement.setString(1, "Daily");
            preparedStatement.setString(2, ("2024-09-04"));
            preparedStatement.setString(3, "10:25");
            preparedStatement.setString(4, ("12:00"));
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 2

            preparedStatement.setString(1, "Sunday");
            preparedStatement.setString(2,("2024-09-01"));
            preparedStatement.setString(3, ("12:00"));
            preparedStatement.setString(4, ("14:00"));
            preparedStatement.executeUpdate();

            // Dữ liệu mẫu 3

            preparedStatement.setString(1, "Holiday");
            preparedStatement.setString(2, ("2024-09-02"));
            preparedStatement.setString(3,("09:00"));
            preparedStatement.setString(4, ("11:00"));
            preparedStatement.executeUpdate();

            // Dữ liệu 4
            preparedStatement.setString(1, "Saturday");
            preparedStatement.setString(2, ("2024-09-03"));
            preparedStatement.setString(3,("03:00"));
            preparedStatement.setString(4, ("06:00"));
            preparedStatement.executeUpdate();
// Dữ liệu mẫu 4
            preparedStatement.setString(1, "Night");
            preparedStatement.setString(2, "2024-09-04");
            preparedStatement.setString(3, "09:46");
            preparedStatement.setString(4, "12:00");
            preparedStatement.executeUpdate();

// Dữ liệu mẫu 5
            preparedStatement.setString(1, "Weekday");
            preparedStatement.setString(2, "2024-09-03");
            preparedStatement.setString(3, "07:00");
            preparedStatement.setString(4, "19:00");
            preparedStatement.executeUpdate();

// Dữ liệu mẫu 6
            preparedStatement.setString(1, "Weekend");
            preparedStatement.setString(2, "2024-09-03");
            preparedStatement.setString(3, "09:00");
            preparedStatement.setString(4, "21:00");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Default");
            preparedStatement.setString(2, ("2024-09-04"));
            preparedStatement.setString(3, "10:31");
            preparedStatement.setString(4, ("23:00"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}