package catering.test.kitchen_task;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.kitchenTask.KitchenTask;
import catering.businesslogic.procedure.Procedure;

public class TestCatERing5b {

    public static void main(String[] args) {
        try {

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            Event event = CatERing.getInstance().getEventManager().getEventByID(1);
            Service service = event.getServices().get(0);
            CatERing.getInstance().getKitchenTaskManager().setCurrentSummarySheet(service.getSheet());

            System.out.println("\nTEST APRE FOGLIO RIEPILOGATIVO");
            CatERing.getInstance().getKitchenTaskManager().getCurrentSheet();

            System.out.println("\nCREO E INSERISCO UNA TASK");
            Procedure proc = CatERing.getInstance().getProcedureManager().getProcedureByID(2);
            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().insertTask(proc);

            System.out.println("\nTUTTE LE TASK PRIMA DELLA CANCELLAZIONE \n");
            System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSheet().toString());
            
            CatERing.getInstance().getKitchenTaskManager().removeTask(task);
            System.out.println("\nTUTTE LE TASK DOPO LA CANCELLAZIONE \n");
            System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSheet().toString());
        } catch (UseCaseLogicException e) {
            System.out.println(e.getMessage());
        }
    }

}