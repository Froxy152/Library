package ru.shestakov.Library.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book", url = "localhost:8000")
public interface BookServiceProxy {
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam String status, @RequestHeader String Authorization);
}
