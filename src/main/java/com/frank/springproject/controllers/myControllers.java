package com.frank.springproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.frank.springproject.thedata.Answer;
import com.frank.springproject.thedata.Question;
import com.frank.springproject.thedata.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController // Tell the server there are controllers in here to handle paths

public class myControllers {
        // HttpServletRequest as the first parameter to the method allows access to HTTP request information
        //             using the object name specified
        @GetMapping(value="/")   // handle the "root path - /
        public String anyNameYouWant(HttpServletRequest request) {
//                System.out.println("--- Root path (/) handler called ---");
                logRequest(request);
                return "some string";
        }

        @GetMapping(value="/dinosaurs")   // handle the path - /dinosaurs for GET
        public String theAnimals(HttpServletRequest request) {
//                System.out.println("--- HTTP GET for path (/dinosaurs) handler called ---");
                logRequest(request);
                return "Not the mama!";
        }

        @PostMapping(value="/tvshow")   // handle the path - /dinosaurs for POST
        public String theTVShow(HttpServletRequest request) {
                logRequest(request);
                return "The flinstones";
        }

        @PostMapping(value="/name/jayda")   // handle the path - /dinosaurs/matthew for POST
        public String frank(HttpServletRequest request) {
                logRequest(request);
                return "Jayda";
        }


        @GetMapping(value="/jayda")   // handle the path - /uma for GET
        public Data theJaydaMethod(HttpServletRequest request) {
                logRequest(request);
                Data responseObject = new Data();

                responseObject.setQuestion("Where is the Sprint 2 Canvas stuff?");
                responseObject.setAnswer("Patience, Jayda!");

                return responseObject;
        }
        // Data objects are normally NOT sent with a GET HTTP request
        // POST request used since we want to send data to server
        // Note use of @RequestBody in parameter to method
        //      this tells the server to convert the JSON in the request
        //      to the Object specified - the  default ctro and setters for class
        //      are used to instantiate and populate the new object.
        //
        //      The JSON data attribute names must match the name of the data members in the class
        //
        @PostMapping(value="/askQuestion")   // handle the path - /askQuestion for POST
        public Answer questionMethod(HttpServletRequest aRequest, @RequestBody Question questionAsked) throws JsonProcessingException {

                logRequest(aRequest);                                             // Write HTTP request to log
                ObjectWriter ow = new ObjectMapper().writer();                    // Convert object received to JSON
                logMessage("with body: " + ow.writeValueAsString(questionAsked)); // add data from body of request to log

                Answer anAnswer = new Answer();

                switch(questionAsked.getQuestionText())   {
                        case "Who is teaching unit 6?":
                                anAnswer.setTheAnswer("Frank");
                                break;
                        case "Who is teaching unit 1?":
                                anAnswer.setTheAnswer("Brian");
                                break;
                        case "Who is teaching unit 2?":
                                anAnswer.setTheAnswer("Daniel");
                                break;
                        case "Who is teaching unit 4?":
                                anAnswer.setTheAnswer("Mauli");
                                break;
                        case "Who is teaching unit 5?":
                                anAnswer.setTheAnswer("Tom");
                                break;
                        default:
                                anAnswer.setTheAnswer("You have stumped me!");
                }

                return anAnswer;
        }

        // Log request with timestamp
        private void logRequest(HttpServletRequest theRequest) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
                String timeNow = now.format(formatter);

                System.out.printf("%s --> %4s %4s request for URL: %s%s\n",
                        timeNow
                        , theRequest.getProtocol()
                        , theRequest.getMethod()
                        , theRequest.getRequestURI()
                        , (theRequest.getQueryString() != null ? ("?" + theRequest.getQueryString()) : ""));

        }

        // log a message passed in as a parameter
        private void logMessage(String message) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
                String timeNow = now.format(formatter);

                System.out.printf("%s --> %s\n", timeNow, message);
        }

}
