package nnpia.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public GreetingService() {
    }

    public String sayGreeting() {
        return "Hello world!";
    }
}