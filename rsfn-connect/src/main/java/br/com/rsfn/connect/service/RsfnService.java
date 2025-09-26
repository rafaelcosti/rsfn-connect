package br.com.rsfn.connect.service;

import br.com.rsfn.connect.dto.RsfnRequest;
import br.com.rsfn.connect.dto.RsfnResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RsfnService {

    private final WebClient rsfnWebClient;

    public RsfnService(@Qualifier("rsfnWebClient") WebClient rsfnWebClient) {
        this.rsfnWebClient = rsfnWebClient;
    }

    /**
     * Envia uma mensagem para a RSFN.
     *
     * @param request O objeto de requisição contendo a mensagem.
     * @return Um Mono contendo a resposta da RSFN.
     */
    public Mono<RsfnResponse> sendMessage(RsfnRequest request) {
        // Aqui você define o endpoint específico para o serviço que está consumindo
        String specificEndpoint = "/messages"; // Exemplo

        return rsfnWebClient.post()
                .uri(specificEndpoint)
                .body(Mono.just(request), RsfnRequest.class)
                .retrieve()
                .bodyToMono(RsfnResponse.class)
                .doOnSuccess(response -> {
                    // Lógica em caso de sucesso
                    System.out.println("Resposta recebida com sucesso: " + response.getStatus());
                })
                .doOnError(error -> {
                    // Lógica para tratamento de erro (ex: log, métricas)
                    System.err.println("Erro ao enviar mensagem para a RSFN: " + error.getMessage());
                });
    }
}
