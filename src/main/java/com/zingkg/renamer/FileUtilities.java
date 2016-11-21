package com.zingkg.renamer;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class FileUtilities {
    /**
     * The console command to trigger the rename function.
     */
    public static final String RENAME_COMMAND = "--rename";

    /**
     * The console command to trigger the number prepend function.
     */
    public static final String NUMBER_PREPEND_COMMAND = "--num-prepend";

    /**
     * The console command to trigger the number append function.
     */
    public static final String NUMBER_APPEND_COMMAND = "--num-append";

    /**
     * The console command to trigger the delete preceding and number prepend function.
     */
    public static final String DELETE_PRECEDING_NUM_PREPEND_COMMAND = "--del-prec-num-prep";

    /**
     * The console command to trigger the delete ending and number append function.
     */
    public static final String DELETE_ENDING_NUM_APPEND_COMMAND = "--del-end-num-app";

    /**
     * The console command to trigger the prepend string function.
     */
    public static final String PREPEND_STRING_COMMAND = "--pre-str";

    /**
     * The console command to trigger the append string function.
     */
    public static final String APPEND_STRING_COMMAND = "--app-str";

    /**
     * The console command to trigger the wipe rename and number function.
     */
    public static final String WIPE_RENAME_NUMBER_COMMAND = "--wipe-rename-number";

    /**
     * A const with all of the ASCII digits.
     */
    private static final String DIGITS = "1234567890";

    /**
     * Returns a new append path for the file given a path, name, and count.
     *
     * @param path  The path of the new file.
     * @param name  The new name of the file.
     * @param count The count for the file.
     * @return The complete path for the new file.
     */
    private static String newAppendPath(String path, String name, int count) {
        final int lastDirPos = findLastDirPos(path);
        final int extPos = path.lastIndexOf('.');
        if (extPos >= 0)
            return path.substring(0, lastDirPos + 1) + name + count + path.substring(extPos);
        else
            return path.substring(0, lastDirPos + 1) + name + count;
    }

    /**
     * Rename each of the files and return a stream with their new paths. The count is ascending.
     *
     * @param name     The name of the file(s).
     * @param startNum The starting number to be appended to each file.
     * @param files    The group of files to rename.
     * @return A stream with new files and their paths.
     */
    public static Stream<File> renameAppendAsc(String name, int startNum, Stream<String> files) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> new File(newAppendPath(file, name, count.getAndIncrement())));
    }

    /**
     * Rename each of the files and return a stream with their new paths. The count is descending.
     *
     * @param name     The name of the file(s).
     * @param startNum The starting number to be appended to each file.
     * @param files    The group of files to rename.
     * @return A stream with new files and their paths.
     */
    public static Stream<File> renameAppendDesc(String name, int startNum, Stream<String> files) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> new File(newAppendPath(file, name, count.getAndDecrement())));
    }

    /**
     * Renames a file. Uses a find name, and replacement name to give the file a new name.
     *
     * @param findName    The name to find and replace.
     * @param replaceName Replaces the findName with this name.
     * @param files       The file paths to rename.
     * @return A stream of the renamed files.
     */
    public static Stream<File> renameReplace(
        String findName,
        String replaceName,
        Stream<String> files
    ) {
        return files.filter(file -> file.contains(findName)).map(file -> {
            final int pos = file.indexOf(findName);
            return new File(
                file.substring(0, pos) + replaceName + file.substring(pos + findName.length())
            );
        });
    }

    /**
     * Numbers a batch of files. Prepends the numbers. Numbers are positively consecutive.
     *
     * @param inputString The input string to prepend to file names.
     * @param startNum    The starting number.
     * @param files       The file paths to rename.
     * @return A stream of the renamed files.
     */
    public static Stream<File> numberPrepend(
        String inputString,
        final int startNum,
        Stream<String> files
    ) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> {
            final int lastDirPos = findLastDirPos(file);
            return new File(
                file.substring(0, lastDirPos + 1) + count.getAndIncrement() + inputString +
                file.substring(lastDirPos + 1)
            );
        });
    }

    /**
     * Numbers a batch of files. Prepends the numbers. Numbers are in descending order.
     *
     * @param inputString The input string to prepend to file names.
     * @param startNum    The starting number.
     * @param files       The file paths to rename.
     * @return A stream of the renamed files.
     */
    public static Stream<File> numberPrependDesc(
        String inputString,
        final int startNum,
        Stream<String> files
    ) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> {
            final int lastDirPos = findLastDirPos(file);
            return new File(
                file.substring(0, lastDirPos + 1) + count.getAndDecrement() + inputString +
                file.substring(lastDirPos + 1)
            );
        });
    }

    /**
     * Numbers a batch of files. Numbers are positively consecutive.
     *
     * @param inputString The input string to append to the files.
     * @param startNum    The starting number.
     * @param files       The file paths to rename.
     * @return A stream with new files that are meant to be used with the old files.
     */
    public static Stream<File> numberAppend(
        String inputString,
        int startNum,
        Stream<String> files
    ) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> {
            final int dotPos = file.lastIndexOf('.');
            final String newFileName;
            if (dotPos != -1) {
                newFileName = file.substring(0, dotPos) + inputString + count.getAndIncrement() +
                file.substring(dotPos);
            } else {
                newFileName = file + inputString + count.getAndIncrement();
            }
            return new File(newFileName);
        });
    }

    /**
     * Numbers the files with an input string and a number. Each number is in descending order.
     *
     * @param inputString The input string to be appended to each file.
     * @param startNum    The starting number to start on.
     * @param files       The files meant to be renamed.
     * @return The new files for each of the old set of files.
     */
    public static Stream<File> numberAppendDesc(
        String inputString,
        int startNum,
        Stream<String> files
    ) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> {
            final int dotPos = file.lastIndexOf('.');
            final String newFileName;
            if (dotPos != -1) {
                newFileName = file.substring(0, dotPos) + inputString + count.getAndDecrement() +
                file.substring(dotPos);
            } else {
                newFileName = file + inputString + count.getAndDecrement();
            }
            return new File(newFileName);
        });
    }

    /**
     * Conduct a number prepending while also deleting a preceding number.
     *
     * @param inputString The input string to prepend to the files.
     * @param startNum    The starting number.
     * @param files       The file paths to rename.
     * @return A stream with new file paths and names.
     */
    public static Stream<File> deletePrecedingAndNumberPrepend(
        String inputString,
        int startNum,
        Stream<String> files
    ) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> {
            final int lastDirPos = findLastDirPos(file);
            final String filePath = file.substring(0, lastDirPos + 1);
            final String fileName = file.substring(lastDirPos + 1);
            final int notDigitPos = findFirstNotOf(fileName, DIGITS);
            final String moddedName = count.getAndIncrement() + inputString +
                fileName.substring(notDigitPos);
            final String newFileName = filePath + moddedName;
            return new File(newFileName);
        });
    }

    /**
     * Conduct a number appending, but remove the number before it.
     *
     * @param inputString The input string to append to the files.
     * @param startNum    The starting number.
     * @param files       The file paths to rename.
     * @return A file stream with new paths and names.
     */
    public static Stream<File> deleteEndingAndNumberAppend(
        String inputString,
        int startNum,
        Stream<String> files
    ) {
        AtomicInteger count = new AtomicInteger(startNum);
        return files.map(file -> {
            final int dotPos = file.lastIndexOf('.');
            final String fileName;
            final String extension;
            if (dotPos != -1) {
                fileName = file.substring(0, dotPos);
                extension = file.substring(dotPos);
            } else {
                fileName = file;
                extension = "";
            }
            final int numPos = findLastNotOf(fileName, DIGITS);
            final String newFileName = fileName.substring(0, numPos + 1) + inputString +
                count.getAndIncrement() + extension;
            return new File(newFileName);
        });
    }

    /**
     * Prepends the input onto the file name.
     *
     * @param inputString The input string to prepend to the files.
     * @param files       The file paths to rename.
     * @return A stream with new paths and names.
     */
    public static Stream<File> prependString(String inputString, Stream<String> files) {
        return files.map(file -> {
            final int lastDirPos = findLastDirPos(file);
            final String newFile = file.substring(0, lastDirPos + 1) + inputString +
                file.substring(lastDirPos + 1);
            return new File(newFile);
        });
    }

    /**
     * Appends the input onto the file name.
     *
     * @param inputString The input string to append to the file names.
     * @param files       The file paths to rename.
     * @return A stream of files with new paths and name.
     */
    public static Stream<File> appendString(String inputString, Stream<String> files) {
        return files.map(file -> {
            final int dotPos = file.lastIndexOf('.');
            final String newFile;
            if (dotPos != -1)
                newFile = file.substring(0, dotPos) + inputString + file.substring(dotPos);
            else
                newFile = file + inputString;

            return new File(newFile);
        });
    }

    /**
     * Wipes the input name and then replaces it and numbers.
     *
     * @param inputString The input string to replace the file name.
     * @param files       The files to rename.
     * @return A stream of files with new paths and names.
     */
    public static Stream<File> wipeRenameAndNumber(String inputString, Stream<String> files) {
        AtomicInteger count = new AtomicInteger(1);
        return files.map(file -> {
            final int dotPos = file.lastIndexOf('.');
            final String newFile;
            if (dotPos != -1)
                newFile = inputString + count.getAndIncrement() + file.substring(dotPos);
            else
                newFile = inputString + count.getAndIncrement();

            return new File(newFile);
        });
    }

    public static void renameFiles(Stream<String> currentFileStrings, Stream<File> newFiles) {
        Iterator<File> currentFileIterator = currentFileStrings.map(File::new).iterator();
        Iterator<File> newFileIterator = newFiles.iterator();
        while (currentFileIterator.hasNext() && newFileIterator.hasNext())
            currentFileIterator.next().renameTo(newFileIterator.next());
    }

    /**
     * Finds the first not of in a string from a sequence.
     * @param string   The string to be searched up.
     * @param sequence The sequence to search in the string.
     * @return The int where the position of the item was found.
     */
    private static int findFirstNotOf(String string, String sequence) {
        final int stringLength = string.length();
        char[] sequenceCharArray = sequence.toCharArray();
        for (int i = 0; i < stringLength; i++) {
            boolean found = false;
            for (final char sequenceChar : sequenceCharArray) {
                if (string.charAt(i) == sequenceChar) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return i;
        }

        return -1;
    }

    /**
     * Finds the last not of in a string from a sequence of string.
     *
     * @param string   The string to search up.
     * @param sequence The sequence to search in the string.
     * @return The int where the position of the item was found.
     */
    private static int findLastNotOf(String string, String sequence) {
        char[] sequenceCharArray = sequence.toCharArray();
        for (int i = string.length() - 1; i >= 0; i--) {
            boolean found = false;
            for (final char sequenceChar : sequenceCharArray) {
                if (string.charAt(i) == sequenceChar) {
                    found = true;
                    break;
                }
            }

            if (!found)
                return i;
        }

        return -1;
    }

    /**
     * Checks if we are on windows.
     *
     * @return True if the OS is windows. False if not on windows.
     */
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Finds the last directory position based on the OS.
     *
     * @param fileName The fileName that is to be queried on.
     * @return The integer value where the directory is found.
     */
    private static int findLastDirPos(String fileName) {
        if (isWindows())
            return fileName.lastIndexOf('\\');
        else
            return fileName.lastIndexOf('/');
    }
}
