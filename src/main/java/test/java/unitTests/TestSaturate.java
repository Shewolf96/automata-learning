package test.java.unitTests;

import algorithm.Saturate;
import automata.InfiniteWordGenerator;
import org.junit.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public void testEmptyClosure() {
        HashSet<InfiniteWordGenerator> actualSet = new HashSet<>();
        Assert.assertTrue(mutuallyContains(super.getClosure(actualSet), new HashSet<>()));
    }

    @Test
    public void testSingletonClosure() {
        HashSet<InfiniteWordGenerator> actualSet = new HashSet<>();
        actualSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"a"}));
        Assert.assertTrue(mutuallyContains(super.getClosure(actualSet), actualSet));
    }

    @Test
    public void testClosure1() {
        HashSet<InfiniteWordGenerator> actualSet = new HashSet<>();
        actualSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"a", "b"}));
        actualSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"c", "d"}));
        actualSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"b", "a"}));

        HashSet<InfiniteWordGenerator> expectedSet = new HashSet<>(actualSet);
        expectedSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"d", "c"}));

        Assert.assertTrue(mutuallyContains(super.getClosure(actualSet), expectedSet));

    }

    @Test
    public void testClosure2() {
        HashSet<InfiniteWordGenerator> actualSet = new HashSet<>();
        actualSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"a", "b", "c"}));
        actualSet.add(new InfiniteWordGenerator(new String [] {"a"}, new String [] {"a"}));
        actualSet.add(new InfiniteWordGenerator(new String [] {"a", "b", "c"}, new String [] {"z", "z"}));

        HashSet<InfiniteWordGenerator> expectedSet = new HashSet<>(actualSet);
        expectedSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"b", "c", "a"}));
        expectedSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"c", "a", "b"}));
        expectedSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"a"}));
        expectedSet.add(new InfiniteWordGenerator(new String [] {"b", "c"}, new String [] {"z", "z"}));
        expectedSet.add(new InfiniteWordGenerator(new String [] {"c"}, new String [] {"z", "z"}));
        expectedSet.add(new InfiniteWordGenerator(new String [] {}, new String [] {"z", "z"}));

        Assert.assertTrue(mutuallyContains(super.getClosure(actualSet), expectedSet));

    }

    private <E> boolean mutuallyContains(Set<E> set1, Set<E> set2) {
        List<E> list1 = set1.stream().collect(Collectors.toList());
        List<E> list2 = set2.stream().collect(Collectors.toList());

        return list1.containsAll(list2) && list2.containsAll(list1);
    }

}
