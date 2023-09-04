package catering.businesslogic.procedure;

import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Procedure {

    private static Map<Integer, Procedure> all = new HashMap<>();
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

    public Procedure() {
        ingredients = new ArrayList<>();
        authors = new ArrayList<>();
        ingredientsQty = new ArrayList<>();
    }

    public Procedure(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
        authors = new ArrayList<>();
        ingredientsQty = new ArrayList<>();
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
                    String type = rs.getString("type");
                    Procedure proc;
                    if (type.equals("recipe")) {
                        proc = new Recipe(rs.getString("name"));
                    } else {
                        proc = new Preparation(rs.getString("name"));
                    }
                    proc.id = id;
                    all.put(proc.id, proc);
                }
            }
        });
        ObservableList<Procedure> ret = FXCollections.observableArrayList(all.values());
        Collections.sort(ret, new Comparator<Procedure>() {
            @Override
            public int compare(Procedure o1, Procedure o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    public static Map<Integer, Procedure> getAllProcedures() {
        return all;
    }

    public static Map<Integer, Preparation> getAllPreparations() {
        return all.values().stream().filter(p -> p instanceof Preparation).map(p -> (Preparation) p).collect(Collectors.toMap(Preparation::getId, p -> p));
    }

    public static Map<Integer, Recipe> getAllRecipes() {
        return all.values().stream().filter(p -> p instanceof Recipe).map(p -> (Recipe) p).collect(Collectors.toMap(Recipe::getId, r -> r));
    }

    public static Procedure loadProcedureById(int id) {
        if (all.containsKey(id)) return all.get(id);
        AtomicReference<Procedure> procRef = new AtomicReference<>();
        String query = "SELECT * FROM Procedures WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String type = rs.getString("type");
                Procedure proc;
                if (type.equals("recipe")) {
                    proc = new Recipe(rs.getString("name"));
                } else {
                    proc = new Preparation(rs.getString("name"));
                }
                proc.name = rs.getString("name");
                proc.id = id;
                all.put(proc.id, proc);
                procRef.set(proc);
            }
        });

        return procRef.get();
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
}