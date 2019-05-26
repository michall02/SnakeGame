package pl.sda.snake.repository;

import pl.sda.snake.model.User;

public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(User.class);
    }
}
