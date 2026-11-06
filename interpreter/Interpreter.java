package interpreter;

import java.io.*;
import java.util.*;
import parser.ParserWrapper;
import ast.Program;

public class Interpreter {

    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_PARSING_ERROR = 1;
    public static final int EXIT_STATIC_CHECKING_ERROR = 2;
    public static final int EXIT_UNINITIALIZED_VAR_ERROR = 3;
    public static final int EXIT_DIV_BY_ZERO_ERROR = 4;
    public static final int EXIT_FAILED_STDIN_READ = 5;
    public static final int EXIT_DEAD_CODE = 6;

    public static Scanner s;

    public static void main(String[] args) {
        if (args.length < 1) {
            fatalError("No input file provided.", EXIT_PARSING_ERROR);
        }

        String filename = args[0];
        Program astRoot = null;
        BufferedReader reader = null;

        s = new Scanner(System.in);

        try {
            reader = new BufferedReader(new FileReader(filename));

            astRoot = ParserWrapper.parse(reader, filename);
            
            reader.close();

            if (astRoot != null) {
                astRoot.execute();  
            }

        } catch (IOException ex) {
            fatalError("Failed to read the input file: " + ex.getMessage(), EXIT_FAILED_STDIN_READ);
        } catch (Exception ex) {
            fatalError("Uncaught parsing error: " + ex.getMessage(), EXIT_PARSING_ERROR);
        } finally {
            try {
                if (reader != null) {
                    reader.close();  
                }
            } catch (IOException ex) {
                fatalError("Failed to close input file: " + ex.getMessage(), EXIT_FAILED_STDIN_READ);
            }
        }

        System.exit(EXIT_SUCCESS);
    }

    public static void fatalError(String message, int processReturnCode) {
        System.err.println(message);
        System.exit(processReturnCode);
    }
}
