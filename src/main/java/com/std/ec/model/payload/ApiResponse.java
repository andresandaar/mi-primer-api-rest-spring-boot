package com.std.ec.model.payload;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {
    private Date timestamp =new Date();
    private String message;
    private String url;

    public ApiResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=","");
    }



}
