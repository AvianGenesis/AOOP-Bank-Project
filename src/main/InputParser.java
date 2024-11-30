package main;

public class InputParser {
    
    /** 
     * @param str
     * @return int
     */
    public int tryParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Throwable e) {
            return Integer.MIN_VALUE;
        }
    }

    
    /** 
     * @param str
     * @return double
     */
    public double tryParseAmt(String str) {
        try {
            return (Math.round(Double.parseDouble(str) * 100.00) / 100.00);
        } catch (Throwable e) {
            return Double.MIN_VALUE;
        }
    }
}
