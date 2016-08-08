package renamer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the command line class.
 */
public class CommandLineJUnitTest {
    /**
     * Ensures that help returns program success.
     */
    @Test
    public void helpTest() {
        String[] args = new String[1];
        args[0] = "--help";
        assertTrue(CommandLine.main(args) == CommandLine.SUCCESS);
    }

    /**
     * Ensures that the rename test will return an error.
     */
    @Test
    public void lowArgsRenameTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.RENAME_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that the number prepend test will an error.
     */
    @Test
    public void lowArgsNumberPrependTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.NUMBER_PREPEND_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that the number append test will return an error.
     */
    @Test
    public void lowArgsNumberAppendTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.NUMBER_APPEND_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that the delete preceding and number prepend will return an error.
     */
    @Test
    public void lowArgsDeletePrecedingNumberPrependTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.DELETE_PRECEDING_NUM_PREPEND_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that the delete ending and number append will return an error.
     */
    @Test
    public void lowArgsDeleteEndingNumberAppendTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.DELETE_ENDING_NUM_APPEND_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that prepending a file name without enough arguments returns an error.
     */
    @Test
    public void lowArgsPrependStringTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.PREPEND_STRING_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that appending a file name without enough arguments returns 1.
     */
    @Test
    public void lowArgsAppendStringTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.APPEND_STRING_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }

    /**
     * Ensures that wiping and renaming a file name without enough arguments returns 1.
     */
    @Test
    public void lowArgsWipeRenameAndNumberTest() {
        String[] args = new String[1];
        args[0] = FileUtilities.WIPE_RENAME_NUMBER_COMMAND;
        assertTrue(CommandLine.main(args) == CommandLine.ERROR);
    }
}
