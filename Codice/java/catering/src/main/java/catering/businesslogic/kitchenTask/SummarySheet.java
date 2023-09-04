package catering.businesslogic.kitchenTask;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SummarySheet {

    private int ID;
    private ArrayList<KitchenTask> tasks;

    public SummarySheet() {
        this.ID = 0;
        this.tasks = new ArrayList<>();
    }

    public static void saveNewSummarySheet(Event event, Service service) {
        String summarySheetInsert = "INSERT INTO catering.SummarySheets (service_id) VALUES (?);";
        SummarySheet sheet;
        try {
            sheet = service.getSheet();
        } catch (UseCaseLogicException e) {
            e.printStackTrace();
            return;
        }
        int[] result = PersistenceManager.executeBatchUpdate(summarySheetInsert, 1, new BatchUpdateHandler() {

            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, service.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    sheet.setID(rs.getInt(1));
                }
            }
        });
        if (result[0] > 0) {
            if (sheet.getTasks().size() > 0) {
                KitchenTask.saveAllNewTasks(sheet.getID(), sheet.getTasks());
            }
        }
    }

    public static void saveTaskOrder(SummarySheet sheet) {
        String upd = "UPDATE KitchenTasks SET position = ? WHERE id = ?";
        PersistenceManager.executeBatchUpdate(upd, sheet.tasks.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, batchCount);
                ps.setInt(2, sheet.tasks.get(batchCount).getID());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
            }
        });
    }

    public static ObservableList<SummarySheet> loadAllSheets() {
        String query = "SELECT * FROM SummarySheets";
        ObservableList<SummarySheet> summarySheets = FXCollections.observableArrayList();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int serviceId = rs.getInt("service_id");
                SummarySheet sm = new SummarySheet();
                sm.ID = rs.getInt("id");
                sm.tasks = KitchenTask.loadTasksOfSummarySheetById(sm.ID);
//                sm.

//                summarySheets.add(sm);
            }
        });
        return summarySheets;
    }

    public static SummarySheet loadSummarySheetByServiceID(int serviceID) {
        String query = "SELECT * FROM SummarySheets WHERE service_id = " + serviceID;
        SummarySheet summarySheet = new SummarySheet();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                summarySheet.ID = rs.getInt("id");
                summarySheet.tasks = KitchenTask.loadTasksOfSummarySheetById(summarySheet.ID);
            }
        });
        return summarySheet;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<KitchenTask> getTasks() {
        return tasks;
    }

    public void addTask(KitchenTask task) {
        tasks.add(task);
    }

    public void changeTaskPosition(int position, KitchenTask task) {
        tasks.remove(task);
        tasks.add(position, task);
    }

    public void assignTask(KitchenTask task, Optional<KitchenShift> shift, Optional<User> cook, Optional<Integer> time, Optional<String> quantity) throws UseCaseLogicException {
        if (shift != null && cook == null) {
            task.setShift(shift.get());
        } else if (shift == null && cook != null) {
            task.setCook(cook.get());
        } else if (shift != null && cook != null) {
            if (!cook.get().isAvailable(shift.get())) {
                throw new UseCaseLogicException();
            }
            task.setShift(shift.get());
            task.setCook(cook.get());
        }
        if (time != null) {
            task.setEstimatedTime(time.get());
        }
        if (quantity != null) {
            task.setQuantity(quantity.get());
        }
        task.setCompleted(false);
        task.setToDo(true);

    }

    public void editTask(KitchenTask task, Optional<Integer> time, Optional<String> quantity, Optional<Boolean> completed) {
        if (completed != null) {
            task.setCompleted(completed.get());
        }
        if (time != null) {
            task.setEstimatedTime(time.get());
        }
        if (quantity != null) {
            task.setQuantity(quantity.get());
        }
    }

    public void cancelTask(KitchenTask task) throws UseCaseLogicException {
        if (task.isCompleted() || !task.isToDo()) {
            throw new UseCaseLogicException();
        }
        task.setToDo(false);
        task.setCook(null);
        task.setShift(null);
    }

    public void removeTask(KitchenTask task) throws UseCaseLogicException {
        if (task.isCompleted() || !task.isToDo()) {
            throw new UseCaseLogicException();
        }
        tasks.remove(task);
    }

    @Override
    public String toString() {
        String r = "SUMMARYSHEET: \n\t";
        for (KitchenTask t : tasks) {
            r += t.toString() + "\n\t";
        }
        return r;
    }
}