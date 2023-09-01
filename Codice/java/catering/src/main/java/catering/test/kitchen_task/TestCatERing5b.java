package catering.test.kitchen_task;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.kitchenTask.KitchenTask;
import catering.businesslogic.recipe.Procedure;

public class TestCatERing5b {

    public static void main(String[] args) {
        try {

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST CREA FOGLIO RIEPILOGATIVO");
            Event event = CatERing.getInstance().getEventManager().getEventByID(0);
            Service service = event.getServices().get(0);
            CatERing.getInstance().getKitchenTaskManager().generateSummarySheet(event, service);

            System.out.println("\nCREO E INSERISCO UNA TASK");
            Procedure proc = CatERing.getInstance().getProcedureManager().getProcedureByID(0);
            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().insertTask(proc);

            System.out.println("\nTUTTE LE TASK PRIMA DELLA CANCELLAZIONE \n");
            System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSheet().toString());
            
            CatERing.getInstance().getKitchenTaskManager().removeTask(task);
            System.out.println("\nTUTTE LE TASK DOPO LA CANCELLAZIONE \n");
            System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSheet().toString());
        } catch (UseCaseLogicException e) {
        }
    }

}