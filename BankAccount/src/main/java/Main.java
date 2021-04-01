import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //final int minimumYears=18;

        byte day, month;
        int depWith=0;
        String name, surname, temp, choice="1";
        int initialBalance, year;
        boolean registrationComplete=false;
        Scanner sc = new Scanner(System.in);
        try{
            BankAccount.resumeJS();
        }catch(IOException e){
            System.err.println("***RESUME ERROR!***");
            System.err.println("Failed to read JSON file");
        }


        while(!choice.equals("0")){
            System.out.println("");
            System.out.println("\n        ╔════════════════════════════════════════════════════╗");
            System.out.println("        ║                                                    ║");
            System.out.println("        ║          «««« Unified BankManagement »»»»          ║");
            System.out.println("        ║         2018/2019 ITIS C. Zuccante, Mestre         ║");
            System.out.println("        ║                                                    ║");
            System.out.println("        ║   0. Exit                                          ║");
            System.out.println("        ║   1. Create new Bank Account                       ║");
            System.out.println("        ║   2. Deposit                                       ║");
            System.out.println("        ║   3. Withdrawal                                    ║");
            System.out.println("        ║   4. Random Transaction                            ║");
            System.out.println("        ║   5. AccountInfo                                   ║");
            System.out.println("        ║   6. Transaction List                              ║");
            System.out.println("        ║                                                    ║");
            System.out.println("        ╚════════════════════════════════════════════════════╝\n");

            System.out.print("Choose something: ");
            choice=sc.nextLine();

            switch (choice){
                case "1": {
                    registrationComplete=true;
                    System.out.println("Insert Name: ");
                    name=sc.nextLine(); name=sc.nextLine();

                    System.out.println("\nInsert Surname: ");
                    surname=sc.nextLine();

                    System.out.println("\nInsert birthday (): ");
                    System.out.println("\nDay: ");    day=sc.nextByte();
                    System.out.println("\nMonth: ");    month=sc.nextByte();
                    System.out.println("\nYear: ");    year=sc.nextInt();
                    System.out.println(LocalDate.now());
                    System.out.println(Period.between(LocalDate.of(year, month, day), LocalDate.now()).getYears()<=18);


                    if(Period.between(LocalDate.of(year, month, day), LocalDate.now()).getYears()<=18){
                        System.err.println("*ERROR!* Age doesn't comply with National Policy!");
                        registrationComplete=false;
                    }

                    System.out.print("\nInsert initial balance: ");
                    initialBalance=sc.nextInt();
                    if(initialBalance<0){
                        System.err.println("*ERROR!* Negative initial balance not allowed!");
                        registrationComplete=false;
                    }

                    if(registrationComplete){
                        new BankAccount(name, surname, LocalDate.of(year, month, day), planChooser(sc), initialBalance);
                        System.out.println("Account created correctly!");
                    }else System.err.println("*REGISTRATION ERROR* Some fields might not comply.");
                }break;

                case "2": {
                    System.out.println("Please insert deposit amount: ");
                    depWith=sc.nextInt();
                    System.out.println("Add description: ");
                    temp=sc.nextLine();
                    System.out.println("This deposit " + ((search(sc).deposit(depWith, temp)) ? " was " : " was not ") + "allowed.");

                }break;

                case "3":{
                    System.out.println("Please insert withdrawal amount: ");
                    depWith=sc.nextInt();
                    System.out.println("Add description: ");
                    temp=sc.nextLine();
                    System.out.println("This withdrawal " + ((search(sc).withdrawal(depWith, temp)) ? " was " : " was not ") + "allowed.");
                }break;

                case "4":{
                    System.out.println("Registering random transaction...");
                    search(sc).randomTransaction();
                }break;

                case "5":{
                    try{
                        accountInfo(search(sc));
                    }catch (NullPointerException e){
                        System.err.println("Couldn't find a matching account!! Check data.");
                    }

                }break;

                case "6":{
                    BankAccount tempacc = search(sc);
                    if(tempacc==null){
                        System.err.println("*ERROR!* No accounts available!");
                    }else{
                        tempacc.printTransactions();
                        System.out.println("Dump transactions to file? ");
                        temp=sc.nextLine();
                        if(yesNo(temp)){
                            tempacc.transToFile(null);
                            System.out.println("Printing...");
                        }
                    }
                }break;

                case "7":
                    try{
                        BankAccount.refreshJS();
                    }catch(IOException e){}
                break;

                case "0":{}break;

                default:{
                    System.err.println("****INPUT MISMATCH ERROR!**** Insert correct number!!");
                }break;
            }
            try {Thread.sleep(1000);} catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        }

        System.out.println("Shutting down...");

        try{
            BankAccount.refreshJS();
        }catch(IOException e){
            System.err.println("***JSON WRITE ERROR!***");
            System.err.println("Failed to write JSON file");
        }

        try {Thread.sleep(1000);} catch(InterruptedException ex) {Thread.currentThread().interrupt();}
    }

    public static BankAccount search(Scanner sc){
        String n = "0";
        while(true){
            System.out.println("Select search method: \n" +
                    "1. Name-Surname    2. Serial Number");
            n=sc.nextLine();
            switch (n){
                case "1":
                    String name, surname;
                    System.out.println("Please insert name: ");
                    name=sc.nextLine();
                    name=sc.nextLine();
                    System.out.println("Please insert surname: ");
                    surname=sc.nextLine();
                    return BankAccount.search(name, surname);
                case "2":
                    int sn;
                    System.out.println("Please insert Account Number: ");
                    sn=sc.nextInt();
                    return BankAccount.search(sn);
                default:
                    System.err.println("*INPUT MISMATCH ERROR!* Insert correct number!!");
                    break;
            }
        }

    }

    public static int planChooser(Scanner sc){
        String n;
        while(true){
            System.out.println("Select Account plan: \n" +
                    "1. Basic    2. Corporate    3. Advanced");
            n=sc.nextLine();
            if(n>=1 || n<=3)  return n;   else System.err.println("*INPUT MISMATCH ERROR!* No plans available!");
        }
    }

    public static void accountInfo(BankAccount b){
        System.out.println("");
        System.out.println("*****GENERAL ACCOUNT INFORMATION*****");
        System.out.println("Name: " + b.getName() + "       Surname: " + b.getSurname());
        System.out.println("Birthday: " + b.getBirthday() + "       Created: " + b.getCreationDate());
        System.out.println("");
        System.out.println("*****FINANCIAL INFORMATION*****");
        System.out.println("Balance: " + b.getBalance() + "       S/N: " + b.getSerial());
        System.out.println("");
    }

    public static boolean yesNo(String str){
        return (str.equals("yes") || str.equals("Yes") || str.equals("YES") || str.equals("y") || str.equals("Y"));
    }
}
