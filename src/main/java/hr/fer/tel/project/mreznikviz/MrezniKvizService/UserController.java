package hr.fer.tel.project.mreznikviz.MrezniKvizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
