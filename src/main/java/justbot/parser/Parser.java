package justbot.parser;

import justbot.command.MarkCommand;
import justbot.command.UnmarkCommand;
import justbot.command.DeleteCommand;
import justbot.command.ListCommand;
import justbot.command.FindCommand;
import justbot.command.TodoCommand;
import justbot.command.EventCommand;
import justbot.command.DeadlineCommand;
import justbot.command.UnknownCommand;
import justbot.command.ByeCommand;
import justbot.command.Command;
import justbot.command.CommandType;

import justbot.exception.JustbotException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Parser class is responsible for interpreting user input and converting it
 * into executable commands. It parses the input string, determines the command type,
 * and constructs the corresponding Command object.
 */
public class Parser {

    /**
     * Parses the user input string and returns the corresponding Command object.
     *
     * @param userInput The input string provided by the user.
     * @return The Command object that corresponds to the user's input.
     * @throws JustbotException if the command is invalid or the input format is incorrect.
     */
    public Command parseCommand(String userInput) throws JustbotException {
        String[] words = userInput.split(" ", 2);
        CommandType commandType = CommandType.fromString(words[0].trim());

        switch (commandType) {
        case TODO:
            if (words.length < 2 || words[1].trim().isEmpty()) {
                throw new JustbotException("Hey man the description for todo blank!");
            }
            return new TodoCommand(words[1].trim());
        case DEADLINE:
            if (words.length != 2) {
                throw new JustbotException(
                        "Invalid deadline command format. Use: deadline [task description] /by [dd/MM/yyyy HH:mm]");
            }
            String[] deadlineParts = words[1].split("/by", 2);
            if (deadlineParts.length < 2) {
                throw new JustbotException(
                        "Invalid deadline command format. Use: deadline [task description] /by [dd/MM/yyyy HH:mm]");
            }
            String deadlineDescription = deadlineParts[0].trim();
                if (deadlineDescription.isBlank()) {
                    throw new JustbotException("Hey man you can't leave the description for deadline blank!");
                }
                LocalDateTime byDateTime = parseDateTime(deadlineParts[1].trim());
                return new DeadlineCommand(deadlineDescription, byDateTime);
            case EVENT:
                if (words.length < 2) {
                    throw new JustbotException("Invalid event command format. Use: event [task description] /from [dd/MM/yyyy HH:mm] /to [dd/MM/yyyy HH:mm]");
                }
                String[] eventParts = words[1].split("/from", 2);
                if (eventParts.length < 2) {
                    throw new JustbotException("Invalid event command format. Use: event [task description] /from [dd/MM/yyyy HH:mm] /to [dd/MM/yyyy HH:mm]");
                }
                String eventDescription = eventParts[0].trim();
                if (eventDescription.isBlank()) {
                    throw new JustbotException("Hey man you can't leave the description for deadline blank!");
                }
                String[] timeParts = eventParts[1].split("/to", 2);
                if (timeParts.length < 2) {
                    throw new JustbotException("Invalid event time format. Use: /from [dd/MM/yyyy HH:mm] /to [dd/MM/yyyy HH:mm]");
                }
                LocalDateTime startDateTime = parseDateTime(timeParts[0].trim());
                LocalDateTime endDateTime = parseDateTime(timeParts[1].trim());
                if (!startDateTime.isBefore(endDateTime)) {
                    throw new JustbotException("Hey man, why is the end date and time before the start date and time?");
                }
                return new EventCommand(eventDescription, startDateTime, endDateTime);
            case LIST:
                return new ListCommand();
            case FIND:
                if (words.length < 2) {
                    throw new JustbotException("Hey man, follow the format:\n" + "find [task description]");
                }
                String keyword = words[1];
                return new FindCommand(keyword);
            case MARK:
                try {
                    int markNumber = Integer.parseInt(words[1].trim());
                    return new MarkCommand(markNumber);
                } catch (NumberFormatException e) {
                    throw new JustbotException("Hey man, follow the format:\n" + "mark [task number]");
                }
            case UNMARK:
                try {
                    int unmarkNumber = Integer.parseInt(words[1].trim());
                    return new UnmarkCommand(unmarkNumber);
                } catch (NumberFormatException e) {
                    throw new JustbotException("Hey man, follow the format:\n" + "unmark [task number]");
                }
            case DELETE:
                try {
                    int deleteNumber = Integer.parseInt(words[1].trim());
                    return new DeleteCommand(deleteNumber);
                } catch (NumberFormatException e) {
                    throw new JustbotException("Hey man, follow the format:\n" + "delete [task number]");
            }
        case BYE:
            return new ByeCommand();
        case UNKNOWN:
        default:
            return new UnknownCommand();
        }
    }

    /**
     * Parses a date and time string and converts it into a LocalDateTime object.
     *
     * @param dateTimeStr The date and time string to parse, expected in the format "dd/MM/yyyy HH:mm".
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws JustbotException if the date and time format is invalid.
     */
    public LocalDateTime parseDateTime(String dateTimeStr) throws JustbotException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return LocalDateTime.parse(dateTimeStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new JustbotException("Invalid date and time format. Please use [dd/MM/yyyy HH:mm].");
        }
    }
}
