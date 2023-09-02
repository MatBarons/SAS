package catering.test.kitchen_task;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import catering.businesslogic.kitchenTask.KitchenTask;
import catering.businesslogic.kitchenTask.SummarySheet;
import catering.businesslogic.procedure.Procedure;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;

public class TestCatERing {
    public static void main(String[] args) {
        try {

            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST CREA FOGLIO RIEPILOGATIVO");
            Event event = CatERing.getInstance().getEventManager().getEventByID(1);
            Service service = event.getServices().get(0);
            //TODO: FIX HERE
            // IL SERVIZIO VIENE DATO COME NON CONFERMATO
            SummarySheet sheet = CatERing.getInstance().getKitchenTaskManager().generateSummarySheet(event, service);
            System.out.println(sheet.toString());

            System.out.println("\nTEST AGGIUNGI PROCEDURA AL FOGLIO RIEPILOGATIVO");
            Procedure p = CatERing.getInstance().getProcedureManager().getProcedureByID(1);
            CatERing.getInstance().getKitchenTaskManager().insertTask(p);
            System.out.println(sheet);

            System.out.println("\nTEST RIORDINA RIEPILOGATIVO");
            KitchenTask task = CatERing.getInstance().getKitchenTaskManager().getCurrentSheet().getTasks().get(1);
            CatERing.getInstance().getKitchenTaskManager().changeTaskPosition(0, task);
            System.out.println(sheet);

            System.out.println("\nTEST CONSULTA TURNI CUCINA");
            ArrayList<KitchenShift> shiftBoard = CatERing.getInstance().getKitchenTaskManager().getKitchenShiftBoard(0);
            System.out.println(shiftBoard);

            System.out.println("\nTEST ASSEGNA COMPITI CUCINA");
            KitchenTask task2 = CatERing.getInstance().getKitchenTaskManager().getCurrentSheet().getTasks().get(1);
            System.out.println("\n PRIMA DELL'ASSEGNAMENTO: \n");
            System.out.println(task2);
            KitchenShift shift = CatERing.getInstance().getKitchenTaskManager().getKitchenShiftBoard(0).get(0);
            task2.setShift(shift);
            User cook = CatERing.getInstance().getUserManager().getCook("Guido");
            task2.setCook(cook);
            task2.setEstimatedTime(60);
            task2.setQuantity("10kg");
            System.out.println("DOPO L'ASSEGNAMENTO: \n");
            System.out.println(task2);


        } catch (UseCaseLogicException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
