package com.Dormitory.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessMessage {

    private String message;
    private Integer httpStatusCode;
}
