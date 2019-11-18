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

public class SwappiFacade {
    
    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static SwappiFacade instance;

    private SwappiFacade() {
    }

    public static SwappiFacade getSwappiFacade() {
        if (instance == null) {

            instance = new SwappiFacade();
        }
        return instance;
    }
    
    
    public String getSwappiData() throws MalformedURLException, IOException{
        URL url = new URL("https://anapioficeandfire.com/api/characters/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        //con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
          jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
      }

    public List<PersonDTO> getAll() throws InterruptedException, ExecutionException{
        List<PersonDTO> persons = new ArrayList<>();
        
        Queue<Future<PersonDTO>> queue = new ArrayBlockingQueue(5);
       
       // List<Future<String>> futures = new ArrayList();
        
        for (int i = 1; i <= 5; i++) {
            Future<PersonDTO> future = executor.submit(() -> {
                
                PersonDTO quote = GSON.fromJson(getSwappiData(), PersonDTO.class);
                return quote;
            });

            queue.add(future);
        }
            
        
        while (!queue.isEmpty()) {
            Future<PersonDTO> qoute = queue.poll();
            if (qoute.isDone()) {
                persons.add(qoute.get());
            } else {
                queue.add(qoute);
            }
        }

        return persons;
    
}
   public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        SwappiFacade df = SwappiFacade.getSwappiFacade();
        
        List<PersonDTO> persons = df.getAll();
        
        for (PersonDTO person : persons) {
            System.out.println(person);
        }
        
    }
 
}
