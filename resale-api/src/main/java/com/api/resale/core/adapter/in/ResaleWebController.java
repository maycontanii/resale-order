package com.api.resale.core.adapter.in;

import com.api.resale.core.adapter.in.dto.SaveResalePayload;
import com.api.resale.core.domain.Resale;
import com.api.resale.core.ports.resale.ResaleInputPort;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/resales")
public class ResaleWebController {

    private final ResaleInputPort resaleInputPort;

    @PostMapping({"/", ""})
    public ResponseEntity<String> save(@RequestBody SaveResalePayload payload) {
        payload.validate();
        return ResponseEntity.ok(resaleInputPort.save(payload));
    }

    @GetMapping("/{id}")
    public Resale get(@PathVariable String id) {
        return resaleInputPort.get(id);
    }
}
