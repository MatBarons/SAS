package catering.businesslogic.kitchenTask;

import java.util.ArrayList;
import java.util.Optional;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.shift.ShiftManager;
import catering.businesslogic.user.User;

public class KitchenTaskManager{
    private SummarySheet currentSheet;
    private ArrayList<KitchenTaskEventReceiver> eventReceivers;
    public KitchenTaskManager(){
        this.eventReceivers = new ArrayList<>();
    }

    public SummarySheet getCurrentSheet() throws UseCaseLogicException{
        if(this.currentSheet == null){
            throw new UseCaseLogicException();
        }
        return currentSheet;
    }

    public void setCurrentSummarySheet(SummarySheet sheet){
        this.currentSheet = sheet;
    }


    public SummarySheet generateSummarySheet(Event event,Service service){

        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef() || !event.getAssignedChef().equals(user) || !event.containsService(service) || !service.isConfirmed()) {
            throw new UseCaseLogicException();
        }

        ArrayList<Recipe> recipes = service.getMenu().getNeededRecipes();
        SummarySheet sheet = new SummarySheet();
        for(Recipe recipe : recipes){
            KitchenTask t = new KitchenTask(recipe);
            sheet.addTask(t);
        }
        service.setSheet(sheet);
        setCurrentSummarySheet(sheet);
        notifySheetGenerated(sheet);
        return currentSheet;
    }


    public void insertTask(Recipe rec){
        KitchenTask task = new KitchenTask(rec);
        currentSheet.addTask(task);
        notifyInsertedTask(currentSheet,task);
    }

    public void changeTaskPosition(int position,KitchenTask task){
        if(position >= currentSheet.getTasks().size() || position < 0){
            throw new IllegalArgumentException();
        }
        currentSheet.moveTask(position,task);
        notifyRearrangedSheet(currentSheet);
    }

    public ArrayList<KitchenShift> getShiftBoard(int serviceID){
        return ShiftManager.getShiftBoard(serviceID);
    }

    public void assignTask(KitchenTask task,Optional<KitchenShift> shift,Optional<User> cook,Optional<Integer> time,Optional<String> quantity) throws UseCaseLogicException{
        if(!currentSheet.getTasks().contains(task)){
            throw new UseCaseLogicException();
        }
        currentSheet.assignTask(task,shift,cook,time,quantity);
        notifyAssignedTask(currentSheet, task);
    }

    public void editTask(KitchenTask task,Optional<Integer> time,Optional<String> quantity) throws UseCaseLogicException{
        if(!currentSheet.getTasks().contains(task)){
            throw new UseCaseLogicException();
        }
        currentSheet.editTask(task, time, quantity);
        notifyEditedTask(currentSheet, task);
    }


    public void cancelTask(KitchenTask task) throws UseCaseLogicException{
        if(!currentSheet.getTasks().contains(task)){
            throw new UseCaseLogicException();
        }
        currentSheet.cancelTask(task);
        notifyCancelTask(currentSheet, task);
    }

    public void removeTask(KitchenTask task) throws UseCaseLogicException{
        if(!currentSheet.getTasks().contains(task)){
            throw new UseCaseLogicException();
        }
        currentSheet.removeTask(task);
        notifyDeletedTask(currentSheet, task);
    }



    //NOTIFIERS
    private void notifySheetGenerated(SummarySheet sheet) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateSheetGenerated(sheet);
        }
    }

    private void notifyInsertedTask(SummarySheet sheet,KitchenTask task) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateInsertedTask(sheet,task);
        }
    }

    private void notifyRearrangedSheet(SummarySheet sheet){
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateRearrangedSheet(sheet);
        }
    }

    private void notifyAssignedTask(SummarySheet sheet,KitchenTask task){
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignedTask(sheet,task);
        }
    }
    private void notifyEditedTask(SummarySheet sheet,KitchenTask task){
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateEditedTask(sheet,task);
        }
    }

    private void notifyDeletedTask(SummarySheet sheet,KitchenTask task){
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateDeletedTask(sheet,task);
        }
    }

    private void notifyCancelTask(SummarySheet sheet,KitchenTask task){
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateCancelTask(sheet,task);
        }
    }
}