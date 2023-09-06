package catering.businesslogic.kitchenTask;

import catering.businesslogic.event.Service;
import catering.businesslogic.event.Event;

public interface KitchenTaskEventReceiver{
    public void updateSheetGenerated(Event event,Service service);
    public void updateInsertedTask(SummarySheet sheet,KitchenTask task);
    public void updateRearrangedSheet(SummarySheet sheet);
    public void updateAssignedTask(SummarySheet sheet,KitchenTask task);
    public void updateEditedTask(SummarySheet sheet,KitchenTask task);
    public void updateDeletedTask(SummarySheet sheet,KitchenTask task);
    public void updateCanceledTask(SummarySheet sheet,KitchenTask task);
}