/*
 Copyright (c) 2021 Tarek Mohamed Abdalla <tarekkma@gmail.com>

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU General Public License as published by the Free Software
 Foundation; either version 3 of the License, or (at your option) any later
 version.

 This program is distributed in the hope that it will be useful, but WITHOUT ANY
 WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ichi2.utils;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UniqueArrayListTest {


    private List<String> dupData = Arrays.asList(
            "55",
            "TEst",
            "TEst",
            "12",
            "TEst",
            "dsf23A",
            "dsf23A",
            "dsf23A",
            "dsf23A",
            "23",
            "12",
            "sd",
            "TEst",
            "55"
    );

    private List<String> noDupData = Arrays.asList(
            "55",
            "TEst",
            "12",
            "dsf23A",
            "23",
            "sd"
    );

    private <E> void assertSameLists(List<E> a, List<E> b){
        assertThat(b, IsIterableContainingInOrder.contains(a.toArray()));
    }

    private <E> void assertNotSameLists(List<E> a, List<E> b){
        assertThat(b, not(IsIterableContainingInOrder.contains(a.toArray())));
    }

    @Test
    public void testOrderIsMaintained() {
        List<Integer> longs = Arrays.asList(1, 1, 2, 3, 4, 1, 5, 1, 6, 7, 8, 9, 10, 11, 1, 12, 13);
        UniqueArrayList<Integer> uniqueList = UniqueArrayList.from(longs);

        assertTrue(uniqueList.indexOf(5) > uniqueList.indexOf(1));
    }


    @Test
    public void test_Sorting() {
        List<Integer> longs = Arrays.asList(10, 9, 7, 3, 2, -1, 5, 1, 65, -656);
        UniqueArrayList<Integer> uniqueList = UniqueArrayList.from(longs);
        Collections.sort(longs);
        uniqueList.sort();
        assertSameLists(longs, uniqueList);
    }


    @Test
    public void test_uniqueness_after_sorting() {
        List<Integer> longs = Arrays.asList(10, 9, 7, 3, 2, -1, 5, 1, 65, -656);
        UniqueArrayList<Integer> uniqueList = UniqueArrayList.from(longs);
        Collections.sort(longs);
        uniqueList.sort();
        assertSameLists(longs, uniqueList);

        uniqueList.addAll(longs);
        uniqueList.add(10);
        uniqueList.add(5, 65);

        assertSameLists(longs, uniqueList);

        uniqueList.add(575757);
        assertNotSameLists(longs, uniqueList);
    }


    @Test
    public void test_comparator() {
        List<String> list = Arrays.asList("TarekkMA", "TarekkMA", "TarekkmA", "tarekkma");
        UniqueArrayList<String> uniqueList = UniqueArrayList.from(list, String.CASE_INSENSITIVE_ORDER);

        assertEquals(1, uniqueList.size());
        assertEquals("TarekkMA", uniqueList.get(0));

        uniqueList.clear();
        Collections.reverse(list);
        uniqueList.addAll(list);

        assertEquals(1, uniqueList.size());
        assertEquals("tarekkma", uniqueList.get(0));
    }


    @Test
    public void test_add_unique_after_sorting() {
        List<Integer> longs = Arrays.asList(10, 9, 7, 3, 2, -1, 5, 1, 65, -656);
        UniqueArrayList<Integer> uniqueList = UniqueArrayList.from(longs);
        Collections.sort(longs);
        uniqueList.sort();
        assertEquals(longs, uniqueList);
        uniqueList.addAll(longs);
        assertEquals(longs, uniqueList);
    }


    @Test
    public void testFromCollection() {
        UniqueArrayList<String> uniqueArrayList = UniqueArrayList.from(dupData);
        assertEquals(noDupData, uniqueArrayList);

        uniqueArrayList = new UniqueArrayList<>();
        assertTrue(uniqueArrayList.isEmpty());
    }


    @Test
    public void testFromEmptyCollection() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();
        assertTrue(uniqueArrayList.isEmpty());
    }


    @Test
    public void test_add_not_existing() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        assertEquals(Arrays.asList("a", "Z", "f"), uniqueArrayList);
    }


    @Test
    public void test_add_existing() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        assertEquals(Arrays.asList("a", "Z", "f"), uniqueArrayList);

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");
        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");
        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        assertEquals(Arrays.asList("a", "Z", "f"), uniqueArrayList);
    }


    @Test
    public void test_set_not_existing() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");


        String res = uniqueArrayList.set(1, "m");

        assertEquals(Arrays.asList("a", "m", "f"), uniqueArrayList);
        assertEquals("Z", res);
    }


    @Test
    public void test_set_existing() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");


        String res = uniqueArrayList.set(1, "a");

        assertEquals(Arrays.asList("a", "f"), uniqueArrayList);
        assertEquals("Z", res);
    }

    @Test
    public void test_set_will_remove_replaced_item() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.set(0, "b");
        uniqueArrayList.add("a");

        assertSameLists(Arrays.asList("b", "a"), uniqueArrayList);
    }


    @Test
    public void test_addAll_no_change() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(Arrays.asList("a", "Z", "f"));

        assertEquals(Arrays.asList("a", "Z", "f"), uniqueArrayList);
        assertFalse(res);
    }


    @Test
    public void test_addAll_full_change() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(Arrays.asList("w", "x", "y"));

        assertEquals(Arrays.asList("a", "Z", "f", "w", "x", "y"), uniqueArrayList);
        assertTrue(res);
    }


    @Test
    public void test_addAll_partial_change() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(Arrays.asList("f", "Y", "Z"));

        assertEquals(Arrays.asList("a", "Z", "f", "Y"), uniqueArrayList);
        assertTrue(res);
    }


    @Test
    public void test_addAll_withIndex_no_change() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(1, Arrays.asList("a", "Z", "f"));

        assertEquals(Arrays.asList("a", "Z", "f"), uniqueArrayList);
        assertFalse(res);
    }


    @Test
    public void test_addAll_withIndex_full_change() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(1, Arrays.asList("w", "x", "y"));

        assertSameLists(Arrays.asList("a", "w", "x", "y", "Z", "f"), uniqueArrayList);
        assertTrue(res);
    }


    @Test
    public void test_addAll_withIndex_partial_change() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(1, Arrays.asList("f", "Y", "Z"));

        assertSameLists(Arrays.asList("a", "Y", "Z", "f"), uniqueArrayList);
        assertTrue(res);
    }


    @Test
    public void test_addAll_withIndex_last_position() {
        UniqueArrayList<String> uniqueArrayList = new UniqueArrayList<>();

        uniqueArrayList.add("a");
        uniqueArrayList.add("Z");
        uniqueArrayList.add("f");

        boolean res = uniqueArrayList.addAll(uniqueArrayList.size(), Arrays.asList("w", "x", "y"));

        assertSameLists(Arrays.asList("a", "Z", "f", "w", "x", "y"), uniqueArrayList);
        assertTrue(res);
    }


    @Test
    public void test_addAll_order_1() {
        UniqueArrayList<Integer> uniqueArrayList = new UniqueArrayList<>();

        final List<Integer> l1 = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(uniqueArrayList.addAll(l1));
        assertSameLists(l1, uniqueArrayList);

        final List<Integer> l2 = Arrays.asList(5, 4, 3, 2, 1, 0);
        assertTrue(uniqueArrayList.addAll(0,l2));

        final List<Integer> l3 = Arrays.asList(0, 1, 2, 3, 4, 5);
        assertSameLists(l3, uniqueArrayList);
    }


    @Test
    public void test_addAll_order_2() {
        UniqueArrayList<Integer> uniqueArrayList = new UniqueArrayList<>();

        final List<Integer> l1 = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(uniqueArrayList.addAll(l1));
        assertSameLists(l1, uniqueArrayList);

        final List<Integer> l2 = Arrays.asList(0, 1, 2, 3, 4, 5);
        assertTrue(uniqueArrayList.addAll(0,l2));

        assertSameLists(l2, uniqueArrayList);
    }


    @Test
    public void test_clear() {
        List<Integer> longs = Arrays.asList(1, 1, 2, 3, 4, 1, 5, 1, 6, 7, 8, 9, 10, 11, 1, 12, 13);
        UniqueArrayList<Integer> uniqueList = UniqueArrayList.from(longs);

        assertFalse(uniqueList.isEmpty());
        uniqueList.clear();
        assertTrue(uniqueList.isEmpty());
        uniqueList.addAll(longs);
        assertFalse(uniqueList.isEmpty());
    }


    @Test
    public void test_remove_object() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertNotEquals(-1, uniqueList.indexOf(1L));
        uniqueList.remove(1L);
        assertEquals(-1, uniqueList.indexOf(1L));
        uniqueList.set(10, 1L);
        assertEquals(10, uniqueList.indexOf(1L));
    }


    @Test
    public void test_remove_index() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertNotEquals(-1, uniqueList.indexOf(1L));
        uniqueList.remove(0/*index of 1L*/);
        assertEquals(-1, uniqueList.indexOf(1L));
        uniqueList.set(10, 1L);
        assertEquals(10, uniqueList.indexOf(1L));
    }


    @Test
    public void test_contain() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertTrue(uniqueList.contains(1L));
        assertFalse(uniqueList.contains(1502L));
    }


    @Test
    public void test_get_by_index() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertEquals((Long) 1L, uniqueList.get(0));
        assertNotEquals((Long) 1L, uniqueList.get(1));


        assertEquals((Long) 13L, uniqueList.get(12));
        assertEquals((Long) 12L, uniqueList.get(11));
    }


    @Test
    public void test_indexOf_and_lastIndexOf() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertEquals(0, uniqueList.indexOf(1L));
        assertEquals(5, uniqueList.indexOf(6L));
        assertEquals(12, uniqueList.indexOf(13L));
        assertEquals(9, uniqueList.indexOf(10L));
        assertEquals(2, uniqueList.indexOf(3L));

        assertEquals(0, uniqueList.lastIndexOf(1L));
        assertEquals(5, uniqueList.lastIndexOf(6L));
        assertEquals(12, uniqueList.lastIndexOf(13L));
        assertEquals(9, uniqueList.lastIndexOf(10L));
        assertEquals(2, uniqueList.lastIndexOf(3L));
    }


    @Test
    public void test_iterator() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        List<Long> list = new ArrayList<>();
        uniqueList.iterator().forEachRemaining(list::add);

        assertEquals(Arrays.asList(
                1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L
        ), list);
    }


    @Test
    public void test_listIterator() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        List<Long> list = new ArrayList<>();
        uniqueList.listIterator().forEachRemaining(list::add);

        assertEquals(Arrays.asList(
                1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L
        ), list);
    }


    @Test
    public void test_listIterator_index() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        List<Long> list = new ArrayList<>();
        uniqueList.listIterator(5).forEachRemaining(list::add);

        assertEquals(Arrays.asList(
                6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L
        ), list);
    }


    @Test
    public void test_subList() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertEquals(Arrays.asList(
                6L, 7L, 8L, 9L, 10L
        ), uniqueList.subList(5, 10));
    }


    @Test
    public void test_containsAll() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertTrue(uniqueList.containsAll(Arrays.asList(1L, 10L, 13L)));
        assertFalse(uniqueList.containsAll(Arrays.asList(1L, 130L, 13L)));
        assertFalse(uniqueList.containsAll(Arrays.asList(-1L, 130L, 1003L)));
    }


    @Test
    public void test_retainAll() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertNotEquals(2, uniqueList.size());
        uniqueList.retainAll(Arrays.asList(1L, 3L));
        assertEquals(2, uniqueList.size());
        assertEquals(Arrays.asList(1L, 3L), uniqueList);
    }


    @Test
    public void test_isEmpty() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        assertFalse(uniqueList.isEmpty());
        uniqueList.removeAll(Arrays.asList(
                1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L
        ));
        assertTrue(uniqueList.isEmpty());
    }


    @Test
    public void test_toArray() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        final Object[] arr = Arrays.asList(
                1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L
        ).toArray();

        assertArrayEquals(arr, uniqueList.toArray());
    }


    @Test
    public void test_toArrayType() {
        List<Long> longs = Arrays.asList(1L, 1L, 2L, 3L, 4L, 1L, 5L, 1L, 6L, 7L, 8L, 9L, 10L, 11L, 1L, 12L, 13L);
        UniqueArrayList<Long> uniqueList = UniqueArrayList.from(longs);

        final Long[] arr = Arrays.asList(
                1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L
        ).toArray(new Long[] {});

        final Long[] arr_res = uniqueList.toArray(new Long[] {});
        assertArrayEquals(arr, arr_res);

        assertThat(arr_res[0], instanceOf(Long.class));
    }
}