package ofx.client;

import net.sf.ofx4j.OFXException;
import net.sf.ofx4j.client.BankAccount;
import net.sf.ofx4j.client.FinancialInstitution;
import net.sf.ofx4j.client.FinancialInstitutionData;
import net.sf.ofx4j.client.context.OFXApplicationContext;
import net.sf.ofx4j.client.context.OFXApplicationContextHolder;
import net.sf.ofx4j.client.impl.FinancialInstitutionServiceImpl;
import net.sf.ofx4j.domain.data.banking.AccountType;
import net.sf.ofx4j.domain.data.banking.BankAccountDetails;
import net.sf.ofx4j.domain.data.common.Transaction;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Throwables.*;

public class Retriever {
    private final BankAccount bankAccount;

    public Retriever(FinancialInstitutionData data, OFXApplicationContext context, Credentials creds) {
        OFXApplicationContextHolder.setCurrentContext(context);

        BankAccountDetails details = new BankAccountDetails();
        details.setRoutingNumber(creds.routing());
        details.setAccountNumber(creds.account());
        details.setAccountType(AccountType.CHECKING);

        FinancialInstitution fi = new FinancialInstitutionServiceImpl()
                .getFinancialInstitution(data);

        bankAccount
                = fi.loadBankAccount(details,
                creds.user(),
                creds.pass());
    }

    public List<Transaction> fetch(Date startDate, Date endDate) {
        try {
            return bankAccount
                    .readStatement(startDate, endDate)
                    .getTransactionList()
                    .getTransactions();
        } catch (OFXException e) {
            throw propagate(e);
        }
    }
}
