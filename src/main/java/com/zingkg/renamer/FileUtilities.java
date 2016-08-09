package com.zingkg.renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private static String newAppendPath(String path, String name, int count) {
        final int lastDirPos = findLastDirPos(path);
        final int extPos = path.lastIndexOf('.');
        if (extPos >= 0)
            return path.substring(0, lastDirPos + 1) + name + count + path.substring(extPos);
        else
            return path.substring(0, lastDirPos + 1) + name + count;
    }

    public static List<File> renameAppendAsc(String name, int count, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        for (final String file : files) {
            final String newFileName = newAppendPath(file, name, count);
            newFiles.add(newFileLocation(newFileName));
            count++;
        }
        return newFiles;
    }

    public static List<File> renameAppendDesc(String name, int count, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        for (final String file : files) {
            final String newFileName = name + count;
            newFiles.add(newFileLocation(newFileName));
            count--;
        }
        return newFiles;
    }

    /**
     * Renames a file. Uses a find name, and replacement name to give the file a new name.
     *
     * @param findName
     *     The name to find and replace.
     * @param replaceName
     *     Replaces the findName with this name.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> renameReplace(
        String findName,
        String replaceName,
        List<String> files
    ) {
        List<File> newFiles = new ArrayList<>();
        for (final String file : files) {
            final int pos = file.indexOf(findName);
            if (pos != -1) {
                final String newFileName = file.substring(0, pos) + replaceName +
                    file.substring(pos + findName.length());
                newFiles.add(newFileLocation(newFileName));
            }
        }
        return newFiles;
    }

    /**
     * Numbers a batch of files. Prepends the numbers. Numbers are positively consecutive.
     *
     * @param inputString
     *     The input string to prepend to file names.
     * @param startNum
     *     The starting number.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> numberPrepend(String inputString, int startNum, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        int count = startNum;
        for (final String file : files) {
            final String insertString = count + inputString;
            final int lastDirPos = findLastDirPos(file);
            final String newFileName = file.substring(0, lastDirPos + 1) + insertString +
                file.substring(lastDirPos + 1);
            count++;
            newFiles.add(newFileLocation(newFileName));
        }
        return newFiles;
    }

    /**
     * Numbers a batch of files. Numbers are positively consecutive.
     *
     * @param inputString
     *     The input string to append to the files.
     * @param startNum
     *     The starting number.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> numberAppend(String inputString, int startNum, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        int count = startNum;
        for (final String file : files) {
            final int dotPos = file.lastIndexOf('.');
            final String newFileName;
            if (dotPos != -1) {
                newFileName = file.substring(0, dotPos) + inputString + count +
                file.substring(dotPos);
            } else {
                newFileName = file + inputString + count;
            }

            count++;
            newFiles.add(newFileLocation(newFileName));
        }
        return newFiles;
    }

    /**
     * Conduct a number prepending while also deleting a preceding number.
     *
     * @param inputString
     *     The input string to prepend to the files.
     * @param startNum
     *     The starting number.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> deletePrecedingAndNumberPrepend(
        String inputString,
        int startNum,
        List<String> files
    ) {
        List<File> newFiles = new ArrayList<>();
        int count = startNum;
        for (final String file : files) {
            final int lastDirPos = findLastDirPos(file);
            final String filePath = file.substring(0, lastDirPos + 1);
            final String fileName = file.substring(lastDirPos + 1);
            final int notDigitPos = findFirstNotOf(fileName, DIGITS);
            final String moddedName = count + inputString + fileName.substring(notDigitPos);
            final String newFileName = filePath + moddedName;
            count++;
            newFiles.add(newFileLocation(newFileName));
        }
        return newFiles;
    }

    /**
     * Conduct a number appending, but remove the number before it.
     *
     * @param inputString
     *     The input string to append to the files.
     * @param startNum
     *     The starting number.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> deleteEndingAndNumberAppend(
        String inputString,
        int startNum,
        List<String> files
    ) {
        List<File> newFiles = new ArrayList<>();
        int count = startNum;
        for (final String file : files) {
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
            final String newFileName = fileName.substring(0, numPos + 1) + inputString + count +
                extension;
            count++;
            newFiles.add(newFileLocation(newFileName));
        }
        return newFiles;
    }

    /**
     * Prepends the input onto the file name.
     *
     * @param inputString
     *     The input string to prepend to the files.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> prependString(String inputString, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        for (final String file : files) {
            final int lastDirPos = findLastDirPos(file);
            final String newFile = file.substring(0, lastDirPos + 1) + inputString +
                file.substring(lastDirPos + 1);
            newFiles.add(newFileLocation(newFile));
        }
        return newFiles;
    }

    /**
     * Appends the input onto the file name.
     *
     * @param inputString
     *     The input string to append to the file names.
     * @param files
     *     The file paths to rename.
     */
    public static List<File> appendString(String inputString, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        for (final String file : files) {
            final int dotPos = file.lastIndexOf('.');
            final String newFile;
            if (dotPos != -1)
                newFile = file.substring(0, dotPos) + inputString + file.substring(dotPos);
            else
                newFile = file + inputString;

            newFiles.add(newFileLocation(newFile));
        }
        return newFiles;
    }

    /**
     * Wipes the input name and then replaces it and numbers.
     *
     * @param inputString
     *     The input string to replace the file name.
     * @param files
     *     The files to rename.
     */
    public static List<File> wipeRenameAndNumber(String inputString, List<String> files) {
        List<File> newFiles = new ArrayList<>();
        int count = 1;
        for (final String file : files) {
            final int dotPos = file.lastIndexOf('.');
            final String newFile;
            if (dotPos != -1)
                newFile = inputString + count + file.substring(dotPos);
            else
                newFile = inputString + count;
            newFiles.add(newFileLocation(newFile));
            count++;
        }
        return newFiles;
    }

    public static File newFileLocation(String newFilePath) {
        return new File(newFilePath);
    }

    public static void renameFiles(List<String> currentFileStrings, List<File> newFiles) {
        List<File> currentFiles = new ArrayList<>();
        for (String string : currentFileStrings) {
            currentFiles.add(new File(string));
        }

        final int length = currentFiles.size();
        for (int i = 0; i < length; i++)
            renameFile(currentFiles.get(i), newFiles.get(i));
    }

    private static void renameFile(File currentFile, File newFile) {
        currentFile.renameTo(newFile);
    }

    /**
     * Finds the first not of in a string from a sequence.
     * @param string
     *     The string to be searched up.
     * @param sequence
     *     The sequence to search in the string.
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
     * @param string
     *     The string to search up.
     * @param sequence
     *     The sequence to search in the string.
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
     * @param fileName
     *     The fileName that is to be queried on.
     * @return The integer value where the directory is found.
     */
    private static int findLastDirPos(String fileName) {
        if (isWindows())
            return fileName.lastIndexOf('\\');

        // Unix.
        return fileName.lastIndexOf('/');
    }
}
