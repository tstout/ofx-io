package ofx.client;

import net.sf.ofx4j.client.context.DefaultApplicationContext;
import net.sf.ofx4j.client.context.OFXApplicationContext;
import net.sf.ofx4j.client.impl.BaseFinancialInstitutionData;

import java.net.MalformedURLException;
import java.net.URL;

import static com.google.common.base.Throwables.*;

//
// Connection information found at
// http://wiki.gnucash.org/wiki/Talk:Setting_up_OFXDirectConnect_in_GnuCash_2
// More connection info can be found at
// http://www.ofxhome.com/ofxforum/viewtopic.php?id=47455
//
public class BoaData extends BaseFinancialInstitutionData {
    //
    // Set AppId and AppVersion...
    //
    private final OFXApplicationContext context = new DefaultApplicationContext("QWIN", "1700");

    public BoaData() {
        setOrganization("HAN");
        setFinancialInstitutionId("5959");
        setId("md:1039");

        try {

            setOFXURL(new URL("https://eftx.bankofamerica.com/eftxweb/access.ofx"));
            //
            // Old URL - worked until recently...
            //
            //setOFXURL(new URL("https://ofx.bankofamerica.com/cgi-forte/fortecgi?servicename=ofx_2-3&pagename=bofa"));
        } catch (MalformedURLException e) {
            throw propagate(e);
        }
    }

    public OFXApplicationContext appContext() {
        return context;
    }
}
