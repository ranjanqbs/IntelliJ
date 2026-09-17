package Test_Cases;

import java.io.IOException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderDemoTest {

    @DataProvider(name = "basicData")
    public Object[][] getBasicData() {
        return new Object[][]{
                {"testuser1", "password1"},
                {"testuser2", "password2"},
                {"testuser3", "password3"}
        };
    }

    @Test(dataProvider = "basicData")
    public void testFillForm(String username, String password) {
        System.out.println("Username: " + username + ", Password: " + password);
    }

    @DataProvider(name = "hasMapData")
    public Object[][] hasMapData() {
        Map<String, String> user1 = new HashMap<>();
        user1.put("email", "Test@gmail.com");
        user1.put("password", "Test@1");

        Map<String, String> user2 = new HashMap<>();
        user2.put("email", "Test2@gmail.com");
        user2.put("password", "Test@13");

        return new Object[][]{
                {user1},
                {user2}
        };
    }


    @DataProvider(name = "jsonData")
    public Object[][] jsonData() throws IOException {
        String jsonContent = new String(Files.readAllBytes(
                Paths.get(System.getProperty("user.dir"), "src", "test", "Resources", "TestData_TC1.json")));
        Type type = new TypeToken<List<HashMap<String, String>>>(){}.getType();
        List<HashMap<String, String>> list = new Gson().fromJson(jsonContent, type);
        Object[][] table = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            table[i][0] = list.get(i);
        }
        return table;
    }

    @Test(dataProvider = "jsonData")
    public void testWithJsonUser(Map<String, String> user) {
        System.out.println(user.get("email"));
        System.out.println(user.get("password"));
    }
}