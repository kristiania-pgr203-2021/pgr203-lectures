package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {

    private final ServerSocket serverSocket;
    private Path contentRoot = Paths.get(".");

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        new Thread(this::handleConnections).start();
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
                    responseMessage = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: " + Files.size(targetPath) + "\r\n" +
                            "Connection: close\r\n" +
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

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        Socket socket = serverSocket.accept();
        
        String messageContent = "Hello <strong>world</strong>";
        
        String responseMessage =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + messageContent.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                messageContent
                ;

        socket.getOutputStream().write(responseMessage.getBytes());
        socket.getOutputStream().flush();

        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }
        
        socket.close();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setRoot(Path contentRoot) {
        this.contentRoot = contentRoot;
    }
}
