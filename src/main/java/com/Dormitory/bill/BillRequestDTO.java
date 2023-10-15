package com.Dormitory.bill;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequestDTO {
    @NotNull(message = "Chỉ số điện cuối không được bỏ trống")
    private Integer finalElectricity;
    @NotNull(message = "Chỉ số nước cuối không được bỏ trống")
    private Integer finalWater;
    @NotNull(message = "ID phòng không được bỏ trống")
    private Integer roomId;
}
