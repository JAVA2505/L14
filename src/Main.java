import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        //server
        try {
          ServerSocket server =
              new ServerSocket(8899);
          System.out.println("Server created;");
          while(true){
            System.out.println("Server ready to get connections...");
            Socket s = server.accept();
            ObjectInputStream ois =
                new ObjectInputStream(s.getInputStream());
            String message = (String)ois.readObject();
            ois.close();
            s.close();
            System.out.println("SERVER: "+message);
            if("exit".equals(message)){
              System.out.println("Server will exit now...");
              break;
            }
          }
          server.close();
          System.out.println("Server closed;");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      }).start();

      //client
    try {
      while(true) {
        System.out.println("CLIENT: Enter message:");
        String message = new Scanner(System.in).nextLine();
        Socket s = new Socket("localhost", 8899);
        ObjectOutputStream oos =
                new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(message);
        oos.close();
        s.close();
        if("exit".equals(message)){
          System.out.println("Client closed.");
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
