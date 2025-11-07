package com.odde.atddv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leeonky.dal.DAL;
import com.github.leeonky.interpreter.InterpreterException;
import com.github.leeonky.interpreter.SyntaxException;
import lombok.SneakyThrows;
import org.mockserver.client.MockServerClient;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mockserver.serialization.HttpRequestSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.leeonky.dal.Assertions.expect;
import static org.mockserver.model.HttpRequest.request;

@Component
public class DALMockServer {

    private final Map<String, ResponseBuilder> allExpectations = Collections.synchronizedMap(new LinkedHashMap<>());
    private final Map<String, AtomicInteger> responseTimes = Collections.synchronizedMap(new HashMap<>());
    private final Queue<HttpRequest> allRequests = new ConcurrentLinkedDeque<>();
    private final MockServerLogger mockServerLogger = new MockServerLogger(DALMockServer.class);
    private final HttpRequestSerializer requestSerializer = new HttpRequestSerializer(mockServerLogger);
    @Autowired
    private MockServerClient mockServerClient;

    public void clear() {
        allExpectations.clear();
        allRequests.clear();
        responseTimes.clear();
    }

    public List<HttpRequest> requests() {
        return new ArrayList<>(allRequests);
    }

    public void mock(Map<String, ResponseBuilder> expectationResponses) {
        allExpectations.putAll(expectationResponses);
        for (String expectation : expectationResponses.keySet()) {
            responseTimes.put(expectation, new AtomicInteger(0));
        }
        mockServerClient.when(request()).respond(httpRequest -> {
            allRequests.add(httpRequest);
            List<Unexpectation> unexpectations = new ArrayList<>();
            List<Map.Entry<String, ResponseBuilder>> entries = new ArrayList<>(allExpectations.entrySet());
            Collections.reverse(entries);
            for (Map.Entry<String, ResponseBuilder> entry : entries) {
                String expectation = entry.getKey();
                ResponseBuilder response = entry.getValue();
                try {
                    DAL.dal("MockD").evaluate(httpRequest, expectation);
                    if (response.times > 0) {
                        int currentTime = responseTimes.get(expectation).incrementAndGet();
                        if (currentTime > response.times) {
                            unexpectations.add(new Unexpectation(expectation, "times %s more than %s".formatted(currentTime, response.times)));
                            continue;
                        }
                    }
                    HttpResponse httpResponse = HttpResponse.response().withStatusCode(response.code)
                            .withContentType(MediaType.APPLICATION_JSON);
                    response.giveBody(httpResponse);
                    response.buildHeaders().forEach(httpResponse::withHeader);
                    return httpResponse;
                } catch (SyntaxException e) {
                    return HttpResponse.response().withStatusCode(500).withBody(e.getMessage() + "\n\n" + e.show(expectation));
                } catch (InterpreterException ex) {
                    unexpectations.add(new Unexpectation(expectation, ex));
                }
            }
            String message = buildMessage(httpRequest, unexpectations);
//            System.out.println(message);
            return HttpResponse.response(message).withStatusCode(404);
        });
    }

    public void mockWithTimes(Map<String, List<String>> expectationResponses) {
        for (var expectation : expectationResponses.entrySet()) {
            for (var response : expectation.getValue()) {
                mockServerClient.when(request(), Times.exactly(1)).respond(httpRequest -> {
                    Throwable lastException = null;
                    String expectedRequest = expectation.getKey();
                    try {
                        expect(httpRequest).should(expectedRequest);
                        return HttpResponse.response(response).withContentType(MediaType.APPLICATION_JSON);
                    } catch (SyntaxException e) {
                        return HttpResponse.response().withStatusCode(500).withBody(e.getMessage());
                    } catch (Throwable throwable) {
                        lastException = throwable;
                    }
                    return HttpResponse.response(lastException != null ? lastException.getMessage() : "No expectation error").withStatusCode(404);
                });
            }
        }
    }

    private String buildMessage(HttpRequest httpRequest, List<Unexpectation> unexpectations) {
        StringBuilder message = new StringBuilder();
        message.append("Unexpected request:\n")
                .append(requestSerializer.serialize(httpRequest))
                .append("\n")
                .append("Expectations:");
        unexpectations.stream().map(Unexpectation::message).forEach(message::append);
        return message.toString();
    }

    public static final class Unexpectation {
        public final String expectation;
        private final String message;

        public Unexpectation(String expectation, InterpreterException ex) {
            this(expectation, "\n----------------------------------------------------------\n" + ex.show(expectation) + "\n" + ex.getMessage());
        }

        public Unexpectation(String expectation, String message) {
            this.expectation = expectation;
            this.message = "expectation\n" + message;
        }

        public String message() {
            return message;
        }
    }

    public static class ResponseBuilder {
        public int code;
        public Object body;
        public int times;
        public Map<String, Object> headers = new LinkedHashMap<>();

        public List<Header> buildHeaders() {
            return headers.entrySet().stream().map(e -> {
                if (e.getValue() instanceof List)
                    return new Header(e.getKey(), ((List<?>) e.getValue()).stream().map(String::valueOf).toArray(String[]::new));
                return new Header(e.getKey(), String.valueOf(e.getValue()));
            }).toList();
        }

        @SneakyThrows
        public void giveBody(HttpResponse httpResponse) {
            if (body instanceof List) {
                List<Byte> bytes = (List<Byte>) body;
                byte[] binary = new byte[bytes.size()];
                for (int i = 0; i < bytes.size(); i++)
                    binary[i] = bytes.get(i);
                httpResponse.withBody(binary);
            } else if (body instanceof String) {
                httpResponse.withBody((String) body);
            } else {
                httpResponse.withBody(new ObjectMapper().writeValueAsString(body));
            }
        }
    }
}
