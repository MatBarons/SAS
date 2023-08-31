package catering.businesslogic.kitchenTask;
import java.util.ArrayList;
import java.util.Optional;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;

public class SummarySheet{

    private ArrayList<KitchenTask> tasks;
    public SummarySheet() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<KitchenTask> getTasks() {
        return tasks;
    }

    public void addTask(KitchenTask task){
        tasks.add(task);
    }

    public void changeTaskPosition(int position,KitchenTask task){
        tasks.remove(task);
        tasks.add(position,task);
    }

    public void assignTask(KitchenTask task,Optional<KitchenShift> shift,Optional<User> cook,Optional<Integer> time,Optional<String> quantity) throws UseCaseLogicException{
        if(shift != null && cook == null){
            task.setShift(shift.get());
        } else if(shift == null && cook != null){
            task.setCook(cook.get());
        }else if(shift != null && cook != null){
            if(!cook.get().isAvailable(shift.get())){
                throw new UseCaseLogicException();
            }
            task.setShift(shift.get());
            task.setCook(cook.get());
        }
        if(time != null){
            task.setEstimatedTime(time.get());
        }
        if(quantity != null){
            task.setQuantity(quantity.get());
        }
        task.setCompleted(false);
        task.setToDo(true);

    }

    public void editTask(KitchenTask task,Optional<Integer> time,Optional<String> quantity,Optional<Boolean> completed){
        if(completed != null){
            task.setCompleted(completed.get());
        }
        if(time != null){
            task.setEstimatedTime(time.get());
        }
        if(quantity != null){
            task.setQuantity(quantity.get());
        }
    }

    public void cancelTask(KitchenTask task) throws UseCaseLogicException{
        if(task.isCompleted() || !task.isToDo()){
            throw new UseCaseLogicException();   
        }
        task.setToDo(false);
        task.setCook(null);
        task.setShift(null);
    }

    public void removeTask(KitchenTask task) throws UseCaseLogicException{
        if(task.isCompleted() || !task.isToDo()){
            throw new UseCaseLogicException();   
        }
        tasks.remove(task);
    }
}