package catering.businesslogic.kitchenTask;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.procedure.Recipe;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.Optional;

public class KitchenTaskManager {
    private SummarySheet currentSheet;
    private ArrayList<KitchenTaskEventReceiver> eventReceivers;

    public KitchenTaskManager() {
        this.eventReceivers = new ArrayList<>();
    }

    public SummarySheet getCurrentSheet() throws UseCaseLogicException {
        if (this.currentSheet == null) {
            throw new UseCaseLogicException("Non esiste un foglio riepilogativo");
        }
        return currentSheet;
    }

    public void setCurrentSummarySheet(SummarySheet sheet) {
        this.currentSheet = sheet;
    }


    public SummarySheet generateSummarySheet(Event event, Service service) throws UseCaseLogicException {

        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef())
            throw new UseCaseLogicException("L'utente autenticato non è uno CHEF");
        if (!event.getChef().equals(user))
            throw new UseCaseLogicException("L'utente autenticato non è lo CHEF dell'evento");
        if (!event.getServices().contains(service))
            throw new UseCaseLogicException("L'evento non contiene il servizio");
        if (!service.isConfirmed())
            throw new UseCaseLogicException("Il servizio non è CONFERMATO");

        ArrayList<Recipe> recipes = service.getMenu().getNeededRecipes();
        SummarySheet sheet = new SummarySheet();
        for (Recipe recipe : recipes) {
            KitchenTask t = new KitchenTask(recipe);
            sheet.addTask(t);
        }
        service.setSheet(sheet);
        setCurrentSummarySheet(sheet);
        notifySheetGenerated(event, service);
        return currentSheet;
    }


    public KitchenTask insertTask(Procedure proc) throws UseCaseLogicException {
        if (currentSheet == null)
            throw new UseCaseLogicException();

        KitchenTask task = new KitchenTask(proc);

        if (currentSheet.getTasks().contains(task))
            throw new UseCaseLogicException("Procedura già presente");

        currentSheet.addTask(task);
        notifyInsertedTask(currentSheet, task);
        return task;
    }

    public void changeTaskPosition(int position, KitchenTask task) {
        if (position >= currentSheet.getTasks().size() || position < 0) {
            throw new IllegalArgumentException();
        }
        currentSheet.changeTaskPosition(position, task);
        notifyRearrangedSheet(currentSheet);
    }

    public ArrayList<KitchenShift> getKitchenShiftBoard(int serviceID) {
        return CatERing.getInstance().getShiftManager().getKitchenShiftBoard(serviceID);
    }

    public void assignTask(KitchenTask task, Optional<KitchenShift> shift, Optional<User> cook, Optional<Integer> time, Optional<String> quantity) throws UseCaseLogicException {
        if (task.getProcedure() == null)
            throw new UseCaseLogicException("Procedura mancante");

        if (currentSheet == null || !currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException();
        }

        if (cook.isPresent() && shift.isPresent() && !cook.get().isAvailable(shift.get())) {
            throw new UseCaseLogicException("Cuoco non disponibile");
        }
        currentSheet.assignTask(task, shift, cook, time, quantity);
        notifyAssignedTask(currentSheet, task);
    }

    public void editTask(KitchenTask task, Optional<Integer> time, Optional<String> quantity, Optional<Boolean> completed) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.getTasks().contains(task)) {
            throw new UseCaseLogicException();
        }
        currentSheet.editTask(task, time, quantity, completed);
        notifyEditedTask(currentSheet, task);
    }


    public void cancelTask(KitchenTask task) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.getTasks().contains(task) || task.isCompleted() || !task.isToDo()) {
            throw new UseCaseLogicException();
        }
        currentSheet.cancelTask(task);
        notifyCanceledTask(currentSheet, task);
    }

    public void deleteTask(KitchenTask task) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.getTasks().contains(task) || task.isCompleted()) {
            throw new UseCaseLogicException();
        }
        currentSheet.deleteTask(task);
        notifyDeletedTask(currentSheet, task);
    }


    //NOTIFIERS

    public void addReceiver(KitchenTaskEventReceiver e) {
        eventReceivers.add(e);
    }

    public void removeReceiver(KitchenTaskEventReceiver e) {
        eventReceivers.remove(e);
    }


    private void notifySheetGenerated(Event event, Service service) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateSheetGenerated(event, service);
        }
    }

    private void notifyInsertedTask(SummarySheet sheet, KitchenTask task) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateInsertedTask(sheet, task);
        }
    }

    private void notifyRearrangedSheet(SummarySheet sheet) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateRearrangedSheet(sheet);
        }
    }

    private void notifyAssignedTask(SummarySheet sheet, KitchenTask task) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignedTask(sheet, task);
        }
    }

    private void notifyEditedTask(SummarySheet sheet, KitchenTask task) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateEditedTask(sheet, task);
        }
    }

    private void notifyDeletedTask(SummarySheet sheet, KitchenTask task) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateDeletedTask(sheet, task);
        }
    }

    private void notifyCanceledTask(SummarySheet sheet, KitchenTask task) {
        for (KitchenTaskEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateCanceledTask(sheet, task);
        }
    }
}