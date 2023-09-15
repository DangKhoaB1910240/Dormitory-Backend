package com.Dormitory.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthResponseDTO {
    @NonNull
    private String accessToken;
    private String typeToken = new String("Bearer ");
}
