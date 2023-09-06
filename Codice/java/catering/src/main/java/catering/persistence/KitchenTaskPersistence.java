package catering.persistence;

import catering.businesslogic.event.Service;
import catering.businesslogic.event.Event;
import catering.businesslogic.kitchenTask.KitchenTask;
import catering.businesslogic.kitchenTask.KitchenTaskEventReceiver;
import catering.businesslogic.kitchenTask.SummarySheet;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {

    @Override
    public void updateSheetGenerated(Event event,Service service) {
        SummarySheet.saveNewSummarySheet(event,service);
    }

    @Override
    public void updateInsertedTask(SummarySheet sheet, KitchenTask task) {
        KitchenTask.saveNewTask(sheet.getID(), task);
    }

    @Override
    public void updateRearrangedSheet(SummarySheet sheet) {
        KitchenTask.saveTaskOrder(sheet);
    }

    @Override
    public void updateAssignedTask(SummarySheet sheet, KitchenTask task) {
        KitchenTask.saveKitchenTaskAssigned(sheet.getID(), task);
    }

    @Override
    public void updateEditedTask(SummarySheet sheet, KitchenTask task) {
        KitchenTask.saveKitchenTaskEdited(sheet.getID(), task);
    }

    @Override
    public void updateDeletedTask(SummarySheet sheet, KitchenTask task) {
        KitchenTask.updateDeleteKitchenTask(sheet.getID(), task);
    }

    @Override
    public void updateCanceledTask(SummarySheet sheet, KitchenTask task) {
        KitchenTask.updateCancelKitchenTask(sheet.getID(), task);
    }
}
