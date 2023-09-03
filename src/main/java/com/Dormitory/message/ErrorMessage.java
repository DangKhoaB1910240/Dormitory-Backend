package com.Dormitory.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor// XÂY DỰNG HÀM CONSTRUCTOR với 2 tham số message và statuscode;
public class ErrorMessage {
    
    @NonNull
    private String message;

    private List<String> details = new ArrayList<>();

}
