package catering.businesslogic.event;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Enumerations.ServiceStatus;
import catering.businesslogic.kitchenTask.KitchenTask;
import catering.businesslogic.kitchenTask.SummarySheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class Service {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private String place;
    private ServiceStatus status;
    private Menu menu;

    private SummarySheet sheet;
    private ArrayList<User> staff;

    public Service(String name) {
        this.name = name;
        this.status = ServiceStatus.IN_ATTESA;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public SummarySheet getSheet() throws UseCaseLogicException {
        if (sheet == null) {
            throw new UseCaseLogicException("Foglio riepilogativo non ancora creato");
        }
        return sheet;
    }

    public void setSheet(SummarySheet sheet) {
        this.sheet = sheet;
    }

    public ArrayList<User> getStaff() {
        return staff;
    }

    public void addStaff(User u) {
        staff.add(u);
    }

    public boolean isConfirmed() {
        return status == ServiceStatus.IN_CORSO;
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }


    public void cancelService() throws UseCaseLogicException {
        this.status = ServiceStatus.ANNULLATO;
        for (KitchenTask task : sheet.getTasks()) {
            sheet.cancelTask(task);
        }
        staff.clear();
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<Service> loadServiceInfoForEvent(int event_id) {
        ObservableList<Service> result = FXCollections.observableArrayList();
        String query = "SELECT * FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                Service serv = new Service(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");

                int proposed_menu_id = rs.getInt("proposed_menu_id");
                int approved_menu_id = rs.getInt("approved_menu_id");

                if (proposed_menu_id == 0 && approved_menu_id == 0) {
                    serv.menu = null;
                    serv.status = ServiceStatus.IN_ATTESA;
                }

                if (approved_menu_id != 0) {
                    serv.menu = Menu.loadMenuById(approved_menu_id);
                    serv.status = ServiceStatus.IN_CORSO;
                }

                if (proposed_menu_id != 0) {
                    serv.menu = Menu.loadMenuById(proposed_menu_id);
                    serv.status = ServiceStatus.IN_ATTESA;
                }

                SummarySheet sheet = SummarySheet.loadSummarySheetByServiceID(serv.id);

                serv.setSheet(sheet);

                result.add(serv);
            }
        });

        return result;
    }


}
