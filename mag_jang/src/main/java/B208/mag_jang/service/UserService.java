package B208.mag_jang.service;

import B208.mag_jang.domain.User;
import B208.mag_jang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

//    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(User user){
        validateDuplicateUser(user);

        userRepository.save(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByNickName(user.getNickName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });
    }
}
