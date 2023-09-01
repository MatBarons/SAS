package catering.test.kitchen_task;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.kitchenTask.SummarySheet;

public class TestCatERing1a {
    public static void main(String[] args) {
        try{
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST APRE FOGLIO RIEPILOGATIVO");
            SummarySheet sheet = CatERing.getInstance().getEventManager().getEventByID(0).getServices().get(0).getSheet();
            System.out.println(sheet.toString());
        }catch(UseCaseLogicException e){
            System.out.println("Errore di logica nello use case");
        }
           
    }
}
