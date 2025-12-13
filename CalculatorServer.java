
// ToDo follow the commands
// rmiregistry 2000
// javac CalculatorServer.java; java CalculatorServer
// java CalculatorGUI
import java.rmi.Naming;

public class CalculatorServer {

    @SuppressWarnings("UseSpecificCatch")
    public static void main(String[] args) {
        try {
            // * port 2000
            try {
                java.rmi.registry.LocateRegistry.createRegistry(2000);
            } catch (java.rmi.server.ExportException e) {
                System.out.println("Registry already running on port 2000.");
            }

            CalculatorImpl calc = new CalculatorImpl();

            // Naming.rebind("CalcService", calc);
            Naming.rebind("rmi://localhost:2000/CalcService", calc);

            System.out.println("Calculator Server is Ready...");
        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }
}