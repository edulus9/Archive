public interface BATypes {

    int BASIC = 0;
    int CORPORATE = 1;
    int ADVANCED = 2;

    static double WithdrawLimit(BankAccount b){
        switch(b.getType()){
            case BASIC: return Basic.WITHDRAWLIMIT;
            case CORPORATE: return Corporate.WITHDRAWLIMIT;
            case ADVANCED: return Advanced.WITHDRAWLIMIT;
        }
        return 0;
    }

    static double DepositLimit(BankAccount b){
        switch(b.getType()){
            case BASIC: return Basic.DEPOSITLIMIT;
            case CORPORATE: return Corporate.DEPOSITLIMIT;
            case ADVANCED: return Advanced.DEPOSITLIMIT;
        }
        return 0;
    }

    interface Basic{
        double WITHDRAWLIMIT=200.00d;
        double DEPOSITLIMIT=200.00d;
        double BALANCELIMIT=2000.00d;
    }
    interface Corporate{
        double WITHDRAWLIMIT=200.00d;
        double DEPOSITLIMIT=200.00d;
        double BALANCELIMIT=2000.00d;
    }
    interface Advanced{
        double WITHDRAWLIMIT=200.00d;
        double DEPOSITLIMIT=200.00d;
        double BALACNELIMIT=2000.00d;
    }
}