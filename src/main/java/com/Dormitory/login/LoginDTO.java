package com.Dormitory.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO { // DT0 = Data transfer object
    
    private String username;

    private String password;

}
