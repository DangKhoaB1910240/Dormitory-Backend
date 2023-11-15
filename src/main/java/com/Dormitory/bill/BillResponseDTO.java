package com.Dormitory.bill;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class BillResponseDTO {
    @NonNull
    private String roomType;
    private Integer numberOfRoomsPaid = 0; //Số lượng phòng đã thanh toán
    private Integer numberOfRoomsUnpaid = 0; //Số lượng phòng chưa thanh toán
    private Float totalPrice = 0f; // tổng giá cần thu
    private Float currentPrice = 0f;//tổng giá hiện tại dã đóng
    private List<Integer> listRoomsPaids = new ArrayList<>(); //Danh sách phòng đã đóng
    private List<Integer> listRoomsUnpaids = new ArrayList<>(); //Danh sách phòng chưa đóng

    public void addListRoomsPaids(Integer r) {
        listRoomsPaids.add(r);
    }
    public void addListRoomsUnpaids(Integer r) {
        listRoomsUnpaids.add(r);
    }
}
