package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {
    private final int statusCode;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());
        
        String[] statusLine = readLine(socket).split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            buffer.append((char)c);
        }
        return buffer.toString();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
