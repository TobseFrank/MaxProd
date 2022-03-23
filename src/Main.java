import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class Main {
  static boolean debug = false;

  public static void main(String[] args) {
    if (args.length < 2){
      System.out.println("Bitte mindestens 2 Zahlen eingeben");
      return;
    }
    double[] maxValue;  //{MaxWert,Anfang,Ende}

    //Aufsplitten in 2 Arrays
    String[] leftSide = new String[args.length / 2];
    String[] rightSide = new String[args.length / 2 + args.length % 2];

    System.arraycopy(args, 0, leftSide, 0, args.length / 2);
    System.arraycopy(args, leftSide.length, rightSide, 0, args.length / 2 + args.length % 2);


    double[] maxLeft = maxInArray(leftSide);
    double[] leftEndBorder = maxOnEndOfArray(leftSide);
    double[] leftEndNegativeBorder = maxNegativeOnEndOfArray(leftSide);

    double[] maxRight = maxInArray(rightSide);
    double[] rightStartBorder = maxOnStartOfArray(rightSide);
    double[] rightStartNegativeBorder = maxNegativeOnStartOfArray(rightSide);

    if (debug) {
      System.out.println("---------");
      System.out.println(Arrays.toString(args));
      System.out.println("---------");
      System.out.println(Arrays.toString(leftSide));
      System.out.println("Max Links " + Arrays.toString(maxLeft));
      System.out.println("Links Rechte Grenze " + Arrays.toString(leftEndBorder));
      System.out.println("Links Rechte Negative Grenze " + Arrays.toString(leftEndNegativeBorder));
      System.out.println("---------");
      System.out.println(Arrays.toString(rightSide));
      System.out.println("Max Rechts " + Arrays.toString(maxRight));
      System.out.println("Rechts Linke Grenze " + Arrays.toString(rightStartBorder));
      System.out.println("Rechts Linke Negative Grenze " + Arrays.toString(rightStartNegativeBorder));
      System.out.println("---------");
    }

    maxValue = maxLeft;

    if (maxRight[0] > maxValue[0]) {
      maxValue = maxRight;
      maxValue[1] += leftSide.length;
      maxValue[2] += leftSide.length;
    }

    if (leftEndBorder[0] * rightStartBorder[0] > maxValue[0]) {
      maxValue[0] = leftEndBorder[0] * rightStartBorder[0];
      maxValue[1] = leftEndBorder[1];
      maxValue[2] = leftSide.length + rightStartBorder[2];
    }

    if (leftEndNegativeBorder[0] * rightStartNegativeBorder[0] > maxValue[0]) {
      maxValue[0] = leftEndNegativeBorder[0] * rightStartNegativeBorder[0];
      maxValue[1] = leftEndNegativeBorder[1];
      maxValue[2] = leftSide.length + rightStartNegativeBorder[2];
    }

    System.out.println(Arrays.toString(maxValue));
  }


  public static double[] maxInArray(String[] strings) { //Scan Line Verfahren
    Instant start = Instant.now();

    int von = 0;
    int bis = 0;

    double maxProd;
    double allTimeMaxProd = 0;

    int schritte = 0;

    for (int i = 0; i < strings.length; i++) {
      maxProd = Double.parseDouble(strings[i]); //nehmen der ersten Zahl zum Start der Multiplikation

      if (maxProd > allTimeMaxProd) {            //Erkennung des ersten, sowie von Alleinstehenden Highs
        allTimeMaxProd = maxProd;
        von = i;
        bis = i;
      }

      for (int j = i + 1; j < strings.length; j++) {
        maxProd *= Double.parseDouble(strings[j]);
        schritte++;

        if (maxProd > allTimeMaxProd) {          //Erkennung eines neuen all time max Produktes
          allTimeMaxProd = maxProd;
          von = i;
          bis = j;
        }

        if (maxProd < 1 && maxProd > -1) {       //Erkennung ob Max Produkt kleiner 1
          break;
        }

      }

    }
    if (debug) {
      System.out.println("Max Produkt: " + allTimeMaxProd + " ,von Stelle: " + von + " , bis Stelle: " + bis);
      System.out.println("Mit: " + schritte + " Schritten, bei einer Array-Groeße von: " + strings.length);
      System.out.println("In: " + Duration.between(start, Instant.now()).toMillis() + " Millisekunden");
    }
    return new double[]{allTimeMaxProd, von, bis};
  }

  public static double[] maxOnEndOfArray(String[] strings) { //Scan Line Verfahren
    Instant start = Instant.now();

    int von = 0;
    int bis = 0;

    double maxProd;
    double allTimeMaxProd = 0;

    int schritte = 0;

    maxProd = Double.parseDouble(strings[strings.length - 1]); //nehmen der ersten Zahl zum Start der Multiplikation

    if (maxProd > allTimeMaxProd) {            //Erkennung des ersten, sowie von Alleinstehenden Highs
      allTimeMaxProd = maxProd;
      von = strings.length - 1;
      bis = strings.length - 1;
    }

    for (int j = strings.length - 1 - 1; j >= 0; j--) {
      maxProd *= Double.parseDouble(strings[j]);
      schritte++;

      if (maxProd > allTimeMaxProd) {          //Erkennung eines neuen all time max Produktes
        allTimeMaxProd = maxProd;
        von = j;
        bis = strings.length - 1;
      }

      if (maxProd < 1 && maxProd > -1) {       //Erkennung ob Max Produkt kleiner 1
        break;
      }


    }

    if (debug) {
      System.out.println("Max Produkt: " + allTimeMaxProd + " ,von Stelle: " + von + " , bis Stelle: " + bis);
      System.out.println("Mit: " + schritte + " Schritten, bei einer Array-Groeße von: " + strings.length);
      System.out.println("In: " + Duration.between(start, Instant.now()).toMillis() + " Millisekunden");
    }
    return new double[]{allTimeMaxProd, von, bis};
  }

  public static double[] maxOnStartOfArray(String[] strings) { //Scan Line Verfahren
    Instant start = Instant.now();

    int von = 0;
    int bis = 0;

    double maxProd;
    double allTimeMaxProd = 0;

    int schritte = 0;

    maxProd = Double.parseDouble(strings[0]); //nehmen der ersten Zahl zum Start der Multiplikation

    if (maxProd > allTimeMaxProd) {            //Erkennung des ersten, sowie von Alleinstehenden Highs
      allTimeMaxProd = maxProd;
    }

    for (int j = 1; j < strings.length; j++) {
      maxProd *= Double.parseDouble(strings[j]);
      schritte++;

      if (maxProd > allTimeMaxProd) {          //Erkennung eines neuen all time max Produktes
        allTimeMaxProd = maxProd;
        bis = j;
      }

      if (maxProd < 1 && maxProd > -1) {       //Erkennung ob Max Produkt kleiner 1
        break;
      }


    }
    if (debug) {
      System.out.println("Max Produkt: " + allTimeMaxProd + " ,von Stelle: " + von + " , bis Stelle: " + bis);
      System.out.println("Mit: " + schritte + " Schritten, bei einer Array-Groeße von: " + strings.length);
      System.out.println("In: " + Duration.between(start, Instant.now()).toMillis() + " Millisekunden");
    }
    return new double[]{allTimeMaxProd, von, bis};
  }

  public static double[] maxNegativeOnEndOfArray(String[] strings) { //Scan Line Verfahren
    Instant start = Instant.now();

    int von = 0;
    int bis = 0;

    double maxProd;
    double allTimeMaxProd = 0;

    int schritte = 0;

    maxProd = Double.parseDouble(strings[strings.length - 1]); //nehmen der ersten Zahl zum Start der Multiplikation

    if (maxProd < allTimeMaxProd) {            //Erkennung des ersten, sowie von Alleinstehenden Highs
      allTimeMaxProd = maxProd;
      von = strings.length - 1;
      bis = strings.length - 1;
    }

    for (int j = strings.length - 1 - 1; j >= 0; j--) {
      maxProd *= Double.parseDouble(strings[j]);
      schritte++;

      if (maxProd < allTimeMaxProd) {          //Erkennung eines neuen all time max Produktes
        allTimeMaxProd = maxProd;
        von = j;
        bis = strings.length - 1;
      }

      if (maxProd == 0) {       //Erkennung ob Max Produkt kleiner 1
        break;
      }


    }
    if (debug) {
      System.out.println("Max Produkt: " + allTimeMaxProd + " ,von Stelle: " + von + " , bis Stelle: " + bis);
      System.out.println("Mit: " + schritte + " Schritten, bei einer Array-Groeße von: " + strings.length);
      System.out.println("In: " + Duration.between(start, Instant.now()).toMillis() + " Millisekunden");
    }
    return new double[]{allTimeMaxProd, von, bis};
  }

  public static double[] maxNegativeOnStartOfArray(String[] strings) { //Scan Line Verfahren
    Instant start = Instant.now();

    int von = 0;
    int bis = 0;

    double maxProd;
    double allTimeMaxProd = 0;

    int schritte = 0;

    maxProd = Double.parseDouble(strings[0]); //nehmen der ersten Zahl zum Start der Multiplikation

    if (maxProd < allTimeMaxProd) {            //Erkennung des ersten, sowie von Alleinstehenden Highs
      allTimeMaxProd = maxProd;
    }

    for (int j = 1; j < strings.length; j++) {
      maxProd *= Double.parseDouble(strings[j]);
      schritte++;

      if (maxProd < allTimeMaxProd) {          //Erkennung eines neuen all time max Produktes
        allTimeMaxProd = maxProd;
        bis = j;
      }

      if (maxProd == 0) {       //Erkennung ob Max Produkt kleiner 1
        break;
      }


    }
    if (debug) {
      System.out.println("Max Produkt: " + allTimeMaxProd + " ,von Stelle: " + von + " , bis Stelle: " + bis);
      System.out.println("Mit: " + schritte + " Schritten, bei einer Array-Groeße von: " + strings.length);
      System.out.println("In: " + Duration.between(start, Instant.now()).toMillis() + " Millisekunden");
    }
    return new double[]{allTimeMaxProd, von, bis};
  }


}
