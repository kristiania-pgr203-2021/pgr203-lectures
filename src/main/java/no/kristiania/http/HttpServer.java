package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            } else if (contentRoot != null && Files.exists(contentRoot.resolve(requestTarget.substring(1)))) {
                String responseBody = Files.readString(contentRoot.resolve(requestTarget.substring(1)));
                String contentType = "text/plain";
                if (requestTarget.endsWith(".html")) {
                    contentType = "text/html";
                }
                
                String responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseBody.length() + "\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "\r\n" +
                        responseBody;
                clientSocket.getOutputStream().write(responseMessage.getBytes());
            } else {
                String responseBody = "File not found: " + requestTarget;

                String responseMessage = "HTTP/1.1 404 Not found\r\n" +
                        "Content-Length: " + responseBody.length() + "\r\n" +
                        "\r\n" +
                        responseBody;
                clientSocket.getOutputStream().write(responseMessage.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(8080);
        server.setContentRoot(Paths.get("."));
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setContentRoot(Path contentRoot) {
        this.contentRoot = contentRoot;
    }
}
