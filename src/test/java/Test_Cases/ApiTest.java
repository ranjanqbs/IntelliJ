package Test_Cases;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

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

        System.out.println("Post request End here");

        // Now post call//////////////////////////////////////
        System.out.println("Post Call Start here");


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
        int eventId = JsonPath.read(eventResponse.text(), "$.data.id");
        System.out.println("Event created and its ID is "+eventId);
        System.out.println("Post request End here");

        // Now post call End here//////////////////////////////////////

        //GET Method Start here ////////////
        System.out.println("Get request Start here");

        APIResponse retrieveEvents = apiRequest.get(
                "https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create()
                        .setQueryParam("page", "1")
                        .setQueryParam("limit", "12")
                        .setHeader("Authorization", "Bearer " + token)
        );

        Assert.assertTrue(
                retrieveEvents.ok(),
                "Event Retrieval API should succeed"
        );

        System.out.println(retrieveEvents.text());

        List<Integer> allEventIds = JsonPath.read(
                retrieveEvents.text(),
                "$.data[*].id"
        );

        Assert.assertTrue(
                allEventIds.contains(eventId),
                "Created event should appear in events list"
        );

        System.out.println("Get request End here");
        //GET Method End here ////////////

        // Delete Method
        APIResponse deleteResponse = apiRequest.delete(
                "https://api.eventhub.rahulshettyacademy.com/api/event/" + eventId,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)
        );

        Assert.assertTrue(deleteResponse.ok());



    }




}
