package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpClient {

    private final int statusCode;
    private final HashMap<String, String> headerFields = new HashMap<>();
    private String messageBody;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);
        socket.getOutputStream().write(("GET " + requestTarget + " HTTP/1.1\r\nHost: " + host + "\r\n\r\n").getBytes());
        
        String[] statusLine = readLine(socket.getInputStream()).split(" ");
        statusCode = Integer.parseInt(statusLine[1]);
        
        String headerField;
        while (!(headerField = readLine(socket.getInputStream())).isBlank()) {
            int colonPos = headerField.indexOf(':');
            headerFields.put(headerField.substring(0, colonPos), headerField.substring(colonPos+1).trim());
        }
        this.messageBody = readBytes(socket.getInputStream(), getContentLength());
    }

    private String readBytes(InputStream in, int contentLength) throws IOException {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            result.append((char)in.read());
        }
        return result.toString();
    }

    private String readLine(InputStream in) throws IOException {
        StringBuilder result = new StringBuilder();
        int c;
        while ((c = in.read()) != -1 && c != '\r') {
            result.append((char)c);
        }
        if (c == '\r') {
            in.read();
        }
        return result.toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String fieldName) {
        return headerFields.get(fieldName);
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }
    
    public String getMessageBody() {
        return messageBody;
    }

    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client.getMessageBody());
    }

}
