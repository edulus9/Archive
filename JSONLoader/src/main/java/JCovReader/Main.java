package JCovReader;

import JCovReader.JCOVReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        long millis=System.currentTimeMillis();
        Scanner sc = new Scanner(System.in);

        System.out.println("Insert URL (\"-\" for default): ");
        String URL=sc.nextLine();

        if(URL.equals("-")) URL="https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-regioni.json";

        JCOVReader jcr = new JCOVReader(URL);
        System.out.println("Insert region code: ");
        jcr.updateData(Integer.parseInt(sc.nextLine()));

        System.out.println("1. Current day \n2.All");
        switch(sc.nextLine()){
            case "1":
                System.out.println(jcr.getCAL().get(jcr.getCAL().size()-1));
                break;

            case "2":
                for (int i = 0; i < jcr.getCAL().size(); i++) {
                    System.out.println(jcr.getCAL().get(i));
                }
                break;
            default:
                System.out.println("ERROR. Insert number in range 1-2.");
                break;
        }
        System.out.println("Execution ended in " + (System.currentTimeMillis()-millis) + "ms.");
    }
}
