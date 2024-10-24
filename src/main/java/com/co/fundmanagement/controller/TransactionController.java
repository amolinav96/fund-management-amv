package com.co.fundmanagement.controller;

import com.co.fundmanagement.dto.RequestTransaction;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.model.Transaction;
import com.co.fundmanagement.service.impl.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Operaciones relacionadas con las transacciones")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    @Operation(summary = "Create Transaction", description = "Crear una transaccion en el sistema")
    public Mono<Response> createTransaction(@RequestBody RequestTransaction requestTransaction){
        return transactionService.createTransaction(requestTransaction);
    }

    @GetMapping("/allByUserId")
    @Operation(summary = "find all by userId", description = "Obtener todas las transacciones por userId")
    public Flux<Transaction> findAllByUserId(@RequestParam String userId){
        return transactionService.findAllByUserId(userId);
    }
}
