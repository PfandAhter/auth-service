package com.hotelreservation.authservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BuyRoomRequest extends BaseRequest{

    private Long roomnumber;
    private String member1;
    private String member2;
    private String entrydate;
    private String departdate;
}
