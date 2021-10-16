package no.kristiania.http;

public interface HttpController {
    HttpMessage handle(HttpMessage request);
}
