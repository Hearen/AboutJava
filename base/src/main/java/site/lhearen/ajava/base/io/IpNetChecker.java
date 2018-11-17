package site.lhearen.ajava.base.io;

public class IpNetChecker {
    public static void main(String...args) {

    }

    private static String convertIpToBinary(String ip) {
        Long ret = 0L;
        String[] tokens = ip.split("\\.");
        for (int i = 0; i < 4; ++i) {
            ret |= Long.parseLong(tokens[i]);
            ret <<= 8;
        }
        return Long.toBinaryString(ret);
    }

    private static boolean isIncluded(String smallerNetOrIp, String biggerNet) {
        String[] sNetTokens = smallerNetOrIp.split("/");
        String[] bNetTokens = biggerNet.split("/");
        Integer sPrefixWidth = 32;
        if (smallerNetOrIp.contains("/")) {
            sPrefixWidth = Integer.parseInt(sNetTokens[1]);
        }
        Integer bPrefixWidth = Integer.parseInt(bNetTokens[1]);
        if (sPrefixWidth < bPrefixWidth) {
            return false;
        }
        String sBinaryStr = convertIpToBinary(sNetTokens[0]);
        String bBinaryStr = convertIpToBinary(bNetTokens[0]);
        for (int i = 0; i < bPrefixWidth; ++i) {
            if (bBinaryStr.charAt(i) != sBinaryStr.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
