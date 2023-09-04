package catering.test.kitchen_task;

import java.util.Optional;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.kitchenTask.KitchenTask;
import catering.businesslogic.procedure.Procedure;

public class TestCatERing5a {
    public static void main(String[] args) {
        try{
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            
            Event event = CatERing.getInstance().getEventManager().getEventByID(1);
            Service service = event.getServices().get(0);
            CatERing.getInstance().getKitchenTaskManager().setCurrentSummarySheet(service.getSheet());

            System.out.println("\nTEST APRE FOGLIO RIEPILOGATIVO");
            CatERing.getInstance().getKitchenTaskManager().getCurrentSheet();

            System.out.println("\nCREO UNA TASK DA MODIFICARE IN SEGUITO");
            Procedure proc = CatERing.getInstance().getProcedureManager().getProcedureByID(1);
            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().insertTask(proc);

            System.out.println("\nTASK NON MODIFICATA \n");
            System.out.println(task.toString());
            
            Integer newTime = 10;
            String newQuantity = "5kg";
            Boolean newCompleted = false; 
            CatERing.getInstance().getKitchenTaskManager().editTask(task, Optional.ofNullable(newTime), Optional.ofNullable(newQuantity), Optional.ofNullable(newCompleted));
            System.out.println("\nTASK MODIFICATA \n");
            System.out.println(task.toString());

        }catch(UseCaseLogicException e){
            System.out.println("Errore di logica nello use case");
        }
           
    }
}
