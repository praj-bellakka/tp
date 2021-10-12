package fitnus.parser;

import fitnus.exception.FitNusException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {

    @Test
    void getDate_validInput_LocalDateObject() {
        String line = "2021-12-23";
        LocalDate date = null;
        try {
            date = Parser.getDate(line);
        } catch (FitNusException e) {
            fail("Parser getDate error");
        }
        assertEquals(LocalDate.of(2021, 12, 23), date);
    }

}