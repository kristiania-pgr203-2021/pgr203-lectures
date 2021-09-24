package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {
    private final int statusCode;
    private final HttpMessage httpMessage;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());
        
        httpMessage = new HttpMessage(socket);
        this.statusCode = Integer.parseInt(httpMessage.getStartLine().split(" ")[1]);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String headerName) {
        return httpMessage.getHeader(headerName);
    }

    public String getMessageBody() {
        return httpMessage.getMessageBody();
    }
}
