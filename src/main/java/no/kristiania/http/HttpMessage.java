package no.kristiania.http;

import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    final Map<String, String> headerFields = new HashMap<>();

    public String messageBody;
}
