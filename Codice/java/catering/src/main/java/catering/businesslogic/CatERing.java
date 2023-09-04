package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.kitchenTask.KitchenTaskManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.procedure.ProcedureManager;
import catering.businesslogic.shift.ShiftManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.KitchenTaskPersistence;
import catering.persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private ProcedureManager procedureMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private ShiftManager shiftMgr;
    private KitchenTaskManager ktMgr;

    private MenuPersistence menuPersistence;
    private KitchenTaskPersistence ktPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        procedureMgr = new ProcedureManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        shiftMgr = new ShiftManager();
        ktMgr = new KitchenTaskManager();
        
        menuPersistence = new MenuPersistence();
        ktPersistence = new KitchenTaskPersistence();

        menuMgr.addEventReceiver(menuPersistence);
        ktMgr.addReceiver(ktPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public ProcedureManager getProcedureManager() {
        return procedureMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }
    
    public ShiftManager getShiftManager(){
        return shiftMgr;
    }

    public KitchenTaskManager getKitchenTaskManager(){
        return ktMgr;
    }

    public EventManager getEventManager() { 
        return eventMgr; 
    }

}
