package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private final ServerSocket serverSocket;
    private Path rootDirectory;

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);

        new Thread(this::handleClients).start();
    }

    private void handleClients() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        String[] requestLine = HttpClient.readLine(clientSocket).split(" ");
        String requestTarget = requestLine[1];

        int questionPos = requestTarget.indexOf('?');
        String fileTarget;
        String query = null;
        if (questionPos != -1) {
            fileTarget = requestTarget.substring(0, questionPos);
            query = requestTarget.substring(questionPos+1);
        } else {
            fileTarget = requestTarget;
        }

        if (fileTarget.equals("/hello")) {
            String yourName = "world";
            
            Map<String, String> parameters = parseQuery(query);
            
            if (!parameters.isEmpty()){
                yourName = parameters.get("lastName") + ", " + parameters.get("firstName");
            }
            String responseText = "<p>Hello " + yourName + "</p>";

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());
        } else {
            if (rootDirectory != null && Files.exists(rootDirectory.resolve(fileTarget.substring(1)))) {
                String responseText = Files.readString(rootDirectory.resolve(fileTarget.substring(1)));

                String contentType = "text/plain";
                if (requestTarget.endsWith(".html")) {
                    contentType = "text/html";
                }
                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseText.length() + "\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n" +
                        responseText;
                clientSocket.getOutputStream().write(response.getBytes());
                return;
            }
            
            
            
            String responseText = "File not found: " + requestTarget;

            String response = "HTTP/1.1 404 Not found\r\n" +
                    "Content-Length: " + responseText.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    responseText;
            clientSocket.getOutputStream().write(response.getBytes());
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> parameters = new HashMap<>();
        if (query != null) {
            for (String parameter : query.split("&")) {
                int equalsPos = parameter.indexOf('=');
                String name = parameter.substring(0, equalsPos);
                String value = parameter.substring(equalsPos+1);
                parameters.put(name, value);
            }
        }
        return parameters;
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(1962);
        httpServer.setRoot(Paths.get("src/main/resources"));
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setRoot(Path rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
}
