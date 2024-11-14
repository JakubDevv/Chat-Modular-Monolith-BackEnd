package exceptions.friend;

public class FriendNotFound extends RuntimeException {

    public FriendNotFound(Long id) {
        super(String.format(
                "User with id = %d not found in database",
                id
        ));
    }

}
