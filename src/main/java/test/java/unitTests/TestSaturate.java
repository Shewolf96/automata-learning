package test.java.unitTests;

import algorithm.Saturate;
import automata.InfiniteWordGenerator;
import org.junit.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class TestSaturate extends Saturate {

    @BeforeClass
    public static void setUp() {
        System.out.println("setting up TestSaturate");
    }

    @AfterClass
    public static void finished() {
        System.out.println("TestSaturate finished");
    }

    @Test
    public void testEmptyRotation() {
        InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(new String [] {}, new String [] {});
        Assert.assertArrayEquals(super.getRotations(infiniteWord).toArray(), new HashSet<>().toArray());
    }

    @Test
    public void testSimpleRotation() {
        InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(new String [] {"a"}, new String [] {"b"});
        InfiniteWordGenerator rotation = new InfiniteWordGenerator(new String [] {}, new String [] {"b"});

        HashSet<InfiniteWordGenerator> rotations = new HashSet<>();
        rotations.add(rotation);
        rotations.add(infiniteWord);

        Assert.assertTrue(mutuallyContains(super.getRotations(infiniteWord), rotations));
    }

    @Test
    public void testSimpleRotation2() {
        InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(new String [] {"a", "a", "b"}, new String [] {"b", "a"});

        HashSet<InfiniteWordGenerator> rotations = new HashSet<>();
        rotations.add(infiniteWord);
        rotations.add(new InfiniteWordGenerator(new String [] {"a", "b"}, new String [] {"b", "a"}));
        rotations.add(new InfiniteWordGenerator(new String [] {"b"}, new String [] {"b", "a"}));
        rotations.add(new InfiniteWordGenerator(new String [] {}, new String [] {"a", "b"}));
        rotations.add(new InfiniteWordGenerator(new String [] {}, new String [] {"b", "a"}));

        Assert.assertTrue(mutuallyContains(super.getRotations(infiniteWord), rotations));
//        super.getRotations(infiniteWord).forEach(word -> Assert.assertTrue(rotations.contains(word)));
    }

    @Test
    public void testComplexRotation() {
        InfiniteWordGenerator infiniteWord = new InfiniteWordGenerator(new String [] {}, new String [] {"a", "b", "c", "c"});

        HashSet<InfiniteWordGenerator> rotations = new HashSet<>();
        rotations.add(infiniteWord);
        rotations.add(new InfiniteWordGenerator(new String [] {}, new String [] {"c", "a", "b", "c"}));
        rotations.add(new InfiniteWordGenerator(new String [] {}, new String [] {"c", "c", "a", "b"}));
        rotations.add(new InfiniteWordGenerator(new String [] {}, new String [] {"b", "c", "c", "a"}));

        Assert.assertTrue(mutuallyContains(super.getRotations(infiniteWord), rotations));
    }

    @Test
    public void testClosure() {

    }

    private <E> boolean mutuallyContains(HashSet<E> set1, HashSet<E> set2) {
        List<E> list1 = set1.stream().collect(Collectors.toList());
        List<E> list2 = set2.stream().collect(Collectors.toList());

        return list1.containsAll(list2) && list2.containsAll(list1);
    }

//    @Test
//    void groupAssertions() {
//        int[] numbers = {0, 1, 2, 3, 4};
//        assertAll("numbers",
//                () -> assertEquals(numbers[0], 1),
//                () -> assertEquals(numbers[3], 3),
//                () -> assertEquals(numbers[4], 1)
//        );
//    }


}
