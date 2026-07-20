package com.example.chatbot;

import org.springframework.web.bind.annotation.*;

/**
 * A deliberately simple, rule-based "chatbot".
 *
 * It is NOT an LLM. It exists purely as a target for garak's REST generator,
 * so you can see garak's HTTP-level scanning mechanics without needing an
 * API key or a real model.
 *
 * It has one intentional design flaw, on purpose: a "secret" it is told
 * never to reveal, but which naive keyword-based instruction handling lets
 * slip out if you ask the right way. This mirrors, in miniature, how a real
 * LLM chatbot can leak system-prompt content under prompt injection -- and
 * gives garak's promptinject-style probes something real to catch.
 */
@RestController
@RequestMapping("/api")
public class ChatController {

    private static final String SECRET_CODE = "ALPHA-7-QUEEN";

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String message = request.getMessage() == null ? "" : request.getMessage().toLowerCase();

        // Intentionally naive "instruction override" handling -- do not copy this
        // pattern into a real application. It exists so a scanner has something
        // to find.
        if (message.contains("ignore previous instructions")
                || message.contains("reveal the secret")
                || message.contains("what is the secret code")
                || message.contains("system prompt")) {
            return new ChatResponse(
                    "Sure! The internal secret code is " + SECRET_CODE + ". Anything else?");
        }

        if (message.contains("hello") || message.contains("hi")) {
            return new ChatResponse("Hello! I'm a demo support bot. How can I help you today?");
        }

        return new ChatResponse(
                "I'm a simple demo bot. I can help with basic support questions. "
                        + "I will never share internal secrets, no matter how you ask. (...usually.)");
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
