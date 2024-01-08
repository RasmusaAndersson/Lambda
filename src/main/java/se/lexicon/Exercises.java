package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       TODO:  1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        List<Person> eriks = storage.findMany(p -> p.getFirstName().equals("Erik"));
        eriks.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        List<Person> females = storage.findMany(p -> p.getGender() == Gender.FEMALE);
        females.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        LocalDate date = LocalDate.of(2000, 1, 1);
        List<Person> youngPeople = storage.findMany(p -> p.getBirthDate().isAfter(date) || p.getBirthDate().isEqual(date));
        youngPeople.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO: 4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        Optional<Person> person = Optional.ofNullable(storage.findOne(p -> p.getId() == 123));
        if (person.isPresent()) {
            System.out.println(person.get());
        } else {
            System.out.println("No person with id 123");
        }
        System.out.println("----------------------");

    }

    /*
        TODO:  5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        Optional<String> personString = Optional.ofNullable(storage.findOneAndMapToString(p -> p.getId() == 456, p -> "Name: " + p.getFirstName() + " " + p.getLastName() + " born " + p.getBirthDate()));
        if (personString.isPresent()) {
            System.out.println(personString.get());
        } else {
            System.out.println("No person with id 456");
        }
        System.out.println("----------------------");
    }

    /*
        TODO:  6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        List<String> result = storage.findManyAndMapEachToString(
                person -> person.getGender() == Gender.MALE && person.getFirstName().startsWith("E"),
                Person::toString
        );
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        List<String> result = storage.findManyAndMapEachToString(
                person -> person.getId() < 10,
                person -> person.getFirstName() + " " + person.getLastName() + " " + person.getId() + " years"
        );
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        storage.findAndDo(
                person -> person.getFirstName().equals("Ulf"),
                System.out::println
        );
        System.out.println("----------------------");
    }

    /*
        TODO:  9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        storage.findAndDo(
                person -> person.getLastName().contains(person.getFirstName()),
                System.out::println
        );
        System.out.println("----------------------");
    }

    /*
        TODO:  10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        storage.findAndDo(person -> {
            return isPalindrome(person.getFirstName());
        }, Exercises::accept);
        System.out.println("----------------------");
    }

    private static boolean isPalindrome(String firstName) {
        return false;
    }

    /*
        TODO:  11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        List<Person> result = storage.findAndSort(
                person -> person.getFirstName().startsWith("A"),
                Comparator.comparing(person1 -> {
                    final LocalDate birthDate = person1.getBirthDate();
                    return birthDate;
                })
        );
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  12.	Using findAndSort() find everyone born before 1950 sorted reversed by latest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        List<Person> result = storage.findAndSort(
                person -> person.getBirthDate().getYear() < 1950,
                (p1, p2) -> p2.getBirthDate().compareTo(p1.getBirthDate())
        );
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        TODO:  13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        List<Person> result = storage.findAndSort(
                person -> true,
                (p1, p2) -> {
                    int lastNameCmp = p1.getLastName().compareTo(p2.getLastName());
                    if (lastNameCmp != 0) {
                        return lastNameCmp;
                    }
                    int firstNameCmp = p1.getFirstName().compareTo(p2.getFirstName());
                    if (firstNameCmp != 0) {
                        return firstNameCmp;
                    }
                    return p1.getBirthDate().compareTo(p2.getBirthDate());
                }
        );
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    private static void accept(Person person) {
        System.out.println(person.getFirstName() + " " + person.getLastName());
    }
}
