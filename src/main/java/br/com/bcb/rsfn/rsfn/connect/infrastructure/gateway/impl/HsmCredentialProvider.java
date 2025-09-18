package br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.impl;

import br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.CredentialProvider;
import br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.configuration.RsfnProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

@Component
public class HsmCredentialProvider implements CredentialProvider {

    private final RsfnProperties.Hsm hsmProperties;

    public HsmCredentialProvider(RsfnProperties properties) {
        this.hsmProperties = properties.getHsm();
    }

    @Override
    public KeyManagerFactory getKeyManagerFactory() throws Exception {
        // Configuração do provider SunPKCS11
        Provider p = new sun.security.pkcs11.SunPKCS11(new ByteArrayInputStream(hsmProperties.getPkcs11Config().getBytes()));
        Security.addProvider(p);

        // Carrega a KeyStore do HSM
        KeyStore hsmKeyStore = KeyStore.getInstance("PKCS11", p);
        hsmKeyStore.load(null, hsmProperties.getPassword().toCharArray());

        // Inicializa o KeyManagerFactory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(hsmKeyStore, hsmProperties.getPassword().toCharArray());

        return kmf;
    }

    @Override
    public String getProviderName() {
        return "hsm";
    }
}