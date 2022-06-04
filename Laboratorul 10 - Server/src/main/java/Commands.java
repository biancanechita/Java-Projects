import entity.Message;
import entity.Person;
import repository.PersonRepository;

import java.util.List;

public class Commands {
    private static final PersonRepository persons = new PersonRepository();

    public static void register(Person entity) {
        persons.save(entity);
    }

    public static boolean login(String name, String password) {
        Person person = persons.findByName(name);

        if (person == null)
            return false;

        return person.getPassword().compareTo(password) == 0;
    }

    public static boolean friend(String personName, String[] names) {
        Person person = persons.findByName(personName);

        for (String name : names) {
            Person friend = persons.findByName(name);

            if (friend == null) {
                return false;
            } else {
                person.addFriend(friend);
                persons.save(person);

                friend.addFriend(person);
                persons.save(friend);
            }
        }

        return true;
    }

    public static void send(String name, String text) {
        Person person = persons.findByName(name);

        for (Person friend : person.getFriends()) {
            Message message = new Message(person.getId(), friend.getId(), text);

            persons.sendMessage(message);
        }
    }

    public static List<Message> read(String name) {
        Person person = persons.findByName(name);

        List<Message> messages = persons.readMessage(person);

        System.out.println(messages);

        return messages;
    }
}
