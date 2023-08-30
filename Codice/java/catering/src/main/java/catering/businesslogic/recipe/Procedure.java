package catering.businesslogic.recipe;

import java.util.ArrayList;

public abstract class Procedure{

    protected String name;
    protected int resQty;
    protected boolean isPublic;
    protected String description;
    protected String tag;
    protected ArrayList<String> ingredients;
    protected ArrayList<Integer> ingredientsQty;
    protected String instructions;

}