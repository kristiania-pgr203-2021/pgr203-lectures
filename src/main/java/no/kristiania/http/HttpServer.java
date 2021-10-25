package no.kristiania.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final HashMap<String, HttpController> controllers = new HashMap<>();

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);

        new Thread(this::handleClients).start();
    }

    private void handleClients() {
        try {
            while (true) {
                handleClient();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException, SQLException {
        Socket clientSocket = serverSocket.accept();

        HttpMessage httpMessage = new HttpMessage(clientSocket);
        String[] requestLine = httpMessage.startLine.split(" ");
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

        if (controllers.containsKey(fileTarget)) {
            HttpMessage response = controllers.get(fileTarget).handle(httpMessage);
            response.write(clientSocket);
        } else if (fileTarget.equals("/hello")) {
            String yourName = "world";
            if (query != null) {
                Map<String, String> queryMap = HttpMessage.parseRequestParameters(query);
                yourName = queryMap.get("lastName") + ", " + queryMap.get("firstName");
            }
            String responseText = "<p>Hello " + yourName + "</p>";

            writeOkResponse(clientSocket, responseText, "text/html");
        } else {
            InputStream fileResource = getClass().getResourceAsStream(fileTarget);
            if (fileResource != null) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                fileResource.transferTo(buffer);
                String responseText = buffer.toString();

                String contentType = "text/plain";
                if (requestTarget.endsWith(".html")) {
                    contentType = "text/html";
                } else if (requestTarget.endsWith(".css")) {
                    contentType = "text/css";
                }
                writeOkResponse(clientSocket, responseText, contentType);
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

    private void writeOkResponse(Socket clientSocket, String responseText, String contentType) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseText.length() + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseText;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void addController(String path, HttpController controller) {
        controllers.put(path, controller);
    }
}
