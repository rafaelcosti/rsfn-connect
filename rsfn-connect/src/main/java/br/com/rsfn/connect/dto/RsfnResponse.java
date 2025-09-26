package br.com.rsfn.connect.dto;

import lombok.Data;

@Data
public class RsfnResponse {

    private String correlationId;
    private String status;
    private String payload;

}
