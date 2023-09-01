package catering.businesslogic.user;

import catering.businesslogic.UseCaseLogicException;

public class UserManager {
    private User currentUser;

    public void fakeLogin(String username) //TODO: bisogna implementare il login vero!
    {
        this.currentUser = User.loadUser(username);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    private User getUser(String username){
        return User.loadUser(username);
    }
    
    public User getCook(String username) throws UseCaseLogicException{
        User user = getUser(username);
        if(!user.isCook()){
            throw new UseCaseLogicException();
        }
        return user;
    }

    public User getServiceStaff(String username) throws UseCaseLogicException{
        User user = getUser(username);
        if(user.isOrganizer() || user.isChef()){
            throw new UseCaseLogicException();
        }
        return user;
    }
}
