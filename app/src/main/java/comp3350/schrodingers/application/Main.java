package comp3350.schrodingers.application;

import comp3350.schrodingers.presentation.CLI;

public class Main {

    // Default name of DB.
    private static String dbName = "SC";

    // Simple command-line interface for testing purposes.
    public static void main(String[] args) {
        CLI.run();
        System.out.println("All done");
    }

    // Set/Establish HSQLDB path name (performed in HomeActivity).
    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }

    // Return a reference to the DB.
    public static String getDBPathName() {
        return dbName;
    }
}
