namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン2: クラス
/// Javaとの対応: クラス定義の考え方は同じ。命名規則がPascalCaseになる点に注意。
/// </summary>
public class Reading
{
    public double WindSpeed;
    public double Temperature;
}

public static class Lesson02_Classes
{
    public static void Run()
    {
        Console.WriteLine("=== レッスン2: クラス ===");

        var reading = new Reading
        {
            WindSpeed = 12.3,
            Temperature = 25.5
        };

        Console.WriteLine($"WindSpeed = {reading.WindSpeed}");
        Console.WriteLine($"Temperature = {reading.Temperature}");
    }
}
