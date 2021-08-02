import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws Exception {
        //createEmptyStream();
        //streamOfCollection();
        //streamOfArray();
        //generateSteam();
        //iterateStream();
        //streamOfPrimitives();
        //loopEachCharactorInString();
        //streamOfFiles();
        //streamReduce();
        //streamGroupingBy1();
        //streamGroupingBy2();
        //streamGroupingBy3();
        //streamsPartionedBy1();
        //streamsPartionedBy2();
        //streamsPartionedBy3();
        //streamsWithToMap();
        //streamsWithToConcurentHashMap();

        // given a map of emps (k=dept, v=list of emps),
        // expecting values should be emp names and sort by dept id
        //streamsMapCustomeLogic1();

    }

    private static void streamsMapCustomeLogic1() {
        Map<Integer, List<Emp>> collect = prepareEmps().stream().collect(Collectors.groupingBy(Emp::getDept));
        Map<Integer, List<String>> collect1 = collect.entrySet().stream()
                .collect(
                        Collectors.toMap(e -> e.getKey(), e ->
                                e.getValue().stream()
                                        .sorted(Comparator.comparing(Emp::getId))
                                        .map(Emp::getName)
                                        .collect(Collectors.toList())));
        collect1.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamsWithToConcurentHashMap() {
        Map<Integer, List<Emp>> collect = prepareEmps().stream()
                .collect(Collectors.toMap(Emp::getDept, e -> Arrays.asList(e), (o, n) -> o, ConcurrentHashMap::new));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamsWithToMap() {
        Map<Integer, List<Emp>> collect = prepareEmps().stream()
                .collect(Collectors.toMap(Emp::getDept, e -> Arrays.asList(e), (o, n) -> o));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamsPartionedBy3() {
        Map<Boolean, Map<Boolean, List<Emp>>> collect = prepareEmps().stream()
                .collect(Collectors.partitioningBy(emp -> emp.getId() > 3, Collectors.partitioningBy(emp -> emp.getDept() <= 20)));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamsPartionedBy2() {
        Map<Boolean, List<String>> collect = prepareEmps().stream()
                .collect(Collectors.partitioningBy(emp -> emp.getId() > 3,Collectors.mapping(Emp::getName,Collectors.toList())));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamsPartionedBy1() {
        Map<Boolean, List<Emp>> collect = prepareEmps().stream()
                .collect(Collectors.partitioningBy(emp -> emp.getId() > 3));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamGroupingBy3() {
        Map<Integer, Map<Integer, List<Emp>>> collect = prepareEmps().stream()
                .collect(Collectors.groupingBy(Emp::getDept, Collectors.groupingBy(Emp::getId)));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamGroupingBy2() {
        Map<Integer, List<String>> collect = prepareEmps().stream()
                .collect(Collectors.groupingBy(Emp::getDept, Collectors.mapping(Emp::getName, Collectors.toList())));
        collect.entrySet().forEach(e -> System.out.println(e.getKey() + "#"+e.getValue()));
    }

    private static void streamGroupingBy1() {
        Map<Integer, List<Emp>> collect =
                prepareEmps().stream()
                .collect(Collectors.groupingBy(Emp::getDept));
        collect.entrySet().stream().forEach(e -> System.out.println(e.getKey()+"#"+e.getValue()));
    }

    private static void streamReduce() {
        IntStream.range(1,4).reduce((a,b)-> a+b).ifPresent(System.out::println); //6
        System.out.println(IntStream.range(1,4).reduce(10, (a,b)-> a+b)); //16
        //int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
        System.out.println(
                Arrays.asList(1, 2, 3).parallelStream()
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    return a + b;
                }) // 36 = 11 + 12 + 13
        );
    }

    private static void streamOfFiles() throws IOException {
        Files.lines(Paths.get("C:\\Code\\Training\\Examples\\src\\com\\sample\\example\\Test.java"))
                .forEach(System.out::println);
    }

    private static void loopEachCharactorInString() {
        System.out.println("Sarat".chars().mapToObj(e -> String.valueOf((char)e)).collect(Collectors.joining()));
    }

    private static void streamOfPrimitives() {
        IntStream.range(10,15).forEach(System.out::println);
        System.out.println();
        IntStream.rangeClosed(10,15).forEach(System.out::println);
    }

    private static void iterateStream() {
        Stream.iterate(10,e -> e+1).limit(10).forEach(System.out::println);
    }

    private static void generateSteam() {
        Stream.generate(() -> Math.random()).limit(10).forEach(System.out::println);
    }

    private static void streamOfArray() {
        String[] arr = new String[]{"x","y","z"};
        Stream stream = Stream.of(arr);
        System.out.println(stream.collect(Collectors.joining(",")));
    }

    private static void streamOfCollection() {
        List<String> names = Arrays.asList("a","b","c");
        Stream stream = names.stream();
        System.out.println(stream.collect(Collectors.joining(",")));
    }

    private static void createEmptyStream() {
        Stream.empty().forEach(System.out::println);
    }

    public static List<Emp> prepareEmps() {
        return Arrays.asList(
                new Emp(1,"sarat",20),
                new Emp(2,"chandra",20),
                new Emp(3,"bharadvaj",10),
                new Emp(4,"sudheer",10),
                new Emp(5,"srinivas",30),
                new Emp(6,"hari",30)
        );
    }
}
class Emp {
    int id;
    String name;
    int dept;
    public Emp() {
    }

    public Emp(int id, String name, int dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept=" + dept +
                '}';
    }
}
