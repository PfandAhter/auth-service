package com.hotelreservation.authservice.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AuthUserResponse {
    private String token;
}
