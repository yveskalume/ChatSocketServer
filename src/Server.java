import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void starServer() {
        try {
            while (!serverSocket.isClosed()) {
                // Nouveau client conneté
                Socket socket = serverSocket.accept();

                System.out.println("Un nouveau client s'est connecté!");

                ClientHandler clientHandler = new ClientHandler(socket);

                // Un nouveau thread pour le nouvel utilisateur
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        ServerSocket serverSocket = new ServerSocket(1234, 50, address);
        Server server = new Server(serverSocket);
        server.starServer();
    }

}
