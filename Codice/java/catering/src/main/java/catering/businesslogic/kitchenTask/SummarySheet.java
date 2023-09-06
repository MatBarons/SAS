package catering.businesslogic.kitchenTask;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

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
        if (shift.isPresent() && cook.isEmpty()) {
            task.setShift(shift.get());
        } else if (shift.isEmpty() && cook.isPresent()) {
            task.setCook(cook.get());
        } else if (shift.isPresent() && cook.isPresent()) {
            task.setShift(shift.get());
            task.setCook(cook.get());
        }
        if (time.isPresent()) {
            task.setEstimatedTime(time.get());
        }
        if (quantity.isPresent()) {
            task.setQuantity(quantity.get());
        }
        task.setCompleted(false);
        task.setToDo(true);

    }

    public void editTask(KitchenTask task, Optional<Integer> time, Optional<String> quantity, Optional<Boolean> completed) {
        if (completed.isPresent()) {
            task.setCompleted(completed.get());
        }
        if (time.isPresent()) {
            task.setEstimatedTime(time.get());
        }
        if (quantity.isPresent()) {
            task.setQuantity(quantity.get());
        }
    }

    public void cancelTask(KitchenTask task) {
        task.setToDo(false);
        task.setCook(null);
        task.setShift(null);
    }

    public void deleteTask(KitchenTask task) {
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