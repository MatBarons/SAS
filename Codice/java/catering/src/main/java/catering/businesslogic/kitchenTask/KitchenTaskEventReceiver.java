package catering.businesslogic.kitchenTask;

interface KitchenTaskEventReceiver{
    public void updateSheetGenerated(SummarySheet sheet);
    public void updateInsertedTask(SummarySheet sheet,KitchenTask task);
    public void updateRearrangedSheet(SummarySheet sheet);
    public void updateAssignedTask(SummarySheet sheet,KitchenTask task);
    public void updateEditedTask(SummarySheet sheet,KitchenTask task);
    public void updateDeletedTask(SummarySheet sheet,KitchenTask task);
    public void updateCancelTask(SummarySheet sheet,KitchenTask task);
}