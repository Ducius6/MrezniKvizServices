package hr.fer.tel.project.mreznikviz.MrezniKvizService;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class MrezniKvizServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrezniKvizServiceApplication.class, args);
        try {
            // TODO change this to point to place where service account data is stored
            FileInputStream serviceAccount =
                    new FileInputStream("C:/Users/dujed/AndroidStudioProjects/Mreznikviz/mrezni-kviz-firebase-adminsdk-ykkx5-de6fe21a1a.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://mrezni-kviz.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("napravia stream");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
