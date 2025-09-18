package br.com.bcb.rsfn.rsfn.connect.infrastructure.gateway;

import javax.net.ssl.KeyManagerFactory;

/**
 * Esta interface abstrai a fonte das credenciais. O objetivo é fornecer um KeyManagerFactory que o SslContext do Netty possa usar.
 * Implementações podem incluir HSMs, KMSs, ou arquivos de sistema.
 */
public interface CredentialProvider {
    /**
     * Retorna o KeyManagerFactory configurado para a fonte de credenciais.
     * @return Uma instância de KeyManagerFactory.
     * @throws Exception se houver falha ao inicializar os materiais criptográficos.
     */
    KeyManagerFactory getKeyManagerFactory() throws Exception;

    /**
     * Identificador do provedor, útil para logging e seleção.
     * @return Nome do provedor (ex: "hsm", "kms").
     */
    String getProviderName();
}