package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);

        new Thread(this::handleClients).start();
    }

    private void handleClients() {
        try {
            Socket clientSocket = serverSocket.accept();
            
            String[] requestLine = HttpClient.readLine(clientSocket).split(" ");
            String requestTarget = requestLine[1];
            String responseText = "File not found: " + requestTarget;
            
            String response = "HTTP/1.1 404 Not found\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1962);

        Socket clientSocket = serverSocket.accept();
        
        String html = "Hallo der";
        String contentType = "text/html";

        String response = "HTTP/1.1 200 Det gikk greit\r\n" +
                "Content-Length: " + (html.length()) + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                html;
        
        clientSocket.getOutputStream().write(response.getBytes());


    }
    
}
