package rental;

public enum County {
    B ("B"),
    AB ("AB"),
    AG ("AG"),
    AR ("AR"),
    BC ("BC"),
    BH ("BH"),
    BN ("BN"),
    BR ("BR"),
    BT ("BT"),
    BV ("BV"),
    BZ ("BZ"),
    CJ ("CJ"),
    CL ("CL"),
    CS ("CS"),
    CT ("CT"),
    CV ("CV"),
    DB ("DB"),
    DJ ("DJ"),
    GJ ("GJ"),
    GL ("GL"),
    GR ("GR"),
    HD ("HD"),
    HR ("HR"),
    IF ("IF"),
    IL ("IL"),
    IS ("IS"),
    MH ("MH"),
    MM ("MM"),
    MS ("MS"),
    NT ("NT"),
    OT ("OT"),
    PH ("PH"),
    SB ("SB"),
    SJ ("SJ"),
    SM ("SM"),
    SV ("SV"),
    TL ("TL"),
    TM ("TM"),
    TR ("TR"),
    VL ("VL"),
    VN ("VN"),
    VS ("VS");

    private final String message;

    County(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static void printCounties(){
        for(County c : County.values()){
            System.out.println(c  + c.getMessage());
        }
    }
}
