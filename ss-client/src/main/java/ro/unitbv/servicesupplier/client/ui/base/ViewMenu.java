package ro.unitbv.servicesupplier.client.ui.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Petri on 15-May-16.
 */
public abstract class ViewMenu {

   static final BufferedReader sysScan =
         new BufferedReader(new InputStreamReader(System.in));

   protected abstract String getViewTitle();

   protected void printMenuHeader() {
      System.out.println();
      System.out.println();
      System.out.println(getViewTitle());
      System.out.println(underline());
      System.out.println();
   }

   private String underline() {
      int length = getViewTitle().length();

      String underline = "";
      for (int i = 0; i < length; i++) {
         underline += "_";
      }
      return underline;
   }

   public abstract void proceed();

   protected abstract void renderView();

   protected void printWorkingMessage() {
      System.out.println();
      printIndented("Working on it...");
   }

   protected void printIndented(String text) {
      System.out.print("\t" + text);
   }

   protected void printDoubleIndented(String text) {
      printIndented("\t" + text);
   }


   protected void printlnIndented(String text) {
      System.out.println("\t" + text);
   }

   protected void printlnDoubleIndented(String text) {
      printlnIndented("\t" + text);
   }

   protected String listenForString(String promptText) {
      printDoubleIndented(promptText);
      try {
         return sysScan.readLine();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected int listenForInt(String promptText) {
      String intStr = listenForString(promptText);
      try {
         return Integer.valueOf(intStr);
      } catch (NumberFormatException e) {
         printInvalidValue();
         return listenForInt(promptText);
      }
   }

   private void printInvalidValue() {
      printlnDoubleIndented("Invalid value! Please try again.");
   }

   protected double listenForDouble(String promptText) {
      String doubleStr = listenForString(promptText);
      try {
         return Double.valueOf(doubleStr);
      } catch (NumberFormatException e) {
         printInvalidValue();
         return listenForDouble(promptText);
      }
   }

}
