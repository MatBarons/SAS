package catering.businesslogic.procedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Procedure{
    
    protected int id;
    protected String name;
    protected int resQty;
    protected boolean isPublic;
    protected String description;
    protected String tag;
    protected ArrayList<String> ingredients;
    protected ArrayList<Integer> ingredientsQty;
    protected String instructions;
    protected ArrayList<User> authors;

    private static Map<Integer, Procedure> all = new HashMap<>();
    
    public Procedure(){
        ingredients = new ArrayList<>();
        authors = new ArrayList<>();
        ingredientsQty = new ArrayList<>();
    }

    public Procedure(String name){
        this.name = name;
        ingredients = new ArrayList<>();
        authors = new ArrayList<>();
        ingredientsQty = new ArrayList<>();
    }
    

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }


    public static ObservableList<Procedure> loadAllProcedures() {
        String query = "SELECT * FROM Procedures";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Procedure proc = all.get(id);
                    proc.name = rs.getString("name");
                } else {
                    Procedure proc = new Procedure(rs.getString("name"));
                    proc.id = id;
                    all.put(proc.id, proc);
                }
            }
        });
        ObservableList<Procedure> ret =  FXCollections.observableArrayList(all.values());
        Collections.sort(ret, new Comparator<Procedure>() {
            @Override
            public int compare(Procedure o1, Procedure o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    public static ObservableList<Procedure> getAllProcedures() {
        return FXCollections.observableArrayList(all.values());
    }

    public static Procedure loadProcedureById(int id) {
        if (all.containsKey(id)) return all.get(id);
        Procedure proc = new Procedure();
        String query = "SELECT * FROM Procedures WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                    proc.name = rs.getString("name");
                    proc.id = id;
                    all.put(proc.id, proc);
            }
        });
        return proc;
    }
}