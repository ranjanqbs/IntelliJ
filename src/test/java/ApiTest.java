import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class ApiTest {


    @Test
    // Post Request
    public void e2eApiTest()
    {
        // PUT method////////////////////////////////////
        HashMap<Object, Object> loginPayload = new HashMap<>();
        loginPayload.put("email","ranjanqbs@gmail.com");
        loginPayload.put("password", "Ranjan@121");
        Playwright playwright = Playwright.create();
        APIRequestContext apiRequest = playwright.request().newContext();
        APIResponse loginResponse=apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
        RequestOptions.create().setData(loginPayload));



        System.out.println("Status Code: " + loginResponse.status());
        System.out.println("Status Text: " + loginResponse.statusText());
        System.out.println("Response: " + loginResponse.text());

        Assert.assertTrue(loginResponse.ok());

        // Now post call//////////////////////////////////////

        String token = JsonPath.read(loginResponse.text(), "$.token");
        System.out.println("Login success "+token);
       //Create Event
        String eventTitle = "Playwright API Ranjan";
        HashMap<Object, Object> createEventPayload = new HashMap<>();
        createEventPayload.put("title", eventTitle);
        createEventPayload.put("description", "Ranjan Api");
        createEventPayload.put("category", "Conference");
        createEventPayload.put("venue", "Raja road");
        createEventPayload.put("city", "Patna");
        createEventPayload.put("eventDate", "2026-09-16T05:41:00.000Z");
        createEventPayload.put("price", 700);
        createEventPayload.put("totalSeats", 1400);

        //

        APIResponse eventResponse = apiRequest.post(
                "https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(createEventPayload)
        );

        System.out.println("Create Event Status Code: " + eventResponse.status());
        System.out.println("Create Event Status Text: " + eventResponse.statusText());
        System.out.println("Create Event Response: " + eventResponse.text());

        Assert.assertTrue(eventResponse.ok(), "Create Event API should succeed");


        //
        String eventId = JsonPath.read(eventResponse.text(), "$.data.id").toString();
        System.out.println("Event created and its ID is "+eventId);



        //GET Method

        APIRequest relativeEvent = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events?page=1&limit=12",RequestOptions.create().setQueryParam("page","1")).setQueryParam("limit","12")).





    }




}
