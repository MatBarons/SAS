package catering.businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import catering.businesslogic.CatERing;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends Procedure{

    private Recipe() {
        super();
    }

    public Recipe(String name) {
        super();
        id = 0;
        this.name = name;
    }

    public static ObservableList<Recipe> loadAllRecipes() {
        String query = "SELECT * FROM Procedures WHERE type = 'recipe'";
        ProcedureManager pm = CatERing.getInstance().getProcedureManager();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (pm.getAll().containsKey(id)) {
                    Recipe rec = pm.getAllRecipes().get(id);
                    rec.name = rs.getString("name");
                } else {
                    Recipe rec = new Recipe(rs.getString("name"));
                    rec.id = id;
                    pm.getAll().put(rec.id, rec);
                }
            }
        });
        ObservableList<Recipe> ret =  FXCollections.observableArrayList(pm.getAllRecipes());
        Collections.sort(ret, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    public static Recipe loadRecipeById(int id) {
        ProcedureManager pm = CatERing.getInstance().getProcedureManager();
        if (pm.getAll().containsKey(id)) return pm.getAllRecipes().get(id);
        Recipe rec = new Recipe();
        String query = "SELECT * FROM Procedures WHERE type = 'recipe' AND id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                    rec.name = rs.getString("name");
                    rec.id = id;
                    pm.getAll().put(rec.id, rec);
            }
        });
        return rec;
    }


}
