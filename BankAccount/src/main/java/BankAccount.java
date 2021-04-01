import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.*;

public class BankAccount implements BATypes{

    public static double totalAccounts;

    private double balance;
    private String ownerName;
    private String ownerSurname;
    private LocalDate ownerBirth;
    private LocalDateTime creationDate;
    private int serial;
    private int type;

    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gs = builder.create();


    Random rnd = new Random();
    private List<Transaction> transactionList = new ArrayList<>();
    private static TypeToken<ArrayList<BankAccount>> BARR = new TypeToken<ArrayList<BankAccount>>() {};
    private static ArrayList<BankAccount> bankAccounts = new ArrayList<>();

    BankAccount(String name, String surname, LocalDate birth, int kind){

        ownerName=name;
        ownerSurname=surname;
        ownerBirth = birth;
        creationDate=LocalDateTime.now();
        balance = 0;
        serial=SNGenerator();
        type=kind;
        bankAccounts.add(this);
        totalAccounts++;
    }

    BankAccount(String name, String surname, LocalDate birth, int kind, double initialBalance){
        this(name, surname, birth, kind);
        balance=initialBalance;
    }

    private int SNGenerator(){
        int n=rnd.nextInt();
        if (n < 0)  n=-n;
        while (n==0)    n=rnd.nextInt();

        for(int i=0; i<bankAccounts.size(); i++){                       //checking that other serials are different from the one that is being assigned
            if (bankAccounts.get(i).serial==n){
                i=0;
                n=rnd.nextInt();
                if (n < 0)  n=-n;
                while (n==0)    n=rnd.nextInt();
            }

        }
        return n;
    }

    public String getName(){
        return this.ownerName;
    }

    public String getSurname(){
        return this.ownerSurname;
    }

    public LocalDate getBirthday(){
        return this.ownerBirth;
    }

    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }

    public int getSerial(){
        return this.serial;
    }

    public void printTransactions(){
        for(Transaction t : this.transactionList){
            System.out.println("Date: " + t.getDate());
            System.out.println("Transaction amount: " + t.getAmount());
            System.out.println("Description: " + t.getDescription());
            System.out.println("Allowed: " + t.Allowed());
            System.out.println("");

        }
    }

    public double getBalance(){
        return this.balance;
    }

    public int getType(){
        return this.type;
    }

    public List<Transaction> getTransactionList(){
        return this.transactionList;
    }

    public void transToFile(String fileName){
        if (fileName == null){
            fileName="TransactionList_" + this.getName() + "_" + this.getSurname() + ".txt";
        }
        if(!fileName.substring(fileName.length()-4).equals(".txt")) {
            System.err.println("****ERROR!! FILE FORMAT NOT SUPPORTED!!****");
        }else{
            try {
                FileWriter w = new FileWriter(fileName);
                w.write("**** Transaction List of " + this.getName() + " " + this.getSurname() + " ****" + "\n");
                w.write("**** Account Number: " + this.getSerial() + " ****" + "\n" + "\n");
                for (Transaction t : this.transactionList) {
                    w.write("Date: " + t.getDate() + "\n");
                    w.write("Transaction amount: " + t.getAmount() + "\n");
                    w.write("Description: " + t.getDescription() + "\n");
                    w.write("Allowed: " + t.Allowed() + "\n");
                    w.write("\n");
                }
                w.write("\n");
                w.write("**** End of list ****");
                w.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public boolean withdrawal(double amount, String description) {
        if (amount>0) {
            if (amount < BATypes.WithdrawLimit(this)) {
                if (amount < this.balance) {
                    transactionList.add(new Transaction(-amount, description, LocalDateTime.now(), true));
                    balance -= amount;
                    return true;
                }
            }
            transactionList.add(new Transaction(-amount, description, LocalDateTime.now(),false));
            return false;
        }else{
            if (-amount < BATypes.WithdrawLimit(this)) {
                if (-amount < this.balance) {
                    transactionList.add(new Transaction(amount, description, LocalDateTime.now(), true));
                    balance += amount;
                    return true;
                }
            }
            transactionList.add(new Transaction(amount, description, LocalDateTime.now(),false));
            return false;
        }
    }

    public boolean deposit(double amount, String description){
        if (amount>0) {
            if (amount < BATypes.WithdrawLimit(this)) {
                transactionList.add(new Transaction(amount, description, LocalDateTime.now(), true));
                balance += amount;
                return true;
            }
            transactionList.add(new Transaction(amount, description, LocalDateTime.now(),false));
            return false;
        }else{
            if (-amount < BATypes.WithdrawLimit(this)) {
                transactionList.add(new Transaction(-amount, description, LocalDateTime.now(), true));
                balance -= amount;
                return true;

            }
            transactionList.add(new Transaction(-amount, description, LocalDateTime.now(),false));
            return false;
        }
    }


    public void randomTransaction(){
        switch(rnd.nextInt(2)){
            case 0: this.withdrawal(rnd.nextInt(100), "Random Withdrawal");  break;
            case 1: this.deposit(rnd.nextInt(100), "Random Deposit");  break;
        }

    }

    public static BankAccount search(String name, String surname){
        for (BankAccount B : bankAccounts) {
            if(B.getSurname().equals(surname) && B.getName().equals(name)) return B;
        }
        return null;
    }

    public static BankAccount search(int serial){
        for (BankAccount B : bankAccounts) {
            if(B.getSerial()==serial) return B;
        }

        return null;
    }

    public static ArrayList<BankAccount> search(LocalDateTime creationDate){
        ArrayList<BankAccount> temp = new ArrayList<>();
        for (BankAccount B : bankAccounts) {
            if(B.getCreationDate().equals(creationDate)) temp.add(B);
        }
        return temp;
    }

    public static ArrayList<BankAccount> typeSearch(int type){
        ArrayList<BankAccount> temp = new ArrayList<>();
        for (BankAccount B :bankAccounts) {
            if(B.getType() == type) temp.add(B);
        }
        return temp;
    }


    public static void refreshJS() throws IOException{
        try{
            FileWriter fw = new FileWriter("BankData.json");
            fw.write(gs.toJson(bankAccounts, BARR.getType()));
            fw.close();
        }catch(IOException e){
            throw(e);
        }
    }

    public static void resumeJS() throws IOException{
        FileReader fr = new FileReader("BankData.json");
        bankAccounts=gs.fromJson(fr, BARR.getType());
        fr.close();
    }


    public class Transaction{
        String description;
        double amount;
        LocalDateTime date;
        boolean wasAllowed;

        Transaction(double am, String desc, LocalDateTime date, boolean allowed){
            this.amount=am;
            this.description=desc;
            this.wasAllowed=allowed;
            this.date=date;

        }

        public String getDescription(){
            return this.description;
        }

        public double getAmount(){
            return this.amount;
        }

        public LocalDateTime getDate(){
            return this.date;
        }

        public boolean wasItAllowed(){
            return this.wasAllowed;
        }

        public String Allowed(){
            if(wasAllowed){
                return "Yes";
            }
            return "No";
        }
    }
}