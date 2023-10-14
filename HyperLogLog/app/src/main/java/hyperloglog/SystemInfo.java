package hyperloglog;
public class SystemInfo {
    /**
     * Prints system information including the operating system, JVM, CPU, and date.
     * For printing info about the system that the tests and program is run on.
     * This code is provided in the PCPP course, but provides useful information in testing in this course as well.
     */
    public static void systemInfo() {
        System.out.printf("# OS:   %s; %s; %s%n",
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"));
        System.out.printf("# JVM:  %s; %s%n",
                System.getProperty("java.vendor"),
                System.getProperty("java.version"));
        // The processor identifier works only on MS Windows:
        System.out.printf("# CPU:  %s; %d \"cores\"%n",
                System.getenv("PROCESSOR_IDENTIFIER"),
                Runtime.getRuntime().availableProcessors());
        java.util.Date now = new java.util.Date();
        System.out.printf("# Date: %s%n",
                new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(now));
    }

    public static void main(String[] args) {
        systemInfo();
    }
}
