package com.zingkg.renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RenamerJUnitTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    /**
     * Tests the rename function.
     */
    @Test
    public void renameTest() throws Exception {
        List<File> renamedFiles = FileUtilities.renameReplace("test", "break", createExtFiles());
        assertEquals(renamedFiles.get(0).getName(), "breakfile1.txt");
        assertEquals(renamedFiles.get(1).getName(), "breakfile2.txt");
        assertEquals(renamedFiles.get(2).getName(), "breakfile3.txt");
        assertEquals(renamedFiles.get(3).getName(), "breakfile4.txt");
        assertEquals(renamedFiles.get(4).getName(), "breakfile5.txt");
    }

    /**
     * Tests the wipe rename and number function.
     */
    @Test
    public void wipeRenameAndNumberTest() throws Exception {
        List<File> renamedFiles = FileUtilities.wipeRenameAndNumber("vacation-", createExtFiles());
        assertEquals(renamedFiles.get(0).getName(), "vacation-1.txt");
        assertEquals(renamedFiles.get(1).getName(), "vacation-2.txt");
        assertEquals(renamedFiles.get(2).getName(), "vacation-3.txt");
        assertEquals(renamedFiles.get(3).getName(), "vacation-4.txt");
        assertEquals(renamedFiles.get(4).getName(), "vacation-5.txt");

        List<File> renamedNoExtFiles = FileUtilities.wipeRenameAndNumber(
            "vacation-",
            createNoExtFiles()
        );
        assertEquals(renamedNoExtFiles.get(0).getName(), "vacation-1");
        assertEquals(renamedNoExtFiles.get(1).getName(), "vacation-2");
        assertEquals(renamedNoExtFiles.get(2).getName(), "vacation-3");
        assertEquals(renamedNoExtFiles.get(3).getName(), "vacation-4");
        assertEquals(renamedNoExtFiles.get(4).getName(), "vacation-5");
    }

    /**
     * Tests the number prepend function.
     */
    @Test
    public void numberPrependTest() throws Exception {
        List<File> renamedFiles = FileUtilities.numberPrepend("-", 5, createExtFiles());
        assertEquals(renamedFiles.get(0).getName(), "5-testfile1.txt");
        assertEquals(renamedFiles.get(1).getName(), "6-testfile2.txt");
        assertEquals(renamedFiles.get(2).getName(), "7-testfile3.txt");
        assertEquals(renamedFiles.get(3).getName(), "8-testfile4.txt");
        assertEquals(renamedFiles.get(4).getName(), "9-testfile5.txt");
    }

    /**
     * Tests the number append function.
     */
    @Test
    public void numberAppendTest() throws Exception {
        List<File> renamedFiles = FileUtilities.numberAppend("__", 10, createExtFiles());
        assertEquals(renamedFiles.get(0).getName(), "testfile1__10.txt");
        assertEquals(renamedFiles.get(1).getName(), "testfile2__11.txt");
        assertEquals(renamedFiles.get(2).getName(), "testfile3__12.txt");
        assertEquals(renamedFiles.get(3).getName(), "testfile4__13.txt");
        assertEquals(renamedFiles.get(4).getName(), "testfile5__14.txt");

        List<File> renamedNoExtFiles = FileUtilities.numberAppend("__", 10, createNoExtFiles());
        assertEquals(renamedNoExtFiles.get(0).getName(), "testfile1__10");
        assertEquals(renamedNoExtFiles.get(1).getName(), "testfile2__11");
        assertEquals(renamedNoExtFiles.get(2).getName(), "testfile3__12");
        assertEquals(renamedNoExtFiles.get(3).getName(), "testfile4__13");
        assertEquals(renamedNoExtFiles.get(4).getName(), "testfile5__14");
    }

    /**
     * Tests the delete preceding number and prepend function.
     */
    @Test
    public void deletePrecedingAndNumberPrependTest() throws Exception {
        List<File> renamedFiles = FileUtilities.deletePrecedingAndNumberPrepend(
            "~",
            50,
            createNoExtFiles()
        );
        assertEquals(renamedFiles.get(0).getName(), "50~testfile1");
        assertEquals(renamedFiles.get(1).getName(), "51~testfile2");
        assertEquals(renamedFiles.get(2).getName(), "52~testfile3");
        assertEquals(renamedFiles.get(3).getName(), "53~testfile4");
        assertEquals(renamedFiles.get(4).getName(), "54~testfile5");
    }

    /**
     * Tests the delete ending number and append function.
     */
    @Test
    public void deleteEndingAndNumberAppendTest() throws Exception {
        List<File> renamedFiles = FileUtilities.deleteEndingAndNumberAppend(
            ",",
            100,
            createExtFiles()
        );
        assertEquals(renamedFiles.get(0).getName(), "testfile,100.txt");
        assertEquals(renamedFiles.get(1).getName(), "testfile,101.txt");
        assertEquals(renamedFiles.get(2).getName(), "testfile,102.txt");
        assertEquals(renamedFiles.get(3).getName(), "testfile,103.txt");
        assertEquals(renamedFiles.get(4).getName(), "testfile,104.txt");

        List<File> renamedNoExtFiles = FileUtilities.deleteEndingAndNumberAppend(
            ",",
            100,
            createNoExtFiles()
        );
        assertEquals(renamedNoExtFiles.get(0).getName(), "testfile,100");
        assertEquals(renamedNoExtFiles.get(1).getName(), "testfile,101");
        assertEquals(renamedNoExtFiles.get(2).getName(), "testfile,102");
        assertEquals(renamedNoExtFiles.get(3).getName(), "testfile,103");
        assertEquals(renamedNoExtFiles.get(4).getName(), "testfile,104");
    }

    /**
     * Tests the prepend function.
     */
    @Test
    public void prependStringTest() throws Exception {
        List<File> renamedFiles = FileUtilities.prependString("hi-", createNoExtFiles());
        assertEquals(renamedFiles.get(0).getName(), "hi-testfile1");
        assertEquals(renamedFiles.get(1).getName(), "hi-testfile2");
        assertEquals(renamedFiles.get(2).getName(), "hi-testfile3");
        assertEquals(renamedFiles.get(3).getName(), "hi-testfile4");
        assertEquals(renamedFiles.get(4).getName(), "hi-testfile5");
    }

    /**
     * Tests the append function.
     */
    @Test
    public void appendStringTest() throws Exception {
        List<File> renamedFiles = FileUtilities.appendString(".bye", createExtFiles());
        assertEquals(renamedFiles.get(0).getName(), "testfile1.bye.txt");
        assertEquals(renamedFiles.get(1).getName(), "testfile2.bye.txt");
        assertEquals(renamedFiles.get(2).getName(), "testfile3.bye.txt");
        assertEquals(renamedFiles.get(3).getName(), "testfile4.bye.txt");
        assertEquals(renamedFiles.get(4).getName(), "testfile5.bye.txt");

        List<File> renamedNoExtFiles = FileUtilities.appendString(".bye", createNoExtFiles());
        assertEquals(renamedNoExtFiles.get(0).getName(), "testfile1.bye");
        assertEquals(renamedNoExtFiles.get(1).getName(), "testfile2.bye");
        assertEquals(renamedNoExtFiles.get(2).getName(), "testfile3.bye");
        assertEquals(renamedNoExtFiles.get(3).getName(), "testfile4.bye");
        assertEquals(renamedNoExtFiles.get(4).getName(), "testfile5.bye");
    }

    /**
     * Creates files that have extensions.
     *
     * @return A list of strings that has file names that have extensions.
     */
    private static List<String> createExtFiles() throws Exception {
        return new ArrayList<>(
            Arrays.asList(
                "testfile1.txt",
                "testfile2.txt",
                "testfile3.txt",
                "testfile4.txt",
                "testfile5.txt"
            )
        );
    }

    /**
     * Creates files that have no extensions.
     *
     * @return A list of strings that have file names that have no extensions.
     */
    private static List<String> createNoExtFiles() throws Exception {
        return new ArrayList<>(
            Arrays.asList(
                "testfile1",
                "testfile2",
                "testfile3",
                "testfile4",
                "testfile5"
            )
        );
    }
}
