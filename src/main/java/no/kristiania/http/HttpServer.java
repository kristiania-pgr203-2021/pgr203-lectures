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
            
            int questionPos = requestTarget.indexOf('?');
            String absolutePath = questionPos != -1 ? requestTarget.substring(0, questionPos) : requestTarget;
            String query = questionPos != -1 ? requestTarget.substring(questionPos+1) : null;
            
            if (absolutePath.equals("/hello")) {
                String username = "world";
                if (query != null) {
                    username = query.split("=")[1];
                }
                String responseBody = "<p>Hello " + username + "</p>";

                String responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseBody.length() + "\r\n" +
                        "\r\n" +
                        responseBody;
                clientSocket.getOutputStream().write(responseMessage.getBytes());
            } else if (contentRoot != null && Files.exists(contentRoot.resolve(absolutePath.substring(1)))) {
                String responseBody = Files.readString(contentRoot.resolve(absolutePath.substring(1)));
                String contentType = "text/plain";
                if (absolutePath.endsWith(".html")) {
                    contentType = "text/html";
                }
                
                String responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseBody.length() + "\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "\r\n" +
                        responseBody;
                clientSocket.getOutputStream().write(responseMessage.getBytes());
            } else {
                String responseBody = "File not found: " + absolutePath;

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

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setContentRoot(Path contentRoot) {
        this.contentRoot = contentRoot;
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(8080);
        server.setContentRoot(Paths.get("."));
    }
}
