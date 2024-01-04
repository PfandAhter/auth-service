package com.hotelreservation.authservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCheckInRequest extends BaseRequest{
    Long userid;
}

