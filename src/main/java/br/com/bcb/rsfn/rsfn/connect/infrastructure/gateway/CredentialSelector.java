package br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway;

import br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.configuration.RsfnProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CredentialSelector {

    private final RsfnProperties properties;
    private final Map<String, CredentialProvider> providerMap;

    public CredentialSelector(List<CredentialProvider> providers, RsfnProperties properties) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(CredentialProvider::getProviderName, Function.identity()));
        this.properties = properties;
    }

    public CredentialProvider selectProvider() {
        String providerName = properties.getProviderName();
        CredentialProvider provider = providerMap.get(providerName);

        if (provider == null) {
            throw new IllegalStateException("Nenhum CredentialProvider encontrado para o nome: '" + providerName + "'. Provedores disponíveis: " + providerMap.keySet());
        }

        return provider;
    }
}