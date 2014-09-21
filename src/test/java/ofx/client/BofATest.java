package ofx.client;

import net.sf.ofx4j.domain.data.common.BalanceInfo;
import net.sf.ofx4j.domain.data.common.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.joda.time.DateTime.*;
import static org.junit.Assert.*;

public class BofATest {

    @Test
    public void retrieve_transactions_boa() {
        List<Transaction> transactions =
                new Retriever(new BoaData(),
                        BoaData.CONTEXT,
                        Credentials.fromProperties(".boa-creds.properties"))
                        .fetch(now().minusDays(2), now().minusDays(1))
                        .getTransactionList()
                        .getTransactions();

        assertThat(transactions.size(), not(0));
    }

    @Test
    public void retrieve_balance_boa() {
        BalanceInfo balance = new Retriever(new BoaData(),
                BoaData.CONTEXT,
                Credentials.fromProperties(".boa-creds.properties"))
                .fetch(now().minusDays(2), now().minusDays(1))
                .getAvailableBalance();

        assertThat(new BigDecimal(balance.getAmount()), not(BigDecimal.ZERO));
    }

}
