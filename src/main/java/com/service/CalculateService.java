package com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.model.data.Calculate;
import com.model.data.Holiday;
import com.utils.jsonparser.JsonUtils;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Service
public class CalculateService {
    public static final String COMPANY_A_CODE = "0001";
    public static final String COMPANY_B_CODE = "0023";
    public static final String SUNDAY = "SUNDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final long DAYS_AMOUNT_FOR_COMPANY_A = 6;
    public static final long DAYS_AMOUNT_FOR_COMPANY_B = 9;

    private BiPredicate<LocalDate, List<Holiday>> isHoliday =
            (date, holidays) -> {
        for (Holiday holiday : holidays) {
            if (holiday.getDate().compareTo(date) == 0) {
                return true;
            }
        }
        return false;
    };

    private Predicate<LocalDate> isWeekend = (date) ->
            (date.getDayOfWeek().toString().equals(SATURDAY)
                    || date.getDayOfWeek().toString().equals(SUNDAY));


    public long getDaysAmountForCompany(String companyCode) {
        switch (companyCode) {
            case COMPANY_A_CODE:
                return DAYS_AMOUNT_FOR_COMPANY_A;
            case COMPANY_B_CODE:
                return DAYS_AMOUNT_FOR_COMPANY_B;
            default:
                throw new BadRequestException("bad company code value");
        }
    }

    public Calculate calculateDeadline(final LocalDate startDate,
                                       final List<Holiday> holidays,
                                       final String companyCode) {
        LocalDate deadline = startDate.
                plusDays(getDaysAmountForCompany(companyCode));

        LocalDate date = startDate;
        while (date.isBefore(deadline) || date.compareTo(deadline) == 0) {
            if (isHoliday.test(date, holidays) || isWeekend.test(date)) {
                deadline = deadline.plusDays(1);
            }
            date = date.plusDays(1);
        }
        return new Calculate(companyCode, null, startDate, deadline);
    }

    public String calculateObj(final Response response) throws IOException {
        Calculate calculate = JsonUtils.fromJSON(response.body().string(),
                new TypeReference<Calculate>() {
                });
        StringBuilder str = new StringBuilder();
        str.append("Company code: ").append(calculate.getCompanyCode())
                .append("<br>").
                append("Submission date: ").
                append(calculate.getSubmissionDate())
                .append("<br>").
                append("Hearing date: ").
                append(calculate.getHearingDate())
                .append("<br>").
                append("Deadline: ").append(calculate.getDeadline());
        return str.toString();
    }

    public String calculateJson(final Calculate calculate)
            throws JsonProcessingException {
        return JsonUtils.toJSON(calculate);
    }

    public int holidaysAmount(LocalDate startDate,
                              LocalDate deadline,
                              List<Holiday> holidays) {
        int amount = 0;
        while (startDate.isBefore(deadline) || startDate.compareTo(deadline) == 0) {
            if (isHoliday.test(startDate, holidays)) {
                amount++;
            }
            startDate = startDate.plusDays(1);
        }
        return amount;
    }

    public int weekendsAmount(LocalDate startDate, final LocalDate deadline) {
        int amount = 0;
        while (startDate.isBefore(deadline)
                || startDate.compareTo(deadline) == 0) {
            if (isWeekend.test(startDate)) {
                amount++;
            }
            startDate = startDate.plusDays(1);
        }
        return amount;
    }
}
