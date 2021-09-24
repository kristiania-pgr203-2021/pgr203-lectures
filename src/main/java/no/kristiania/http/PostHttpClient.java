package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class PostHttpClient {
    private final HttpMessage httpMessage;
    private final int statusCode;

    public PostHttpClient(String host, int port, String requestTarget, String messageBody) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Content-Length: " + messageBody.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                messageBody;
        socket.getOutputStream().write(request.getBytes());

        httpMessage = new HttpMessage(socket);
        this.statusCode = Integer.parseInt(httpMessage.getStartLine().split(" ")[1]);
    }

    public int getResponseCode() {
        return statusCode;
    }
}
