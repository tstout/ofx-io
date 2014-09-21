ofx-io
===
Have you ever wanted to automate downloading of your bank statements without resorting to web scraping?
If your financial institution supports Open Financial Exchange (http://www.ofx.net/) then ofx-io might
be able to help.

Ofx-io is a small wrapper for OFX4j http://ofx4j.sourceforge.net/. OFX4J deals with all the rolls of XML
toilet paper and protocol details. Ofx-io merely provides a few conveniences that I needed.

Caveats
===
Banks don't usually document how to do this. Finding the right set of parameters to get this to work
for your bank can be frustrating. Look at the following sites for help:
 http://wiki.gnucash.org/wiki/Talk:Setting_up_OFXDirectConnect_in_GnuCash_2
 http://www.ofxhome.com/ofxforum/viewtopic.php?id=47455

Example
===
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

 
Todo
===
* Currently only BOA checking settings are provided, considering adding Schwab.

