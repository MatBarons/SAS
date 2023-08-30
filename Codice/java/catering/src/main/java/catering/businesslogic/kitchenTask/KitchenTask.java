package catering.businesslogic.kitchenTask;

import catering.businesslogic.recipe.Procedure;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

class KitchenTask{

    private int estimatedTime;
    private String quantity;
    private boolean completed;
    private boolean toDo;
    private Procedure procedure;
    private KitchenShift shift;
    private User cook;

    public KitchenTask(Procedure proc) {
        this.procedure = proc;
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

    public KitchenShift getShift(){
        return shift;
    }

    public void setShift(KitchenShift shift){
        this.shift = shift;
    }

    public User getCook(){
        return cook;
    }

    public void setCook(User cook){
        this.cook = cook;
    }
}