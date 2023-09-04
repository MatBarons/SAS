package catering.businesslogic.event;

import java.time.LocalDate;
import java.util.ArrayList;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Enumerations.EventStatus;
import catering.businesslogic.event.Enumerations.ServiceStatus;
import catering.businesslogic.kitchenTask.SummarySheet;
import catering.businesslogic.user.User;
import javafx.collections.ObservableList;

public class EventManager {

    private Event currentEvent;
    private ArrayList<EventEventReceiver> eventReceivers;


    public EventManager(){
        eventReceivers = new ArrayList<>();
    }

    public Event getEventByID(int ID){
        return Event.loadEventByID(ID);
    }

    public ObservableList<Event> getEventInfo() {
        return Event.loadAllEventInfo();
    }

    public Event createEvent(String name,LocalDate startDate,LocalDate endDate,String client) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(!user.isOrganizer()){
            throw new UseCaseLogicException();
        }
        Event event = new Event(name, startDate, endDate, client, user);
        setCurrentEvent(event);
        notifyCreatedEvent(event);
        return event;
    }

    public void cancelEvent(String notes,String docs) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(currentEvent.getStatus() != EventStatus.IN_CORSO || !user.isOrganizer() || !user.equals(currentEvent.getOrganizer())){
            throw new UseCaseLogicException();
        }
        currentEvent.cancelEvent(notes,docs);
        notifyCanceledEvent(currentEvent);
    }

    public void assignChef(User chef) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(!chef.isChef() && currentEvent.getStatus() != EventStatus.PROGRAMMATO || !user.isOrganizer() || !user.equals(currentEvent.getOrganizer())){
            throw new UseCaseLogicException();
        }
        currentEvent.setChef(chef);
        notifyAssignedChef(currentEvent);
    }

    public void assignStaff(Service service,User newStaff) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(currentEvent.getStatus() == EventStatus.ANNULLATO || currentEvent.getStatus() == EventStatus.TERMINATO  || 
           !user.isOrganizer() || !user.equals(currentEvent.getOrganizer()) || newStaff.isOrganizer() || newStaff.isChef() || 
           currentEvent.getServices().contains(service) || service.getStatus() == ServiceStatus.ANNULLATO || service.getStatus() ==  ServiceStatus.TERMINATO
        ){
            throw new UseCaseLogicException();
        }
        currentEvent.assignStaff(service,newStaff);
    }

    public SummarySheet getSummarySheetByService(Service service) throws UseCaseLogicException {
        return currentEvent.getSummarySheetByService(service);
    }

    public Event getCurrentEvent(){
        return currentEvent;
    }

    public void setCurrentEvent(Event e){
        this.currentEvent = e;
    }

    //NOTIFIERS
    public void addReceiver(EventEventReceiver e){
        eventReceivers.add(e);
    }

    public void removeReceiver(EventEventReceiver e){
        eventReceivers.remove(e);
    }


    private void notifyCreatedEvent(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateCreatedEvent(event);
        }
    }

    private void notifyEditedEvent(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateEditedEvent(event);
        }
    }

    private void notifyCanceledEvent(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateCanceledEvent(event);
        }
    }

    private void notifyDeletedEvent(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateDeletedEvent(event);
        }
    }

    private void notifyEndedEvent(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateEndedEvent(event);
        }
    }

    private void notifyAssignedChef(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignedChef(event);
        }
    }

    public void notifyAssignedStaff(Event event){
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignedStaff(event);
        }
    }
}
