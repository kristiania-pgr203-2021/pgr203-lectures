package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {
    private final int statusCode;
    private HttpMessage httpMessage = new HttpMessage();

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());
        
        String[] statusLine = HttpMessage.readLine(socket).split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);

        String headerLine;
        while (!(headerLine = HttpMessage.readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos+1).trim();
            httpMessage.headerFields.put(headerField, headerValue);
        }

        httpMessage.messageBody = httpMessage.readBytes(socket, getContentLength());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String headerName) {
        return httpMessage.headerFields.get(headerName);
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getMessageBody() {
        return httpMessage.messageBody;
    }
}
