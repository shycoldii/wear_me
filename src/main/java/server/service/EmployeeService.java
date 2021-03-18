package server.service;

import server.model.Employee;
import server.repository.EmployeeRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EmployeeService {
    private Employee employee;

    public EmployeeService(){
          this.employee = new Employee();
      }

       public static boolean is_login(Long id) throws IOException {
           StringBuilder result = new StringBuilder();
           URL url = new URL("http://localhost:8080/shop/login/"+id);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
           try (var reader = new BufferedReader(
                   new InputStreamReader(conn.getInputStream()))) {
               for (String line; (line = reader.readLine()) != null; ) {
                   result.append(line);
               }
           }
           if(result.isEmpty()){
               
           }
           return !result.isEmpty();
       }
       
       public static boolean is_password(String password){
           System.out.println("dfghj");
           return true;
       }
}
