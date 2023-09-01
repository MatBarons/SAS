package catering.businesslogic.recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;

import catering.businesslogic.CatERing;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Preparation extends Procedure{

    private Preparation() {

    }
    
    public Preparation(String name){
        this.name = name;
    }

    public static ObservableList<Preparation> loadAllPreparations(){
        String query = "SELECT * FROM Procedures WHERE type = 'preparation'";
        ProcedureManager pm = CatERing.getInstance().getProcedureManager();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (pm.getAll().containsKey(id)) {
                    Preparation prep = (Preparation)pm.getAll().get(id);
                    prep.name = rs.getString("name");
                } else {
                    Preparation prep = new Preparation(rs.getString("name"));
                    prep.id = id;
                    pm.getAll().put(prep.id, prep);
                }
            }
        });
        ObservableList<Preparation> ret =  FXCollections.observableArrayList(pm.getAllPreparations());
        Collections.sort(ret, new Comparator<Preparation>() {
            @Override
            public int compare(Preparation o1, Preparation o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }
    

    public static Preparation loadPreparationById(int id) {
        ProcedureManager pm = CatERing.getInstance().getProcedureManager();
        if (pm.getAll().containsKey(id)) return pm.getAllPreparations().get(id);
        Preparation prep = new Preparation();
        String query = "SELECT * FROM Procedures WHERE type = 'preparation' AND id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                    prep.name = rs.getString("name");
                    prep.id = id;
                    pm.getAll().put(prep.id, prep);
            }
        });
        return prep;
    }
}