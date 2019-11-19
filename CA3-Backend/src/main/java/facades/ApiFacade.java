package facades;


import DTO.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ApiFacade {
    
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ApiFacade instance;

    private ApiFacade() {
    }

    public static ApiFacade getApiFacade() {
        if (instance == null) {

            instance = new ApiFacade();
        }
        return instance;
    }
    
    
    public String getApiData(int id) throws MalformedURLException, IOException{
        URL url = new URL("https://swapi.co/api/people/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
          jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
      }

    public List<PersonDTO> getAll() throws InterruptedException, ExecutionException{ // maps the Json data into a list of DTO's corresponding to the properties of the JSON retrieved from Swapi
        List<PersonDTO> persons = new ArrayList<>();
        
        Queue<Future<PersonDTO>> queue = new ArrayBlockingQueue(10);
       
       // List<Future<String>> futures = new ArrayList();
        
        for (int i = 1; i <= 10; i++) {
            final int count = i;
            Future<PersonDTO> future = executor.submit(() -> {
                
                PersonDTO person = GSON.fromJson(getApiData(count), PersonDTO.class);
                return person;
            });

            queue.add(future);
        }
            
        
        while (!queue.isEmpty()) {
            Future<PersonDTO> person = queue.poll();
            if (person.isDone()) {
                persons.add(person.get());
            } else {
                queue.add(person);
            }
        }

        return persons;
    
}
   public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        ApiFacade af = ApiFacade.getApiFacade();
        
        List<PersonDTO> persons = af.getAll();
        
        for (PersonDTO person : persons) {
            System.out.println(person);
        }
        
    }
 
}
