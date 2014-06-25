package ofx.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import static com.google.common.base.Throwables.*;
import static java.lang.System.*;

public class Credentials {
    private final String routing;
    private final String account;
    private final String user;
    private final String pass;

    private Credentials(String routing, String account, String user, String pass) {
        this.routing = routing;
        this.account = account;
        this.user = user;
        this.pass = pass;
    }

    public String routing() {
        return routing;
    }

    public String account() {
        return account;
    }

    public String user() {
        return user;
    }

    public String pass() {
        return pass;
    }

    /**
     * Load credentials from a properties file. It is assumed that the file
     * resides in #{user.home}
     * The Properites file must contain:
     *
     * account=checking account number
     * routing=account routing number
     * user=your online user id
     * pass=your password
     */
    public static Credentials fromProperties(String fName) {

        try (BufferedReader in = newReader(fName)) {

            return fromProperties(new Properties() {{
                load(in);
            }});

        } catch (Exception e) {
            throw propagate(e);
        }
    }

    private static BufferedReader newReader(String fName) {
        try {
            return new BufferedReader(new FileReader(new File(getProperty("user.home"), fName)));
        } catch (FileNotFoundException e) {
            throw propagate(e);
        }
    }

    public static Credentials fromProperties(Properties props) {
        return new Builder()
                .withAccount(props.getProperty("account"))
                .withPass(props.getProperty("pass"))
                .withRouting(props.getProperty("routing"))
                .withUser(props.getProperty("user"))
                .build();
    }

    public static class Builder {
        private String routing;
        private String account;
        private String user;
        private String pass;

        public Builder withRouting(String routing) {
            this.routing = routing;
            return this;
        }

        public Builder withAccount(String account) {
            this.account = account;
            return this;
        }

        public Builder withUser(String user) {
            this.user = user;
            return this;
        }

        public Builder withPass(String pass) {
            this.pass = pass;
            return this;
        }

        public Credentials build() {
            return new Credentials(routing, account, user, pass);
        }
    }
}
