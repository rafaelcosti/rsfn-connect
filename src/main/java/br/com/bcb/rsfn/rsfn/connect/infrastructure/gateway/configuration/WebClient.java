package br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.configuration;

import br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.CredentialProvider;
import br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.CredentialSelector;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;

@Configuration
public class WebClientConfig {

    private final CredentialSelector credentialSelector;

    public WebClientConfig(CredentialSelector credentialSelector) {
        this.credentialSelector = credentialSelector;
    }

    @Bean
    public WebClient rsfnWebClient() throws Exception {
        // Seleciona o provedor no momento da criação do WebClient.
        // Para uma seleção por requisição, a lógica seria mais complexa,
        // possivelmente envolvendo a criação de múltiplos SslContexts.
        CredentialProvider provider = credentialSelector.selectProvider();
        KeyManagerFactory keyManagerFactory = provider.getKeyManagerFactory();

        // **IMPORTANTE**: Você também precisa de um TrustStore com os certificados
        // da cadeia de confiança da RSFN.
        // TrustManagerFactory trustManagerFactory = ... carregar o cacerts da RSFN ...

        SslContext sslContext = SslContextBuilder.forClient()
                .keyManager(keyManagerFactory)
                // .trustManager(trustManagerFactory) // Descomente e configure seu TrustManager
                .build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api-rsfn.bacen.gov.br") // URL base da RSFN
                .build();
    }
}
