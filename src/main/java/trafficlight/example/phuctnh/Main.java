package trafficlight.example.phuctnh;
import object.Cluster;
import object.Schedule;
import object.Script;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootApplication
public class Main extends SpringBootServletInitializer{
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
        SQL_functions sqlFunctions = new SQL_functions();
//          sqlFunctions.Drop("schedule");
//        sqlFunctions.Drop("scripts");
//         sqlFunctions.Drop("clusters");
//        sqlFunctions.Drop("History");
//       sqlFunctions.Create_Clusters();
//        sqlFunctions.Create_Scripts();
//        sqlFunctions.Create_Schedule_Table();
//        sqlFunctions.Create_History_Table();
//        sqlFunctions.insertClusters();
//        sqlFunctions.insertScripts();
//        sqlFunctions.insertSchedule();
   //     sqlFunctions.insertHistory();
       // sqlFunctions.updateScriptAndCurrentScript("cluster_002", "Sunday");
       // sqlFunctions.updateScript("cluster_004", "Weekend", "20", "16","3", "inactive");
ArrayList<Cluster>list1 = sqlFunctions.Show_Clusters();
        System.out.println("_______CLUSTER_________");
for(Cluster cluster : list1)
{

    System.out.println(cluster.getCluster_id());
    System.out.println(cluster.getCurrent_script());
    System.out.println(cluster.getCluster_status());
    System.out.println();
}
        System.out.println("_______SCRIPT_________");
        ArrayList<Script> list = sqlFunctions.Show_Scripts();
        for(Script script : list)
        {
            System.out.println(script.getScripts_status());
            System.out.println(script.getCluster_id());
            System.out.println(script.getName());
            System.out.println(script.getRed());
            System.out.println(script.getGreen());
            System.out.println(script.getYellow());
            System.out.println();
        }
//        sqlFunctions.updateCurrentScriptForCluster("cluster_001");
//        sqlFunctions.updateCurrentScriptForCluster("cluster_002");
//        sqlFunctions.updateCurrentScriptForCluster("cluster_003");
//        sqlFunctions.updateCurrentScriptForCluster("cluster_004");
ArrayList<Schedule>list2 = sqlFunctions.Show_Schedule();
        System.out.println("_______SCHEDULE_________");
for(Schedule schedule : list2) {
    System.out.println(schedule.getSchedule_id());
    System.out.println(schedule.getName());
    System.out.println(schedule.getDate());
    System.out.println(schedule.getStart());
    System.out.println(schedule.getEnd());
    System.out.println();
}
//}
//        ArrayList<Cluster_info> list = sqlFunctions.getClusterInfo("C1");
//        for (Cluster_info clusterInfo : list) {
//            System.out.println("Cluster ID: " + clusterInfo.getId_cluster());
//            System.out.println("Status Cluster: " + clusterInfo.getStatus_cluster());
//            System.out.println("Name: " + clusterInfo.getName());
//            System.out.println("Red: " + clusterInfo.getRed());
//            System.out.println("Green: " + clusterInfo.getGreen());
//            System.out.println("Yellow: " + clusterInfo.getYellow());
//            System.out.println("Script Status: " + clusterInfo.getStatus_script());
//            System.out.println("Manual Script: " + clusterInfo.getManual_script());
//            System.out.println("Light ID: " + clusterInfo.getLight_id());
//            System.out.println("Date: " + clusterInfo.getDate());
//            System.out.println("Start: " + clusterInfo.getStart());
//            System.out.println("End: " + clusterInfo.getEnd());
//            System.out.println(); // Print a blank line between objects for readability
//        }


//        sqlFunctions.Create_Cluster_Table();
//        sqlFunctions.Create_Lights_Table();
//        sqlFunctions.Create_Scripts_Table();
//        sqlFunctions.Create_Schedule();
//        sqlFunctions.Create_History_Table();
//        sqlFunctions.Insert_Cluster_Data();
//        sqlFunctions.Insert_Scripts_Data();
//        sqlFunctions.Insert_Lights_Data();
//        sqlFunctions.Insert_Schedule_Data();
//        sqlFunctions.Insert_History_Data();
    }
}