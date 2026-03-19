package com.miiList.product.api.contract;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miiList.product.api.dto.ProductRequest;
import com.miiList.product.api.dto.ProductResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;


@Tag(name = "Products", description = "API to manage product list")
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public interface IProductApi {

    @Operation(summary = "Gets product list")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Products listed successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponse.class)
            )
        )
    })
    @GetMapping
    List<ProductResponse> products(
        @Parameter(description = "Optional filter for name or category")
        @RequestParam(required = false) String filter
    );

    @Operation(summary = "Remove product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product removed"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(
        @Parameter(description = "Product ID")
        @PathVariable Long id
    );

    @Operation(summary = "Add new product")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product created",
            content = @Content(schema = @Schema(implementation = ProductResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    @PostMapping
    ResponseEntity<ProductResponse> addProduct(
        @Valid @RequestBody ProductRequest product
    );
}