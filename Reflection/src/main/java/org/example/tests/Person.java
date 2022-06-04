package org.example.tests;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Test
    public void sayHello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    @Test
    public void saySomething(int age) {
        System.out.println("My brother is " + age + " years old.");
    }
}
