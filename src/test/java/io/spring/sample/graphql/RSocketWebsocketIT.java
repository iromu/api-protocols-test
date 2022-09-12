package io.spring.sample.graphql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("rsocket-over-websocket")
public class RSocketWebsocketIT {
    private static RSocketRequester requester;

    @Value("ws://localhost:${local.server.port}${spring.rsocket.server.mapping-path}")
    private String baseUrl;

    @BeforeEach
    void setUp(@Autowired RSocketRequester.Builder builder) {
        URI uri = URI.create(baseUrl);
        requester = builder.websocket(uri);
    }

    @Test
    public void greetingMono() {
        Mono<String> result = requester
                .route("greetingMono")
                .retrieveMono(String.class);

        StepVerifier.create(result)
                .expectNext("Hello!")
                .verifyComplete();
    }

    @AfterEach
    public void tearDown() {
        requester.dispose();
    }

}
