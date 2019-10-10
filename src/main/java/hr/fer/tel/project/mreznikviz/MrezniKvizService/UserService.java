package hr.fer.tel.project.mreznikviz.MrezniKvizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public int updateScore(Long score, String userName) {
        return repository.updateScore(score, userName);
    }

    @Override
    public void createNewUser(User user) {
        repository.save(user);
    }

    @Override
    public Page<User> findAll(int from, int size) {

        return (Page<User>) repository.findAll(PageRequest.of(from, size, Sort.by("score").descending()));
    }

    @Override
    public User loginInUser(String userName, String password) {
        User user = repository.loginUser(userName, password);
        return user;
    }

    @Override
    public boolean checkIfUsernameIsTaken(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }

        if (user.getUserName() == null || user.getUserName().trim().equals("")) {
            throw new RuntimeException("User cannot be null or empty");
        }

        if (repository.findByUserName(user.getUserName()) != null) {
            return true;
        }

        return false;
    }


}
