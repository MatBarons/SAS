package catering.businesslogic.event;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Enumerations.EventStatus;
import catering.businesslogic.event.Enumerations.ServiceStatus;
import catering.businesslogic.kitchenTask.SummarySheet;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventManager {

    private Event currentEvent;
    private ArrayList<EventEventReceiver> eventReceivers;


    public EventManager() {
        eventReceivers = new ArrayList<>();
    }

    public Event getEventByID(int ID) {
        return Event.loadEventByID(ID);
    }

    public ObservableList<Event> getEventInfo() {
        return Event.loadAllEventInfo();
    }

    public Event createEvent(String name, LocalDate startDate, LocalDate endDate, String client) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isOrganizer()) {
            throw new UseCaseLogicException();
        }
        if (startDate.isAfter(endDate) || endDate.isBefore(startDate)) {
            throw new UseCaseLogicException();
        }
        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new UseCaseLogicException("Date errate");
        }
        Event event = new Event(name, startDate, endDate, client, user);
        setCurrentEvent(event);
        notifyCreatedEvent(event);
        return event;
    }

    public void cancelEvent(String notes, String docs) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentEvent.getStatus() != EventStatus.IN_CORSO || !user.isOrganizer() || !user.equals(currentEvent.getOrganizer())) {
            throw new UseCaseLogicException();
        }
        if (currentEvent.getDateEnd().isBefore(LocalDate.now())) {
            throw new UseCaseLogicException("Data fine già passata");
        }
        currentEvent.cancelEvent(notes, docs);
        notifyCanceledEvent(currentEvent);
    }

    public void assignChef(User chef) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!chef.isChef() && currentEvent.getStatus() != EventStatus.PROGRAMMATO || !user.isOrganizer() || !user.equals(currentEvent.getOrganizer())) {
            throw new UseCaseLogicException();
        }
        currentEvent.setChef(chef);
        notifyAssignedChef(currentEvent, chef);
    }

    public void assignStaff(Service service, User newStaff, Shift shift) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isOrganizer() || !user.equals(currentEvent.getOrganizer())) {
            throw new UseCaseLogicException();
        }
        if (currentEvent.getStatus() == EventStatus.ANNULLATO || currentEvent.getStatus() == EventStatus.TERMINATO) {
            throw new UseCaseLogicException();
        }
        if (currentEvent.getServices().contains(service)) {
            throw new UseCaseLogicException("L'evento non include il servizio");
        }
        if (service.getStatus() == ServiceStatus.ANNULLATO || service.getStatus() == ServiceStatus.TERMINATO) {
            throw new UseCaseLogicException("Servizio già annullato o terminato");
        }
        if (newStaff.isOrganizer() || newStaff.isChef() || newStaff.isAvailable(shift)) {
            throw new UseCaseLogicException("Staff non disponibile");
        }
        currentEvent.assignStaff(service, newStaff);
        notifyAssignedStaff(currentEvent, service, newStaff);
    }

    public SummarySheet getSummarySheetByService(Service service) throws UseCaseLogicException {
        return currentEvent.getSummarySheetByService(service);
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event e) {
        this.currentEvent = e;
    }

    //NOTIFIERS
    public void addReceiver(EventEventReceiver e) {
        eventReceivers.add(e);
    }

    public void removeReceiver(EventEventReceiver e) {
        eventReceivers.remove(e);
    }


    private void notifyCreatedEvent(Event event) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateCreatedEvent(event);
        }
    }

    private void notifyEditedEvent(Event event) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateEditedEvent(event);
        }
    }

    private void notifyCanceledEvent(Event event) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateCanceledEvent(event);
        }
    }

    private void notifyDeletedEvent(Event event) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateDeletedEvent(event);
        }
    }

    private void notifyEndedEvent(Event event) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateEndedEvent(event);
        }
    }

    private void notifyAssignedChef(Event event, User chef) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignedChef(event, chef);
        }
    }

    public void notifyAssignedStaff(Event event, Service service, User staff) {
        for (EventEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignedStaff(event, service, staff);
        }
    }
}
