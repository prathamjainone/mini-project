package io.github.prathamjainone.productivity.tasks.service;

import io.github.prathamjainone.productivity.tasks.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskParserService {

    public Task parseNaturalLanguage(String input) {
        Task task = new Task();
        
        // Set default values
        task.setTitle(input);
        task.setPriority(Task.Priority.MEDIUM);
        task.setDescription("");
        
        // Convert input to lowercase for easier matching
        String lowerInput = input.toLowerCase();
        
        // Check for priority keywords
        if (lowerInput.contains("urgent") || 
            lowerInput.contains("important") ||
            lowerInput.contains("high priority")) {
            task.setPriority(Task.Priority.HIGH);
        } else if (lowerInput.contains("low priority")) {
            task.setPriority(Task.Priority.LOW);
        }

        // Extract date if present (basic implementation)
        if (lowerInput.contains("tomorrow")) {
            task.setDueDate(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0));
        } else if (lowerInput.contains("next week")) {
            task.setDueDate(LocalDateTime.now().plusWeeks(1).withHour(9).withMinute(0));
        }

        // Extract time if present (HH:mm format)
        Pattern timePattern = Pattern.compile("at (\\d{1,2})(?::(\\d{2}))?(\\s*[ap]m)?", Pattern.CASE_INSENSITIVE);
        Matcher timeMatcher = timePattern.matcher(input);
        if (timeMatcher.find()) {
            try {
                int hour = Integer.parseInt(timeMatcher.group(1));
                int minute = timeMatcher.group(2) != null ? Integer.parseInt(timeMatcher.group(2)) : 0;
                String ampm = timeMatcher.group(3);
                
                // Adjust hour for PM times
                if (ampm != null && ampm.trim().toLowerCase().equals("pm") && hour != 12) {
                    hour += 12;
                }
                // Adjust for 12 AM
                if (ampm != null && ampm.trim().toLowerCase().equals("am") && hour == 12) {
                    hour = 0;
                }
                
                LocalDateTime dueDate = task.getDueDate() != null ? task.getDueDate() : LocalDateTime.now();
                task.setDueDate(dueDate.withHour(hour).withMinute(minute));
            } catch (Exception e) {
                // If time parsing fails, keep the existing due date
            }
        }

        return task;
    }
} 