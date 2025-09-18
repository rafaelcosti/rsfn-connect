package br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "rsfn.credentials")
@Validated
public class RsfnProperties {

    @NotBlank
    private String providerName;

    private final Hsm hsm = new Hsm();
    private final Kms kms = new Kms();

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Hsm getHsm() {
        return hsm;
    }

    public Kms getKms() {
        return kms;
    }

    public static class Hsm {
        private String pkcs11Config;
        private String password;
        private String alias;

        // Getters e Setters
        public String getPkcs11Config() { return pkcs11Config; }
        public void setPkcs11Config(String pkcs11Config) { this.pkcs11Config = pkcs11Config; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getAlias() { return alias; }
        public void setAlias(String alias) { this.alias = alias; }
    }

    public static class Kms {
        private String keyId;
        private String certificateArn;

        // Getters e Setters
        public String getKeyId() { return keyId; }
        public void setKeyId(String keyId) { this.keyId = keyId; }
        public String getCertificateArn() { return certificateArn; }
        public void setCertificateArn(String certificateArn) { this.certificateArn = certificateArn; }
    }
}