package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpClient {

    private final int statusCode;
    private final HashMap<String, String> headerFields = new HashMap<>();

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

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("httpbin.org", 80);
        socket.getOutputStream().write("GET /html HTTP/1.1\r\nHost: httpbin.org\r\n\r\n".getBytes());

        InputStream in = socket.getInputStream();
        int c;
        while ((c = in.read()) != -1) {
            System.out.print((char)c);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String fieldName) {
        return headerFields.get(fieldName);
    }

    public int getContentLength() {
        return 0;
    }
}
