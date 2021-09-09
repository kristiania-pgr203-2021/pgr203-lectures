package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Thread thread;
    private Path contentRoot = Paths.get(".");

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        thread = new Thread(this::handleConnections);
        thread.start();
    }

    private void handleConnections() {
        try {
            Socket clientSocket = serverSocket.accept();
            String[] requestLine = HttpMessage.readLine(clientSocket).split(" ", 3);
            String requestTarget = requestLine[1];

            String responseMessage;
            if (requestTarget.equals("/hello")) {
                String messageContent = "Hello world";

                responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + messageContent.length() + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n" +
                        messageContent;
            } else {
                Path targetPath = contentRoot.resolve(requestTarget.substring(1));
                if (Files.exists(targetPath)) {
                    String contentType = "text/plain";
                    if (targetPath.toString().endsWith(".html")) {
                        contentType = "text/html";
                    }
                    responseMessage = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: " + Files.size(targetPath) + "\r\n" +
                            "Connection: close\r\n" +
                            "Content-Type: " + contentType + "\r\n" +
                            "\r\n" +
                            new String(Files.readAllBytes(targetPath));
                } else {
                    String messageContent = "File not found " + requestTarget;
                    responseMessage =
                            "HTTP/1.1 404 Not Found\r\n" +
                                    "Content-Length: " + messageContent.length() + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n" +
                                    messageContent
                    ;
                }
            }
            clientSocket.getOutputStream().write(responseMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setRoot(Path contentRoot) {
        this.contentRoot = contentRoot;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer httpServer = new HttpServer(8080);
        httpServer.setRoot(Paths.get("target/example/"));
        httpServer.join();
    }

    private void join() throws InterruptedException {
        thread.join();
    }
}
