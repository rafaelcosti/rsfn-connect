package br.com.rsfn.connect.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class WebClientConfig {

    // --- Propriedades para os certificados --- //
    // Você precisará adicionar essas propriedades no seu application.properties

    @Value("${rsfn.security.keystore.location}")
    private Resource keyStoreLocation;

    @Value("${rsfn.security.keystore.password}")
    private String keyStorePassword;

    @Value("${rsfn.security.truststore.location}")
    private Resource trustStoreLocation;

    @Value("${rsfn.security.truststore.password}")
    private String trustStorePassword;

    @Bean
    public WebClient rsfnWebClient(@Value("${rsfn.base-url}") String baseUrl) throws Exception {
        SslContext sslContext = SslContextBuilder.forClient()
                .keyManager(getKeyManagerFactory())
                .trustManager(getTrustManagerFactory())
                .build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private KeyManagerFactory getKeyManagerFactory() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream is = keyStoreLocation.getInputStream()) {
            keyStore.load(is, keyStorePassword.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
        return keyManagerFactory;
    }

    private TrustManagerFactory getTrustManagerFactory() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream is = trustStoreLocation.getInputStream()) {
            trustStore.load(is, trustStorePassword.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        return trustManagerFactory;
    }
}
