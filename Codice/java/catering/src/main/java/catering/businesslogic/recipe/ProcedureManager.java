package catering.businesslogic.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProcedureManager {

    private Map<Integer, Procedure> all = new HashMap<>();

    public ProcedureManager() {
        Procedure.loadAllProcedures();
    }

    public Map<Integer, Procedure> getAll(){
        return all;
    }

    public  ArrayList<Preparation> getAllPreparations(){
        return all.values().stream().filter(p -> p instanceof Preparation).map(p -> (Preparation) p).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Recipe> getAllRecipes(){
        return all.values().stream().filter(p -> p instanceof Recipe).map(p -> (Recipe) p).collect(Collectors.toCollection(ArrayList::new));
    }

    public Procedure getProcedureByID(int ID){
        return Procedure.loadProcedureById(ID);
    }

    public Recipe getRecipeByID(int ID){
        return Recipe.loadRecipeById(ID);
    }

    public Preparation getPreparationByID(int ID){
        return Preparation.loadPreparationById(ID);
    }
}
