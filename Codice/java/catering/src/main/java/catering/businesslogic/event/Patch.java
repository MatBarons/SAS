package catering.businesslogic.event;

import catering.businesslogic.event.Enumerations.PatchType;

public class Patch {

    private PatchType type;
    private boolean approved;

    public Patch(PatchType type){
        this.type = type;
        this.approved = false;
    }

    public boolean isApproved(){
        return approved;
    }

    public void approve(){
        this.approved = true;
    }
}
