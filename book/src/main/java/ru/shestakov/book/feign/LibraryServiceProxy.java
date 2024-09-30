package ru.shestakov.book.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "Library", url = "localhost:8001")
public interface LibraryServiceProxy {

    @PostMapping("/api/v1/library/add")
    public ResponseEntity<?> save(@RequestParam Integer id, @RequestHeader String Authorization
    );
}
