package ofx.client;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.google.common.base.Throwables.*;
import static com.google.common.io.Resources.*;
import static java.lang.System.*;

class Ssl {
    private static final File TS_FILE = new File(getProperty("user.home"), ".ofx-io/trust-store");
    //
    // As of 11/15/2014 - https://ofx.bankofamerica.com is missing an intermediate cert.
    // The code here is a work-around for this problem by creating an app-specific
    // keystore including the missing cert.
    // A pre-configured keystore is loaded from a resource and copied to the directory
    // .ofx-io. The JVM-wide truststore is then set to this custom trust store via a
    // system property. This seems like a bit of a hack, however it is simpler than
    // many other alternatives. Perhaps BOA will fix their cert someday!
    //
    // Note:
    // https://ssltools.websecurity.symantec.com/checker/views/certCheck.jsp
    //
    void installTrustStore() {
        if (!TS_FILE.exists()) {
            copyTrustStore();
        }
        setProperty("javax.net.ssl.trustStore", TS_FILE.getAbsolutePath());
    }

    private void copyTrustStore() {
        try {
            Files.createParentDirs(TS_FILE);
            copy(getResource("trust-store"), new FileOutputStream(TS_FILE));
        } catch (IOException e) {
            throw propagate(e);
        }
    }
}
