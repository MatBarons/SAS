package catering.businesslogic.event;

interface EventEventReceiver{
    public void updateCreatedEvent(Event event);
    public void updateEditedEvent(Event event);
    public void updateCanceledEvent(Event event);
    public void updateDeletedEvent(Event event);
    public void updateEndedEvent(Event event);
    public void updateAssignedChef(Event event);
    public void updateAssignedStaff(Event event);
}