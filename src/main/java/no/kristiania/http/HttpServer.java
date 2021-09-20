package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {

    private final ServerSocket serverSocket;
    private Path contentRoot;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(this::handleClient).start();
    }

    private void handleClient() {
        try {
            Socket clientSocket = serverSocket.accept();

            String[] requestLine = HttpClient.readLine(clientSocket).split(" ");
            String requestTarget = requestLine[1];
            
            if (requestTarget.equals("/hello")) {
                String responseBody = "<p>Hello world</p>";

                String responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseBody.length() + "\r\n" +
                        "\r\n" +
                        responseBody;
                clientSocket.getOutputStream().write(responseMessage.getBytes());
                return;
            }
            
            if (contentRoot != null && Files.exists(contentRoot.resolve(requestTarget))) {
                String responseBody = Files.readString(contentRoot.resolve(requestTarget));

                String responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseBody.length() + "\r\n" +
                        "\r\n" +
                        responseBody;
                clientSocket.getOutputStream().write(responseMessage.getBytes());
                return;
            }
            

            String responseBody = "File not found: " + requestTarget;

            String responseMessage = "HTTP/1.1 404 Not found\r\n" +
                    "Content-Length: " + responseBody.length() + "\r\n" +
                    "\r\n" +
                    responseBody;
            clientSocket.getOutputStream().write(responseMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(8080);
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setContentRoot(Path contentRoot) {
        this.contentRoot = contentRoot;
    }
}
