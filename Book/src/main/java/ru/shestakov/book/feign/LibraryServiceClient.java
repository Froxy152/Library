package ru.shestakov.book.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@FeignClient(name = "Library")
public interface LibraryServiceClient {

    @PostMapping("/api/v1/library/add")
    public ResponseEntity<?> save(@RequestParam Integer id, @RequestHeader String Authorization);
    @GetMapping("/api/v1/library")
    public ResponseEntity<?> healthCheck(@RequestHeader String Authorization);
    @DeleteMapping("/api/v1/library")
    public void deleteByStatus(@RequestParam Integer id, @RequestHeader String Authorization);
}

