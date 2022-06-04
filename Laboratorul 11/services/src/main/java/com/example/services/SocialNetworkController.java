package com.example.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/people")
public class SocialNetworkController {
    private final PersonRepository repository;
    private final List<Person> people = new ArrayList<>();

    public SocialNetworkController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Person> getPeople() {
        return (List<Person>) repository.findAll();
    }

    @PostMapping
    public int createPerson(@RequestParam String name, @RequestParam String password) {
        Person person = new Person(name, password);
        repository.save(person);

        return person.getId();
    }

    @GetMapping("/{id}")
    public Optional<Person> getPerson(@PathVariable("id") int id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(
            @PathVariable int id, @RequestParam String password) {
        Optional<Person> person = getPerson(id);

        if (person.isEmpty()) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.NOT_FOUND); //or GONE
        }
        person.get().setPassword(password);

        return new ResponseEntity<>(
                "Password updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable int id) {
        repository.deleteById(id);

        return new ResponseEntity<>(
                "Person removed", HttpStatus.OK);
    }

    @GetMapping("/popular/{k}")
    public List<Person> getPopularPeople(@PathVariable int k) {
        Collections.sort(people);
        List<Person> popularPeople = new ArrayList<>();

        int length = people.size() - 1;
        for (int i = 0; i < k; i++)
            popularPeople.add(people.get(length - i));

        return popularPeople;
    }

    @PatchMapping("/{id}/relationship/{friendId}")
    public ResponseEntity<String> insertRelationship(@PathVariable int id, @PathVariable int friendId) {
        Optional<Person> person = getPerson(id);
        Optional<Person> friend = getPerson(friendId);

        if (person.isEmpty()) {
            return new ResponseEntity<>(
                    "Person not found", HttpStatus.GONE);
        } else if (friend.isEmpty()) {
            return new ResponseEntity<>(
                    "Friend not found", HttpStatus.GONE);
        } else {
            person.get().addFriend(friend.get());
            friend.get().addFriend(person.get());

            return new ResponseEntity<>(
                    "Friend added succesfully", HttpStatus.GONE);
        }
    }

    @GetMapping("/{id}/relationship")
    public Set<Person> readRelationship(@PathVariable int id) {
        Optional<Person> person = getPerson(id);

        if (person.isEmpty()) {
            return null;
        } else
            return person.get().getFriends();
    }
}
