package example.cashcard;


import example.cashcard.model.CashCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;



    @Test
    public void cashCardSerializationTest() throws IOException {

        CashCard c = new CashCard(99l , 123.45);

        assertThat(json.write(c)).isStrictlyEqualToJson("expected.json");

        assertThat(json.write(c)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(c)).extractingJsonPathNumberValue("@.id").isEqualTo(99);

        assertThat(json.write(c)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(c)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);

    }

    @Test
    public void cashCardDeserelizationTest() throws IOException {
        String expected = """
                {
                "id":99,
                "amount":123.45
                }
                """;

        assertThat(json.parse(expected)).isEqualTo(new CashCard(99L,123.45));
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
                
    }
}
