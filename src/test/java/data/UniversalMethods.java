package data;

public class UniversalMethods
{
    private String randomString(int length, int minCharLimit, int manCharLimit)
    {
        String resultString = "";
        for(int i=0; i<length;i++)
        {
            resultString+=randomSymbol(97,122);
        }
        return resultString;
    }

    private char randomSymbol(int minCharLimit, int manCharLimit)
    {
        return (char)getRandomIntegerBetweenRange(minCharLimit,manCharLimit);
    }

    public static int getRandomIntegerBetweenRange(int minLimit, int manLimit)
    {
        int x = (int)(Math.random()*((manLimit-minLimit)+1))+minLimit;
        return x;
    }
}
