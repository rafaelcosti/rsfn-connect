package br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway;

import com.example.rsfnclient.model.RsfnResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RsfnService {

    private final WebClient webClient;

    // Usamos @Qualifier para garantir que estamos injetando o WebClient correto,
    // caso existam outros na aplicação. O nome do bean é o nome do método na classe de configuração.
    public RsfnService(@Qualifier("rsfnWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<RsfnResponse> fazerChamadaRsfn(Object requestBody) {
        return webClient.post()
                .uri("/caminho/do/endpoint")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(RsfnResponse.class);
    }
}