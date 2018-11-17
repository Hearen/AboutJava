package site.lhearen.ajava.base.string;

public class RotationCount {
    public static int arrayLeftRotation(StringBuilder str1, StringBuilder str2) {
        int i;
        int count = 0;
        for (i = 0; i < str1.length(); i++) {
            if (!str1.toString().equals(str2.toString())) {
                count++;
                str1 = leftRotatebyOne(str1);
            } else return count;
        }
        return count;
    }

    static StringBuilder leftRotatebyOne(StringBuilder str) {
        int i;
        char temp = str.charAt(0);
        for (i = 0; i < str.length() - 1; i++) {
            str.setCharAt(i, str.charAt(i + 1));
        }
        str.setCharAt(i, temp);
        return str;
    }

    public static void main(String[] args) {
        StringBuilder str1 = new StringBuilder("david");
        StringBuilder str2 = new StringBuilder("vidda");
        System.out.print(arrayLeftRotation(str1, str2));
    }
}
