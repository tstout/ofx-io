package ofx.client;

import net.sf.ofx4j.OFXException;
import net.sf.ofx4j.client.AccountStatement;
import net.sf.ofx4j.client.BankAccount;
import net.sf.ofx4j.client.FinancialInstitution;
import net.sf.ofx4j.client.FinancialInstitutionService;
import net.sf.ofx4j.client.context.OFXApplicationContextHolder;
import net.sf.ofx4j.client.impl.FinancialInstitutionServiceImpl;
import net.sf.ofx4j.domain.data.banking.AccountType;
import net.sf.ofx4j.domain.data.banking.BankAccountDetails;
import net.sf.ofx4j.domain.data.common.Transaction;
import org.junit.Test;

import java.util.Date;

import static com.google.common.base.Throwables.*;
import static ofx.client.Dates.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class BofATest {
    @Test public void download_from_boa() {
        BoaData data = new BoaData();

        OFXApplicationContextHolder.setCurrentContext(data.appContext());

        FinancialInstitutionService service
                = new FinancialInstitutionServiceImpl();

        FinancialInstitution fi = service.getFinancialInstitution(data);

        Credentials creds = Credentials.fromProperties(".boa-creds.properties");

        BankAccountDetails details = new BankAccountDetails();
        details.setRoutingNumber(creds.routing());
        details.setAccountNumber(creds.account());
        details.setAccountType(AccountType.CHECKING);

        BankAccount bankAccount
                = fi.loadBankAccount(details,
                creds.user(),
                creds.pass());

        Date startDate = newDate("2014/05/24");
        Date endDate = newDate("2014/05/27");

        try {
            AccountStatement statement
                    = bankAccount.readStatement(startDate, endDate);
            assertThat(statement.getTransactionList().getTransactions().size(), not(0));

            for (Transaction tran : statement.getTransactionList().getTransactions()) {
                System.out.println(tran);
            }
        } catch (OFXException e) {
            propagate(e);
        }


    }
}
