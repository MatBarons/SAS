package catering.businesslogic.event;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Enumerations.EventStatus;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private String client;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int participants;
    private User organizer;
    private User chef;
    private EventStatus status;
    private int numServices;
    private String notes;
    private String docs;

    private ObservableList<Service> services;

    public Event(String name, LocalDate dateStart, LocalDate dateEnd, String client, User organizer) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.client = client;
        this.status = EventStatus.PROGRAMMATO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public User getChef() {
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public int getNumServices() {
        return numServices;
    }

    public void setNumServices(int numServices) {
        this.numServices = numServices;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public void setServices(ObservableList<Service> services) {
        this.services = services;
    }

    public ObservableList<Service> getServices() {
        return FXCollections.unmodifiableObservableList(this.services);
    }

    public String toString() {
        return name + ": " + dateStart + "-" + dateEnd + ", " + participants + " pp. (" + organizer.getUserName() + ")";
    }

    public void cancelEvent(String notes, String docs) throws UseCaseLogicException {
        this.notes = notes;
        this.docs = docs;
        this.status = EventStatus.ANNULLATO;

        for (Service s : services) {
            s.cancelService();
        }

    }

    public void assignStaff(Service service, User newStaff) throws UseCaseLogicException {
        service.addStaff(newStaff);
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<Event> loadAllEventInfo() {
        ObservableList<Event> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Events WHERE true";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                LocalDate dateStart = rs.getDate("date_start").toLocalDate();
                LocalDate dateEnd = rs.getDate("date_end").toLocalDate();
                String client = rs.getString("client");
                int org = rs.getInt("organizer_id");
                User organizer = User.loadUserById(org);
                Event e = new Event(n, dateStart, dateEnd, client, organizer);

                int chef = rs.getInt("chef_id");
                e.chef = User.loadUserById(chef);
                e.id = rs.getInt("id");
                e.participants = rs.getInt("expected_participants");
                e.numServices = rs.getInt("num_services");
                e.notes = rs.getString("notes");
                all.add(e);
            }
        });

        for (Event e : all) {
            e.services = Service.loadServiceInfoForEvent(e.id);
        }
        return all;
    }

    public static Event loadEventByID(int ID) {
        Event event = new Event(null, null, null, null, null);
        String query = "SELECT * FROM Events WHERE id = " + ID;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                event.name = rs.getString("name");
                event.dateStart = rs.getDate("date_start").toLocalDate();
                event.dateEnd = rs.getDate("date_end").toLocalDate();
                event.client = rs.getString("client");
                int org = rs.getInt("organizer_id");
                event.organizer = User.loadUserById(org);
                int chef = rs.getInt("chef_id");
                event.chef = User.loadUserById(chef);
                event.id = rs.getInt("id");
                event.participants = rs.getInt("expected_participants");
                event.numServices = rs.getInt("num_services");
                event.notes = rs.getString("notes");
            }
        });

        event.services = Service.loadServiceInfoForEvent(event.id);

        return event;
    }
}
