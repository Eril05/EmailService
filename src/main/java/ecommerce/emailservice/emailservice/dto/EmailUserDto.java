package ecommerce.emailservice.emailservice.dto;

import lombok.Data;

@Data

public class EmailUserDto {

    private String to;
    private String subject;
    private String body;

}
