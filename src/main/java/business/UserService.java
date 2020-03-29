package business;

import repository.IUserRepository;

public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateUser(String username, String password){
        return userRepository.findOne(username,password);
    }

}
