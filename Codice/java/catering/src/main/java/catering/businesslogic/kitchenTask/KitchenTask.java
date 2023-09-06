package catering.businesslogic.kitchenTask;

import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class KitchenTask {

    private int ID;
    private int estimatedTime;
    private String quantity;
    private boolean completed;
    private boolean toDo;
    private Procedure procedure;
    private KitchenShift shift;
    private User cook;

    public KitchenTask(Procedure proc) {
        this.toDo = true;
        this.completed = false;
        this.procedure = proc;
    }

    public static void saveNewTask(int sheet_id, KitchenTask task) {
        String taskInsert = "INSERT INTO catering.KitchenTasks (summarySheet_id, procedure_id) VALUES (" +
                sheet_id + ", " +
                task.getProcedure().getId() + ");";
        PersistenceManager.executeUpdate(taskInsert);
        task.ID = PersistenceManager.getLastId();
    }

    public static ArrayList<KitchenTask> loadTasksOfSummarySheetById(int smId) {
        String selectTasks = "SELECT * FROM KitchenTasks WHERE summarySheet_id = " + smId;

        ArrayList<KitchenTask> tasksList = new ArrayList<>();

        PersistenceManager.executeQuery(selectTasks, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                KitchenTask task = new KitchenTask(Procedure.loadProcedureById(rs.getInt("procedure_id")));
                task.ID = rs.getInt(1);
                task.cook = User.loadUserById(rs.getInt("cook_id"));
                task.shift = (KitchenShift) Shift.loadShiftById(rs.getInt("shift_id"));
                task.toDo = rs.getBoolean("toDo");
                task.completed = rs.getBoolean("completed");
                task.estimatedTime = rs.getInt("estimatedTime");
                task.quantity = rs.getString("quantity");
                tasksList.add(task);
            }
        });

        return tasksList;
    }

    public static void saveTaskOrder(SummarySheet sheet) {
        String upd = "UPDATE KitchenTasks SET position = ? WHERE id = ?";
        PersistenceManager.executeBatchUpdate(upd, sheet.getTasks().size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, batchCount);
                ps.setInt(2, sheet.getTasks().get(batchCount).getID());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
            }
        });
    }

    public static void saveKitchenTaskAssigned(int sheet_id, KitchenTask task) {
        String updateTaskAssigned = "UPDATE catering.KitchenTasks SET toPrepare = ?, completed = ?, quantity = ?, estimatedTime = ?, cook_id = ?, shift_id = ? WHERE id = ?";

        PersistenceManager.executeBatchUpdate(updateTaskAssigned, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setBoolean(1, task.isToDo());
                ps.setBoolean(2, task.isCompleted());
                ps.setString(3, task.getQuantity());
                ps.setInt(4, task.getEstimatedTime());

                if (task.getCook() != null)
                    ps.setInt(5, task.getCook().getId());
                else
                    ps.setNull(5, Types.INTEGER);

                if (task.getShift() != null)
                    ps.setInt(6, task.getShift().getID());
                else
                    ps.setNull(6, Types.INTEGER);

                ps.setInt(7, task.getID());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
            }
        });
    }

    public static void saveKitchenTaskEdited(int sheet_id, KitchenTask task) {
        String taskEdit = "UPDATE catering.KitchenTasks SET estimatedTime = " + task.getEstimatedTime() +
                ", quantity = '" + task.getQuantity() +
                "', completed = " + task.isCompleted() +
                " WHERE id = " + task.getID() + ";";
        System.out.println(taskEdit);
        PersistenceManager.executeUpdate(taskEdit);
    }

    public static void updateDeleteKitchenTask(int sheet_id, KitchenTask task) {
        String deleteKitchenTask = "DELETE FROM KitchenTasks WHERE id = " + task.getID();
        System.out.println(deleteKitchenTask);
        PersistenceManager.executeUpdate(deleteKitchenTask);
    }

    public static void updateCancelKitchenTask(int sheet_id, KitchenTask task) {
        String updateTaskCanceled = "UPDATE catering.KitchenTasks SET toDo = " + task.isToDo()
                + " WHERE id = " + task.getID();
        PersistenceManager.executeUpdate(updateTaskCanceled);
    }

    public static void saveAllNewTasks(int id, ArrayList<KitchenTask> tasks) {
        String taskInsert = "INSERT INTO catering.KitchenTasks (summarySheet_id, procedure_id) VALUES (?, ?);";
        PersistenceManager.executeBatchUpdate(taskInsert, tasks.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, id);
                ps.setInt(2, tasks.get(batchCount).getProcedure().getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                int ID = rs.getInt(1);
                tasks.get(count).setID(ID);
            }
        });
    }

    public String toString() {
        return "KitchenTask{" +
                "ID=" + ID +
                ", estimatedTime=" + estimatedTime +
                ", quantity='" + quantity + '\'' +
                ", completed=" + completed +
                ", toDo=" + toDo +
                ", procedure=" + procedure +
                ", shift=" + shift +
                ", cook=" + cook +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isToDo() {
        return toDo;
    }

    public void setToDo(boolean toDo) {
        this.toDo = toDo;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public KitchenShift getShift() {
        return shift;
    }

    public void setShift(KitchenShift shift) {
        this.shift = shift;
    }

    public User getCook() {
        return cook;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

}