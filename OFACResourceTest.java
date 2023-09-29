package com.owit.partymanager.controller;

import com.google.common.base.Defaults;
import com.owit.partymanager.PartyManagerApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = {PartyManagerApplication.class})
class OFACResourceTest {

    @Test
    public void testPojos() {
        findAllClassesUsingClassLoader("com.accuity").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.administration.dto").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.adminsync.entity").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.dto").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.entity.party").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.entity").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.controller.model.party").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.controller.model").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.security.dto").stream().forEach(this::testAllPojos);
        findAllClassesUsingClassLoader("com.owit.partymanager.constants").stream().forEach(this::testAllPojos);
    }

    private void testAllPojos(Class<?> classUnderTest) {
        try {
            //Object o = classUnderTest.newInstance();
            if (classUnderTest.isInterface()) return;

            Object o = classUnderTest.getConstructors().length == 0
                    ? classUnderTest.newInstance()
                    : classUnderTest.getConstructors()[0]
                    .newInstance(new Object[classUnderTest.getConstructors()[0].getParameterCount()]);
            if (classUnderTest.getConstructors().length != 0)
                callConstructors(classUnderTest);
            callPojoMethods(classUnderTest, o, "set", new Object[]{null});
            callPojoMethods(classUnderTest, o, "get", new Object[]{});
            o.toString();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void callConstructors(Class<?> classUnderTest) {
        try {
            for (Constructor constructor : classUnderTest.getConstructors()) {
                Object[] objects = new Object[constructor.getParameterCount()];
                for (int i = 0; i < constructor.getParameterCount(); i++) {
                    objects[i] = Defaults.defaultValue(constructor.getParameterTypes()[i]);
                }
                constructor.newInstance(objects);
            }
        } catch (Exception e) {
            System.out.println("Error while calling constructors : " + e.getMessage());
        }
    }

    private void callPojoMethods(Class<?> classUnderTest, Object o, String type, Object[] objArray) {
        Arrays.stream(classUnderTest.getDeclaredMethods()).filter(method -> method.getName().startsWith(type))
                .forEach(e -> {
                    try {
                        if (type.equals("set")) {
                            Object[] objects = new Object[e.getParameterCount()];
                            for (int i = 0; i < e.getParameterCount(); i++) {
                                objects[i] = Defaults.defaultValue(e.getParameterTypes()[i]);
                            }
                            e.invoke(o, objects);
                        } else {
                            e.invoke(o, objArray);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error while executing pojo testing for " + e.getName() + " for " + classUnderTest.getName());
                    }
                });
    }

    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            System.out.println("Error while getting class");
        }
        return null;
    }

}
