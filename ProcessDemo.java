import java.nio.file.Files;
import java.io.*;
import java.util.Scanner;
import java.nio.file.Path;

public class ProcessDemo {

  public static void openWeb(String web) {
    String url = "https://www." + web + ".com";
  
    ProcessBuilder processBuilder = null;
    try {
      processBuilder = new ProcessBuilder("cmd", "/c", "start", url);
      System.out.println("Web opened successfully!");
      processBuilder.start();
    } catch (IOException f) {
      f.printStackTrace();
    }
  }

  public static void openApp() {
    try {
      ProcessBuilder processBuilder = new ProcessBuilder("notepad.exe", "inouts.txt");
      System.out.println("App opened successfully!");
      Process process = processBuilder.start();
      process.waitFor();

    } catch (Exception s) {
      System.out.println("error " + s.getMessage());
    }
  }

  public static void openCMD(String app) {
    String apps = app.toLowerCase();

    try {

      ProcessBuilder processBuilder = new ProcessBuilder(apps + ".exe");
      processBuilder.start();
    } catch (Exception s) {
      System.out.println("error: " + s.getMessage());
    }
  }

  public static void openUrl(String url) {
    String urls = url.toLowerCase();
    try {
      ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", urls);
      processBuilder.start();
    } catch (Exception s) {
      System.out.println("error: " + s.getMessage());
    }
  }

  public static String getAppPath(String appName) {
    try {
      ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "where " + appName);
      Process process = processBuilder.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      return reader.readLine(); // Read first line (app path)
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null; // Return null if not found
  }

  public static void openApp(String app) {
    String path = getAppPath(app);
    try {
      ProcessBuilder builder = new ProcessBuilder(path);
      builder.start();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);

    int choice;
    String content = "";

    do {

      System.out.println("\n1.OpenWeb.com\n2.OpenCMD\n3.OpenUrl\n4.OpenApp\n5.Exit");
      try {
        FileWriter writer = new FileWriter("inouts.txt");
        writer.close();
        openApp();
        content = Files.readString(Path.of("inouts.txt"));

      } catch (IOException s) {
        System.out.println("error " + s.getMessage());
      }
      if (content == "") {
        System.out.println("Data not found!");
      }

      System.out.println("Select mode");
      choice = in.nextInt();
      if (choice == 1 && content != "") {
        System.out.println("Enter web domain");
        System.out.println(content);
        openWeb(content);
      } else if (choice == 2) {
        openCMD(content);

      } else if (choice == 3) {
        openUrl(content);
      } else if (choice == 4) {
        openApp(content);
      }
      try {
        FileWriter writers = new FileWriter("inouts.txt", false);
        writers.write("");
      } catch (IOException e) {
        System.out.println("error: " + e.getMessage());
      }
    } while (choice < 5);
  }
}