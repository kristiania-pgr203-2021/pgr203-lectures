package no.kristiania.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    public String startLine;
    public final Map<String, String> headerFields = new HashMap<>();
    public String messageBody;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readHeaders(socket);
        if (headerFields.containsKey("Content-Length")) {
            messageBody = HttpMessage.readBytes(socket, getContentLength());
        }
    }

    public HttpMessage(String startLine, String messageBody) {
        this.startLine = startLine;
        headerFields.putAll(Map.of(
                "Content-Length", String.valueOf(messageBody.length()), 
                "Connection", "close"
        ));
        this.messageBody = messageBody;
    }

    public HttpMessage(String startLine) {
        this.startLine = startLine;
        headerFields.put("Connection", "close");
    }

    public static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> queryMap = new HashMap<>();
        for (String queryParameter : query.split("&")) {
            int equalsPos = queryParameter.indexOf('=');
            String parameterName = queryParameter.substring(0, equalsPos);
            String parameterValue = queryParameter.substring(equalsPos+1);
            queryMap.put(parameterName, parameterValue);
        }
        return queryMap;
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }

    static String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.append((char)socket.getInputStream().read());
        }
        return buffer.toString();
    }

    private void readHeaders(Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = HttpMessage.readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos+1).trim();
            headerFields.put(headerField, headerValue);
        }
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            buffer.append((char)c);
        }
        int expectedNewline = socket.getInputStream().read();
        assert expectedNewline == '\n';
        return buffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(startLine.getBytes());
        outputStream.write("\r\n".getBytes());
        for (String headerName : headerFields.keySet()) {
            outputStream.write(headerName.getBytes());
            outputStream.write(": ".getBytes());
            outputStream.write(headerFields.get(headerName).getBytes());
            outputStream.write("\r\n".getBytes());
        }
        outputStream.write("\r\n".getBytes());
        if (messageBody != null) {
            outputStream.write(messageBody.getBytes());
        }
    }
}
