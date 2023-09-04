package catering.businesslogic.procedure;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Map;

public class ProcedureManager {

    public ProcedureManager() {
        Procedure.loadAllProcedures();
    }

    public Map<Integer, Procedure> getAll() {
        return Procedure.getAllProcedures();
    }

    public Map<Integer, Preparation> getAllPreparations() {
        return Procedure.getAllPreparations();
    }

    public Map<Integer, Recipe> getAllRecipes() {
        return Procedure.getAllRecipes();
    }

    public Procedure getProcedureByID(int ID) {
        return Procedure.loadProcedureById(ID);
    }

    public Recipe getRecipeByID(int ID) {
        return Recipe.loadRecipeById(ID);
    }

    public Preparation getPreparationByID(int ID) {
        return Preparation.loadPreparationById(ID);
    }
}
