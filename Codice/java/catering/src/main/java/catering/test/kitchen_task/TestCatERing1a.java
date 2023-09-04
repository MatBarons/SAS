package catering.test.kitchen_task;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.kitchenTask.KitchenTaskManager;
import catering.businesslogic.kitchenTask.SummarySheet;

public class TestCatERing1a {
    public static void main(String[] args) {
        try{
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            Service serv = CatERing.getInstance().getEventManager().getEventByID(1).getServices().get(0);
            CatERing.getInstance().getKitchenTaskManager().setCurrentSummarySheet(serv.getSheet());

            System.out.println("\nTEST APRE FOGLIO RIEPILOGATIVO");
            SummarySheet sheet = CatERing.getInstance().getKitchenTaskManager().getCurrentSheet();
            System.out.println(sheet.toString());
        }catch(UseCaseLogicException e){
            System.out.println(e.getMessage());
        }
           
    }
}
