package api.body.json.expected;

import java.util.ArrayList;
import java.util.List;

public class RegistrationResponse {

    /**
     * DATA FOR 200 status
     **/
    public List<String> registrationSuccessExpected() {
        List<String> keyList = new ArrayList<>();
        keyList.add("Id");
        keyList.add("UserName");
        keyList.add("Email");
        keyList.add("Password");
        keyList.add("FullName");
        keyList.add("WelcomeString");
        keyList.add("CashSum");
        keyList.add("CurrencyId");

        return keyList;
    }

}
