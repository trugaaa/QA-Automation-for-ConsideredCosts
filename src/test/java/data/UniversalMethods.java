package data;

public class UniversalMethods
{
    void sleepMain(int seconds)
    {
        try {
            Thread.sleep(seconds*1000);
        }
        catch (Exception ignored)
        {

        }
    }

    private String randomString(int length, int minCharLimit, int maxCharLimit)
    {
        String resultString = "";
        for(int i=0; i<length;i++)
        {
            resultString+=randomSymbol(minCharLimit,maxCharLimit);
        }
        return resultString;
    }

    private char randomSymbol(int minCharLimit, int maxCharLimit)
    {
        return (char)getRandomIntegerBetweenRange(minCharLimit,maxCharLimit);
    }

    public static int getRandomIntegerBetweenRange(int minLimit, int manLimit)
    {
        int x = (int)(Math.random()*((manLimit-minLimit)+1))+minLimit;
        return x;
    }
}
