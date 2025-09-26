package br.com.rsfn.connect.controller;

import br.com.rsfn.connect.dto.RsfnRequest;
import br.com.rsfn.connect.dto.RsfnResponse;
import br.com.rsfn.connect.service.RsfnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/rsfn")
public class RsfnController {

    private final RsfnService rsfnService;

    public RsfnController(RsfnService rsfnService) {
        this.rsfnService = rsfnService;
    }

    @PostMapping("/send")
    public Mono<ResponseEntity<RsfnResponse>> sendMessage(@RequestBody RsfnRequest request) {
        return rsfnService.sendMessage(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
