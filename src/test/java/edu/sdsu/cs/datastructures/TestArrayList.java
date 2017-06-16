/**
 * Neha Nene
 * account:cssc0669
 * San Diego State Univeristy
 * CS310 Data Structures, Spring 2017
 * <p>
 * An example suite of tests for the ArrayList data structure implementation. These must live on the file system
 * in your project's: src/test/java/edu/sdsu/cs directory. With a properly configured Maven .pom file, these will fire
 * automatically with each build.
 */
package edu.sdsu.cs.datastructures;

import org.junit.Test;

//import java.util.ArrayList; // comment in as necessary to verify the tests work with the model data structure
import java.util.*;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class TestArrayList {

    private static final int testSize = 2048;
    private static final int valueInvalid = -1;
    private static final int valueValid = 310;

    List<Integer> sut;

    private Integer[] getSequentialIntArray(int size) {
        Integer[] values = new Integer[size];
        for (int i = 0; i < size; i++)
            values[i] = i;

        return values;
    }

    @Test
    public void constructorDefault_initializedCorrectly() {
        sut = new ArrayList<Integer>();
        assertThat(sut.isEmpty(), is(equalTo(true)));
        assertThat(sut.size(), is(equalTo(0)));
    }

    @Test
    public void constructorCollection_initializedCorrectly() {

        final Integer[] values = getSequentialIntArray(testSize);
        sut = new ArrayList<Integer>(Arrays.asList(values));

        assertThat(sut.isEmpty(), is(equalTo(false)));
        assertThat(sut.size(), is(equalTo(values.length)));
        assertThat(sut.containsAll(Arrays.asList(values)), is(equalTo(true)));
    }

    @Test
    public void add_startEmptyEndPosition_contentsCorrect() {
        sut = new ArrayList<>();
        Integer[] values = getSequentialIntArray(testSize);
        for (int position = 0; position < values.length; position++) {
            sut.add(position, values[position]);
        }


        assertThat(sut.size(), is(equalTo(values.length)));
        for (int position = 0; position < values.length; position++) {
            assertThat(sut.get(position), is(equalTo(values[position])));
        }
    }

    @Test
    public void add_startEmptyFrontPosition_contentsCorrect() {
        sut = new ArrayList<>();
        Integer[] values = getSequentialIntArray(testSize);
        for (int position = 0; position < values.length; position++) {
            sut.add(0, values[position]);
        }

        assertThat(sut.size(), is(equalTo(values.length)));
        Arrays.sort(values, Comparator.reverseOrder());
        for (int position = 0; position < values.length; position++) {
            assertThat(sut.get(position), is(equalTo(values[position])));
        }
    }

    @Test
    public void add_existingCollectionFrontPosition_contentsCorrect() {
        Integer[] values = getSequentialIntArray(testSize);
        sut = new ArrayList(Arrays.asList(values));
        for (int value = testSize - 1; value >= 0; value--) {
            sut.add(0, value);
        }

        assertThat(sut.size(), is(equalTo(testSize << 1)));
        for (int position = 0; position < testSize; position++) {
            assertThat(sut.get(position), is(equalTo(position)));
            assertThat(sut.get(position + testSize), is(equalTo(position)));
        }
    }

    @Test
    public void add_manySingleItems_correctContents() {

        final Integer inv = new Integer(valueInvalid);

        sut = new ArrayList<>();
        sut.add(valueInvalid);
        for (int i = 0; i < Integer.MAX_VALUE >> 6; i++) {
            sut.add(inv);
            sut.remove(0);
        }
        sut.remove(0);
        sut.add(new Integer(valueValid));

        assertThat(sut.size(), is(equalTo(1)));
        assertThat(sut.contains(valueValid), is(equalTo(true)));
        assertThat(sut.contains(valueInvalid), is(equalTo(false)));
    }

    @Test
    public void remove_addTwoBackRemoveOneFront_contentsCorrect() {
        sut = new ArrayList<>();
        int counter = 0;
        for (int cycle = 0; cycle < testSize; cycle++) {
            sut.add(counter++);
            sut.add(counter++);
            sut.remove(0);
        }

        assertThat(sut.size(), is(equalTo(testSize)));
        for (int i = 0; i < testSize; i++) {
            assertThat(sut.contains(i), is(equalTo(false)));
            assertThat(sut.get(i), is(equalTo(testSize + i)));
        }
    }

    @Test
    public void remove_allContents_correctRemovedElementAndSize() {
        sut = new ArrayList<>(Arrays.asList(getSequentialIntArray(testSize)));

        for (int count = 0; count < testSize; count++) {
            assertThat(sut.remove(0), is(equalTo(count)));
            assertThat(sut.contains(count), is(equalTo(false)));
            assertThat(sut.size(), is(equalTo(testSize - count - 1)));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void remove_indexBelowBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getInvalidInitializedIntArray(testSize)));
        sut.clear();
        sut.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void remove_indexAboveBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getInvalidInitializedIntArray(testSize)));
        sut.clear();
        sut.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void add_indexAboveBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getSequentialIntArray(testSize)));
        sut.clear();
        sut.add(1, valueInvalid);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void add_indexBelowBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getSequentialIntArray(testSize)));
        sut.clear();
        sut.add(-1, valueInvalid);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_indexAboveBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getInvalidInitializedIntArray(testSize)));
        sut.clear();
        sut.get(1);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_indexBelowBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getInvalidInitializedIntArray(testSize)));
        sut.clear();
        sut.get(-1);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_indexBelowBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getSequentialIntArray(testSize)));
        sut.clear();
        sut.set(-1, valueInvalid);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_indexAboveBounds_exception() {
        sut = new ArrayList<>(Arrays.asList(getSequentialIntArray(testSize)));
        sut.clear();
        sut.set(1, valueInvalid);
        fail();
    }

    @Test
    public void set_sequentialValues_contentsCorrect() {
        sut = new ArrayList<>(Arrays.asList(getInvalidInitializedIntArray(testSize)));
        for (int count = 0; count < testSize; count++) {
            sut.set(count, count);
        }

        assertThat(sut.size(), is(equalTo(testSize)));
        for (int count = 0; count < testSize; count++) {
            assertThat(sut.get(count), is(equalTo(count)));
        }
    }

    @Test
    public void remove_oddValuesFromList_nonePresent() {
        sut = new ArrayList<>(Arrays.asList(getSequentialIntArray(testSize)));
        Iterator<Integer> it = sut.iterator();
        while (it.hasNext()) {
            if (it.next() % 2 == 1) {
                it.remove();
            }
        }

        for (int i = 0; i < testSize; i += 2) {
            assertThat(sut.contains(i), is(equalTo(true)));
            assertThat(sut.contains(i + 1), is(equalTo(false)));
        }
    }

    @Test
    public void clear_middleLeavingEnds_onlyValidItems() {
        sut = new ArrayList<>(Arrays.asList(getInvalidInitializedIntArray(testSize)));
        sut.set(0, valueValid);
        sut.set(sut.size() - 1, valueValid);

        assertThat(sut.contains(valueInvalid), is(equalTo(true)));
        sut.subList(1, sut.size() - 1).clear();
        assertThat(sut.contains(valueInvalid), is(equalTo(false)));
        assertThat(sut.size(), is(equalTo(2)));
    }

    private Integer[] getInvalidInitializedIntArray(int size) {
        Integer[] values = new Integer[size];
        for (int i = 0; i < size; i++)
            values[i] = valueInvalid;

        return values;
    }
}
