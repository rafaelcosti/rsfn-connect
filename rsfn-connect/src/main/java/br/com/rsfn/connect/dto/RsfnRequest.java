package br.com.rsfn.connect.dto;

import lombok.Data;

@Data
public class RsfnRequest {

    private String messageType;
    private String payload; // O payload XML/JSON da mensagem da RSFN

}
