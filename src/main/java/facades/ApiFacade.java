package facades;


import DTO.PersonDTO;
import DTO.SwapiDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
    
    
    public String getApiData(String apiUrl) throws MalformedURLException, IOException{
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
        String jsonStr;
        try (Scanner scan = new Scanner(con.getInputStream())) {
            jsonStr = null;
            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
        }
        return jsonStr;
      }
    
    
        public List<SwapiDTO> getDataInParallelWithQueueAndDTO() throws ProtocolException, IOException, InterruptedException, ExecutionException {
        List<SwapiDTO> results = new ArrayList();
        List<String> URLS = new ArrayList();
        URLS.add("https://swapi.co/api/films/1/");
        URLS.add("https://swapi.co/api/people/2/");
        URLS.add("https://swapi.co/api/planets/3/");
        URLS.add("https://swapi.co/api/starships/4/");
        URLS.add("https://swapi.co/api/vehicles/5/");
        

        Queue<Future<JsonObject>> queue = new ArrayBlockingQueue(URLS.size());

        ExecutorService executor = Executors.newCachedThreadPool();
        for (String url : URLS) {
            Future<JsonObject> future;
            future = executor.submit(() -> {
                JsonObject jsonObject = new JsonParser().parse(getApiData(url)).getAsJsonObject();
                return jsonObject;
            });
            queue.add(future);
        }
        while (!queue.isEmpty()) {
            Future<JsonObject> cpo = queue.poll();
            if (cpo.isDone()) {
                try {
                    results.add(new SwapiDTO(
                            cpo.get().get("name").getAsString(),
                            cpo.get().get("url").getAsString()
                    ));

                } catch (InterruptedException interruptedException) {
                    System.out.println("interruptedException: " + interruptedException);
                } catch (ExecutionException executionException) {
                    System.out.println("executionException: " + executionException);
                } catch (NullPointerException nullPointerException) {
                    System.out.println("NullPointerException: " + nullPointerException);
                }
            } else {
                queue.add(cpo);
            }
        }
        executor.shutdown();
        return results;
    }
    
    public List<String> getDataInParallelWithQueue() throws ProtocolException, IOException, InterruptedException, ExecutionException {
        List<String> results = new ArrayList();
        List<String> URLS = new ArrayList();
        URLS.add("https://swapi.co/api/films/1/");
        URLS.add("https://swapi.co/api/people/2/");
        URLS.add("https://swapi.co/api/planets/3/");
        URLS.add("https://swapi.co/api/starships/4/");
        URLS.add("https://swapi.co/api/vehicles/5/");

        Queue<Future<JsonObject>> queue = new ArrayBlockingQueue(URLS.size());

        ExecutorService executor = Executors.newCachedThreadPool();
        for (String url : URLS) {
            Future<JsonObject> future;
            future = executor.submit(() -> {
                JsonObject jsonObject = new JsonParser().parse(getApiData(url)).getAsJsonObject();
                return jsonObject;
            });
            queue.add(future);
        }
        while (!queue.isEmpty()) {
            Future<JsonObject> cpo = queue.poll();
            if (cpo.isDone()) {
                try {
                    JsonObject json = new JsonObject();
                    json.addProperty("name", cpo.get().get("name").getAsString());
                    json.addProperty("url", cpo.get().get("url").getAsString());
                    results.add(json.toString());
                } catch (InterruptedException interruptedException) {
                    System.out.println("interruptedException: " + interruptedException);
                } catch (ExecutionException executionException) {
                    System.out.println("executionException: " + executionException);
                } catch (NullPointerException nullPointerException) {
                    System.out.println("NullPointerException: " + nullPointerException);
                }
            } else {
                queue.add(cpo);
            }
        }
        executor.shutdown();
        return results;
    }
    
    
    
    
    /*
    public List<PersonDTO> getAll() throws InterruptedException, ExecutionException{ // maps the Json data into a list of DTO's corresponding to the properties of the JSON retrieved from Swapi
        final List<PersonDTO> persons = new ArrayList<>();
        
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
*/
    
   public static void main(String[] args) throws InterruptedException, ExecutionException {
        
      
        
    }
 
}
