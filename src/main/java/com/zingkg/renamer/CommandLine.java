package com.zingkg.renamer;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main class for running the program as a command line application.
 */
public class CommandLine {
    public static int ERROR = 1;
    public static int SUCCESS = 0;

    /**
     * @param args the command line arguments.
     * @return Success of operation.
     */
    public static int main(String[] args) {
        if (args.length < 1) {
            System.err.println("--help for more information");
            return ERROR;
        }

        switch (args[0]) {
            case "--help":
                printHelp();
                return SUCCESS;
            case FileUtilities.RENAME_COMMAND:
                return rename(args);
            case FileUtilities.WIPE_RENAME_NUMBER_COMMAND:
                return wipeRenameNumber(args);
            case FileUtilities.NUMBER_PREPEND_COMMAND:
                return numberPrepend(args);
            case FileUtilities.NUMBER_APPEND_COMMAND:
                return numberAppend(args);
            case FileUtilities.DELETE_PRECEDING_NUM_PREPEND_COMMAND:
                return deletePrecedingNumPrepend(args);
            case FileUtilities.DELETE_ENDING_NUM_APPEND_COMMAND:
                return deleteEndingNumAppend(args);
            case FileUtilities.PREPEND_STRING_COMMAND:
                return prepend(args);
            case FileUtilities.APPEND_STRING_COMMAND:
                return append(args);
            default:
                return ERROR;
        }
    }

    /**
     * Prints help to be displayed on the command line.
     */
    private static void printHelp() {
        System.out.println("usage: renamer command <args> <files>");
        System.out.println("Commands are:");
        System.out.println();

        System.out.println("Basic renamer program");
        System.out.println(
            '\t' + FileUtilities.RENAME_COMMAND + "\tReplaces a string in the file with another"
        );
        System.out.println(
            '\t' + FileUtilities.WIPE_RENAME_NUMBER_COMMAND + "\tWipes the file's name, uses " + 
            "the input name, and numbers the files starting at 1"
        );
        System.out.println();

        System.out.println("Adding a name and numbering the files");
        System.out.println(
            '\t' + FileUtilities.NUMBER_PREPEND_COMMAND + "\tPrepends a string and a number to " +
            "the file"
        );
        System.out.println(
            '\t' + FileUtilities.NUMBER_APPEND_COMMAND + "\tAppends a string and a number to the " +
            "file"
        );
        System.out.println();

        System.out.println("Delete a number, add a name, and number the files");
        System.out.println(
            '\t' + FileUtilities.DELETE_PRECEDING_NUM_PREPEND_COMMAND + "\tDeletes a preceding " +
            "number and prepends a number to the file"
        );
        System.out.println(
            '\t' + FileUtilities.DELETE_ENDING_NUM_APPEND_COMMAND + "\tDeletes an ending number " +
            "and appends a number to the file"
        );
        System.out.println();

        System.out.println("Add a name to the file name");
        System.out.println(
            '\t' + FileUtilities.PREPEND_STRING_COMMAND + "\tPrepends a string to the file(s)"
        );
        System.out.println(
            '\t' + FileUtilities.APPEND_STRING_COMMAND + "\tAppends a string to the file(s)"
        );
    }

    /**
     * Gets the input string.
     *
     * @param args The command line arguments array.
     * @return The input string on the arguments.
     */
    private static String getInputString(String[] args) {
        return args[1];
    }

    /**
     * Gets the starting number part of the command line arguments.
     *
     * @param args The command line arguments array.
     * @return The starting number in the arguments.
     */
    private static int getStartingNumber(String[] args) {
        return Integer.parseInt(args[2]);
    }

    private static int rename(String[] args) {
        if (args.length <= 3) {
            System.out.println(
                FileUtilities.RENAME_COMMAND + " requires arguments: <find name> <replace name> " +
                "<files>"
            );
            return ERROR;
        }

        List<String> files = getFiles(3, args).collect(Collectors.toList());
        FileUtilities.renameFiles(
            files.stream(),
            FileUtilities.renameReplace(args[1], args[2], files.stream())
        );
        return SUCCESS;
    }

    private static int wipeRenameNumber(String[] args) {
        if (args.length <= 2) {
            System.out.println(
                FileUtilities.WIPE_RENAME_NUMBER_COMMAND + " requires arguments: <input string> " +
                "<files>"
            );
            return ERROR;
        }

        List<String> files = getFiles(2, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.wipeRenameAndNumber(
            getInputString(args),
            files.stream()
        );
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    private static int numberPrepend(String[] args) {
        if (args.length <= 3) {
            System.out.println(
                FileUtilities.NUMBER_PREPEND_COMMAND + " requires arguments: <input string> " +
                "<starting number> <files>"
            );
            return ERROR;
        }

        List<String> files = getFiles(3, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.numberPrepend(
            getInputString(args),
            getStartingNumber(args),
            files.stream()
        );
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    private static int numberAppend(String[] args) {
        if (args.length <= 3) {
            System.out.println(
                FileUtilities.NUMBER_APPEND_COMMAND + " requires arguments: <input string> " +
                "<starting number> <files>"
            );
            return ERROR;
        }

        List<String> files = getFiles(3, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.numberAppend(
            getInputString(args),
            getStartingNumber(args),
            files.stream()
        );
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    private static int deletePrecedingNumPrepend(String[] args) {
        if (args.length <= 3) {
            System.out.println(
                FileUtilities.DELETE_PRECEDING_NUM_PREPEND_COMMAND + " requires arguments: " +
                "<input string> <starting number> <files>"
            );
            return ERROR;
        }

        final int startNum = Integer.parseInt(args[2]);
        List<String> files = getFiles(3, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.deletePrecedingAndNumberPrepend(
            getInputString(args),
            startNum,
            files.stream()
        );
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    private static int deleteEndingNumAppend(String[] args) {
        if (args.length <= 3) {
            System.out.println(
                FileUtilities.DELETE_ENDING_NUM_APPEND_COMMAND + " requires arguments: " +
                "<input string> <starting number> <files>"
            );
            return ERROR;
        }

        final int startNum = Integer.parseInt(args[2]);
        List<String> files = getFiles(3, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.deleteEndingAndNumberAppend(
            getInputString(args),
            startNum,
            files.stream()
        );
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    private static int prepend(String[] args) {
        if (args.length <= 2) {
            System.out.println(
                FileUtilities.PREPEND_STRING_COMMAND + " requires arguments: <input string> " +
                "<files>"
            );
            return ERROR;
        }

        List<String> files = getFiles(2, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.prependString(getInputString(args), files.stream());
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    private static int append(String[] args) {
        if (args.length <= 2) {
            System.out.println(
                FileUtilities.APPEND_STRING_COMMAND + " requires arguments: " +
                "<input string> <files>"
            );
            return ERROR;
        }

        List<String> files = getFiles(2, args).collect(Collectors.toList());
        Stream<File> newFiles = FileUtilities.appendString(getInputString(args), files.stream());
        FileUtilities.renameFiles(files.stream(), newFiles);
        return SUCCESS;
    }

    /**
     * Gets the files in the command line.
     *
     * @param start
     *     The start of the command lines to start accumulating file names.
     * @param args
     *     The arguments in the command line.
     * @return A list with all of the files extracted from the command line.
     */
    private static Stream<String> getFiles(final int start, String[] args) {
        return Arrays.stream(args, start, args.length);
    }
}
