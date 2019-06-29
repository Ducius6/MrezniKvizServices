package hr.fer.tel.project.mreznikviz.MrezniKvizService;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/users")
    public List<User> findUsers(@ModelAttribute(value="from") int from, @ModelAttribute(value="size") int size) {
        Page<User> pages = userService.findAll(from,size);
        List<User> list = pages.getContent();
        return list;
    }

    @PatchMapping(path = "/users")
    public ResponseEntity<Object> updateUserScore(@ModelAttribute(value = "score") Long score,  @ModelAttribute (value = "userName") String userName){
        int promjena = userService.updateScore(score, userName);
        return new ResponseEntity<Object>(HttpStatus.OK);

    }


    @PostMapping(path = "/users")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {

        if (userService.checkIfUsernameIsTaken(user)) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.NOT_FOUND);
            body.put("errors", "Username already exists");
            return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
        }
        userService.createNewUser(user);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping(path = "/users/login")
    public User loginUser(@RequestParam String userName, @RequestParam String password) {
        User user = userService.loginInUser(userName, password);
        System.out.println(user.getUserName());
        return user;
    }

    @GetMapping(path = "/users/sendpushmessage")
    public ResponseEntity<Object> sendPushMessage(@RequestParam(name="token") String token, @RequestParam(name="quizId") String quizId) {
        System.out.println(token.toString());
        if (token.isEmpty()) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.NOT_FOUND);
            body.put("errors", "No token");
            return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
        }

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

        }
        catch (Exception exc) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.NOT_FOUND);
            body.put("errors", exc.getMessage());
            return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
        }

        // This registration token comes from the client FCM SDKs
        com.google.firebase.messaging.Message message = Message.builder()
                .putData("title", "New Quiz Request")
                .putData("message", "Come and play")
                .putData("quizId", quizId)
                .setNotification(new Notification("New Quiz Request", "Come and play"))
                .setToken(token)
                .build();
        System.out.println("napravia poruku");

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        }
        catch (FirebaseMessagingException fme) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.NOT_FOUND);
            body.put("errors", fme.getMessage());
            return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
