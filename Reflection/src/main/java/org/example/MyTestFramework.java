package org.example;

import com.github.javafaker.Faker;
import org.example.tests.Person;
import org.example.tests.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Objects;

public class MyTestFramework {
    private static final MyClassloader classloader = new MyClassloader();
    private static final Faker faker = new Faker();

    public static void main(String[] args) throws Exception {
        String classpath = "C:\\Users\\nechi\\Desktop\\classes";
        final File folder = new File(classpath);

        if (folder.exists()) {
            URL url = folder.toURI().toURL();
            classloader.addURL(url);

            String namePackage = "";
            listFilesForFolder(folder, namePackage);
        }
    }

    private static void listFilesForFolder(File folder, String namePackage) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                namePackage = namePackage + fileEntry.getName() + ".";

                listFilesForFolder(fileEntry, namePackage);
            } else {
                String className = namePackage + fileEntry.getName();
                className = className.substring(0, (className.length() - 1) - 5);

                Class<?> clazz = classloader.loadClass(className);
                System.out.println("Compiled from \"" + fileEntry.getName() + "\"");

                int modifiers = clazz.getModifiers();

                if (Modifier.isPublic(modifiers)) {
                    disassembleClassFile(clazz);

                    int passed = 0, failed = 0;
                    for (Method m : clazz.getMethods()) {
                        if (m.isAnnotationPresent(Test.class)) {
                            if (m.getParameterTypes().length > 0) {
                                Class[] signature = new Class[]{String.class, String.class};
                                Constructor<?> ctor = clazz.getConstructor(signature);

                                // Prepare the arguments – they must match the signature
                                String firstName = faker.name().firstName();
                                String lastName = faker.name().lastName();

                                // Create the object
                                Person person = (Person) ctor.newInstance(firstName, lastName);

                                if (((m.getParameterTypes()[0]).getSimpleName()).equals("String")) {
                                    String friendName = faker.name().firstName();

                                    try {
                                        m.invoke(person, friendName);
                                        passed++;
                                    } catch (Throwable ex) {
                                        System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                                        failed++;
                                    }
                                } else if (((m.getParameterTypes()[0]).getSimpleName()).equals("int")) {
                                    int age = faker.number().randomDigitNotZero();

                                    try {
                                        m.invoke(person, age);
                                        passed++;
                                    } catch (Throwable ex) {
                                        System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                                        failed++;
                                    }
                                }
                            } else {
                                try {
                                    m.invoke(null);
                                    passed++;
                                } catch (Throwable ex) {
                                    System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                                    failed++;
                                }
                            }
                        }
                    }
                    System.out.printf("Passed: %d, Failed %d\n\n", passed, failed);
                }
            }
        }

    }

    private static void disassembleClassFile(Class clazz) {
        System.out.println("public " + Class.class + " {");

        for (var field : clazz.getFields()) {
            System.out.println(field);
        }

        for (var constructor : clazz.getConstructors()) {
            System.out.println(constructor);
        }

        for (var method : clazz.getMethods()) {
            System.out.println(method);
        }

        System.out.println("}\n");
    }
}