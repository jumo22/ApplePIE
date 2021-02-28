import java.util.*;

import static java.lang.Math.min;


public class OccurrenceFinder {

    public static boolean findIngredients(String[] myIngredients, String[] myBasket) {
        HashMap<Character, Integer> myIngredientMap = new HashMap<>();
        HashMap<Character, Integer> myBasketMap = new HashMap<>();
        int[] ingredientsNum = new int[]{0, 0, 0, 0, 0};

        for (String b : myBasket) {
            makeHashMap(b, myBasketMap);
            for (int i = 0; i < myIngredients.length; i++) {
                makeHashMap(myIngredients[i], myIngredientMap);

                if (myIngredientMap.size() > myBasketMap.size()) {
                    System.out.println("Ingredient bigger than basket element");
                    myIngredientMap.clear();
                    continue;
                } else {
                    int n = findMoves(myIngredientMap, myBasketMap);
                    if (n == 0) {
                        System.out.println("Basket element does not contain ingredient's letters");
                        myIngredientMap.clear();
                        continue;
                    } else {
                        ingredientsNum[i] += n;
                    }
                }
                myIngredientMap.clear();

            }
            myBasketMap.clear();
        }

        for (int i = 0; i < ingredientsNum.length; i++) {
            System.out.println(myIngredients[i] + " appears " + ingredientsNum[i] + " time(s).");
            if (ingredientsNum[i] < 1)
                return false;
        }
        return true;
    }


    public static int findMoves(HashMap<Character, Integer> myWordMap, HashMap<Character, Integer> myExampleMap) {
        int movesNum = -1;
        for (Map.Entry<Character, Integer> e : myWordMap.entrySet()) {
            int myWordValue = e.getValue();
            if (!myExampleMap.containsKey(e.getKey())) {
                return 0;
//                System.err.println("Not containing: " + e);
            } else {
                int myExampleValue = myExampleMap.get(e.getKey());

                if (myWordValue > myExampleValue) {
                    return 0;
                } else {
                    int currentNum = myExampleValue / myWordValue;
                    if (movesNum == -1) {
                        movesNum = currentNum;
                    } else {
                        movesNum = min(currentNum, movesNum);
                    }
                }
            }
        }
        return movesNum;
    }

    public static void makeHashMap(String s, HashMap<Character, Integer> myWordMap) {
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (myWordMap.containsKey(c)) {
                myWordMap.put(c, myWordMap.get(c) + 1);
            } else {
                myWordMap.put(c, 1);
            }
        }
    }

    public static void makeExampleHashMap(String s, HashMap<Character, Integer> myExampleMap,
                                          HashMap<Character, Integer> myWordMap) {
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (myWordMap.containsKey(c)) {
                if (myExampleMap.containsKey(c)) {
                    myExampleMap.put(c, myExampleMap.get(c) + 1);
                } else {
                    myExampleMap.put(c, 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please input your keyword: ");
        //String myWord = "APPLE";
        String myWord = s.nextLine();
        String[] myIngredients = new String[]{"APPLE", "SUGAR", "FLOUR", "EGG", "BUTTER"};
        String[] myBasket = new String[]{"BARPUTTXOLLE", "APLENOPLEGXAPPB", "APLENOPLEGXAPPB", "QWERAGG",
                "UPLENORLEGXAPL", "RPLSUORLEGXAPFGA"};

        //String myExampleString = "ABpPLNBAPLLEEPNAHDSNE";
        System.out.println("Please input your test String: ");
        String myExampleString = s.nextLine();

        myWord = myWord.toUpperCase();
        myExampleString = myExampleString.toUpperCase();

        HashMap<Character, Integer> myWordMap = new HashMap<>();
        HashMap<Character, Integer> myExampleMap = new HashMap<>();
        int maxMoves;

        if (myWord.length() > myExampleString.length()) {
            System.out.println("The Input Test String has fewer letter than the given keyword.");
            maxMoves = 0;
        } else {
            makeHashMap(myWord, myWordMap);
            makeExampleHashMap(myExampleString, myExampleMap, myWordMap);
            if (myWordMap.size() != myExampleMap.size()) {
                System.out.println("The Input Test String does not contain all the letters of the given keyword.");
                maxMoves = 0;
            } else {
                maxMoves = findMoves(myWordMap, myExampleMap);
            }
        }

        System.out.println("The maximum number of moves that can be applied is: " + maxMoves);

        if (findIngredients(myIngredients, myBasket)) {
            System.out.println("There is at least one basic ingredient of each type in our basket. Hooray!");
        } else {
            System.out.println("Sadly, we don't have all basic ingredients to bake our apple pie :(");
        }
    }

}
