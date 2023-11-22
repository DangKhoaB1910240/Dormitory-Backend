package com.Dormitory.blacklist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4401")
@RequestMapping("api/v1/blacklist")
public class BlackListResource {
    @Autowired
    private BlackListService blackListService;
    @PostMapping
    public ResponseEntity<Void> createBlackList(@Valid @RequestBody BlackList blackList) {
        blackListService.createBlackList(blackList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<Page<BlackList>> getAllBlackLists(@PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(blackListService.getAllBlackLists(pageable));
    }
}
