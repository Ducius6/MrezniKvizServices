package hr.fer.tel.project.mreznikviz.MrezniKvizService;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    public int updateScore(Long score, String userName);
    public void createNewUser(User user);
    public Page<User> findAll(int from, int size);
    boolean checkIfUsernameIsTaken(User user);
    public User loginInUser(String userName, String password);
}