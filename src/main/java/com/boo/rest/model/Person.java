package com.boo.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.RegEx;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Builder
@Getter
@AllArgsConstructor
public class Person {
    @Max(Integer.MAX_VALUE)
    @Nonnegative
    private long id;
    @Size(max = 50)
    private String name;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
}
