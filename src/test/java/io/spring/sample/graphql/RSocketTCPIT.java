package io.spring.sample.graphql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RSocketTCPIT {
    private static RSocketRequester requester;


    @BeforeEach
    void setUp(@Autowired RSocketRequester.Builder builder,
               @Value("${spring.rsocket.server.port}") Integer port) {
        requester = builder.tcp("localhost", port);
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
