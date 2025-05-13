import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
    public static void main (String args[]){
        List<Process> fcfsList = new ArrayList<>();
        // Sample processes (could load from input instead)
                                // pid, arrival, burst, ioStart, ioDuration, priority
        fcfsList.add(new Process(1, 0, 10, 2, 3, 1));
        fcfsList.add(new Process(2, 2, 6, 1, 2, 2));
        fcfsList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler fcfs = new FCFS();
        SimulationEngine engine = new SimulationEngine(fcfs);

        System.out.println("\nTesting FCFS scheduler using preset inputs...");
        engine.start(fcfsList);

        fcfsList.clear();

            //Generates random values for 20 processes
        Random rand = new Random();
        
        for (int i = 0; i < 20; i++) {
            int aTime = rand.nextInt(30);            // Arrival time between 0–29
            int burst = rand.nextInt(5) + 5;         // Burst time between 5–9
            int ioStart = rand.nextInt(burst - 1);   // I/O start between 0 and burst-2
            int ioDur = rand.nextInt(3) + 1;         // I/O duration between 1–3
            int priority = rand.nextInt(5) + 1;      // Priority between 1–5

            fcfsList.add(new Process(i, aTime, burst, ioStart, ioDur, priority));
        }

        System.out.println("\nTesting FCFS scheduler using randomized inputs...");
        SimulationEngine engine2 = new SimulationEngine(fcfs);
        engine2.start(fcfsList);



        System.out.println("\n");


        List<Process> rrList = new ArrayList<>();
        rrList.add(new Process(1, 0, 10, 2, 3, 1));
        rrList.add(new Process(2, 2, 6, 1, 2, 2));
        rrList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler rr = new RoundRobin();
        SimulationEngine engine3 = new SimulationEngine(rr);
        
        System.out.println("Testing RR scheduler using preset inputs...");
        engine3.start(rrList);

        rrList.clear();

            //Generates random values for 20 processes
        for (int i = 0; i < 20; i++) {
            int aTime = rand.nextInt(30);            // Arrival time between 0–29
            int burst = rand.nextInt(5) + 5;         // Burst time between 5–9
            int ioStart = rand.nextInt(burst - 1);   // I/O start between 0 and burst-2
            int ioDur = rand.nextInt(3) + 1;         // I/O duration between 1–3
            int priority = rand.nextInt(5) + 1;      // Priority between 1–5

            rrList.add(new Process(i, aTime, burst, ioStart, ioDur, priority));
        }

        System.out.println("\nTesting RR scheduler using randomized inputs...");
        SimulationEngine engine4 = new SimulationEngine(rr);
        engine4.start(rrList);


        System.out.println("\n");


        List<Process> priList = new ArrayList<>();
        priList.add(new Process(1, 0, 10, 2, 3, 5));
        priList.add(new Process(2, 2, 6, 1, 2, 9));
        priList.add(new Process(3, 4, 8, 3, 1, 3));

        Scheduler priScheduler = new Priority();
        SimulationEngine engine5 = new SimulationEngine(priScheduler);

        System.out.println("\nTesting Priority scheduler using preset inputs...");
        engine5.start(priList);
        
        priList.clear();

            //Generates random values for 20 processes
        for (int i = 0; i < 20; i++) {
            int aTime = rand.nextInt(30);            // Arrival time between 0–29
            int burst = rand.nextInt(5) + 5;         // Burst time between 5–9
            int ioStart = rand.nextInt(burst - 1);   // I/O start between 0 and burst-2
            int ioDur = rand.nextInt(3) + 1;         // I/O duration between 1–3
            int priority = rand.nextInt(20) + 1;      // Priority between 1–20

            priList.add(new Process(i, aTime, burst, ioStart, ioDur, priority));
        }

        System.out.println("Testing Priority scheduler using randomized inputs...");
        SimulationEngine engine6 = new SimulationEngine(priScheduler);
        engine6.start(priList);

    }

}
