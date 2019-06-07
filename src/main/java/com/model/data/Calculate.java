package com.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Calculate {
    @JsonProperty("companyCode")
    private String companyCode;
    @JsonProperty("submissionDate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime submissionDate;
    @JsonProperty("hearingDate")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate hearingDate;
    @JsonProperty("deadline")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate deadline;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Company code: ").append(companyCode).
                append("\n").
                append("Submission date: ").append(submissionDate).
                append("\n").
                append("Hearing date: ").append(hearingDate).
                append("\n").
                append("Deadline: ").append(deadline);
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calculate calculate = (Calculate) o;
        return Objects.equals(companyCode, calculate.companyCode) &&
                Objects.equals(submissionDate, calculate.submissionDate) &&
                Objects.equals(hearingDate, calculate.hearingDate) &&
                Objects.equals(deadline, calculate.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, submissionDate, hearingDate, deadline);
    }
}
