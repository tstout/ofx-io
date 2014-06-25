package ofx.client;

import net.sf.ofx4j.domain.data.common.Transaction;
import org.junit.Test;

import java.util.List;

import static ofx.client.Dates.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class BofATest {

    @Test
    public void retrieve_from_boa() {
        List<Transaction> transactions =
                new Retriever(new BoaData(),
                        BoaData.CONTEXT,
                        Credentials.fromProperties(".boa-creds.properties"))
                .fetch(newDate("2014/05/01"), newDate("2014/06/01"));

        assertThat(transactions.size(), not(0));
    }

}
