package com.hotelreservation.authservice.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListInRoomsRequest extends BaseRequest{
    private Long userid;
}

