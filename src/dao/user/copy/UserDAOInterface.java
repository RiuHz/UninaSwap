package dao.user.copy;

import entity.UserEntity;
import exception.user.InvalidUserException;

public interface UserDAOInterface {


	public boolean checkCredentials(UserEntity user) throws InvalidUserException;
    public boolean insertUser(UserEntity user) throws InvalidUserException ;
}
