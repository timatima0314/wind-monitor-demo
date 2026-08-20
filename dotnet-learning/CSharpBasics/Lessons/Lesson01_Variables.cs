namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン1: 変数・型
/// Javaとの対応: int/double/boolはほぼ同じ。String -> string(小文字)。
/// </summary>
public static class Lesson01_Variables
{
    public static void Run()
    {
        Console.WriteLine("=== レッスン1: 変数・型 ===");

        int count = 10;
        double temperature = 25.5;
        string name = "Takagi";
        bool isOnline = true;

        Console.WriteLine($"count = {count}");
        Console.WriteLine($"temperature = {temperature}");
        Console.WriteLine($"name = {name}");
        Console.WriteLine($"isOnline = {isOnline}");

        // var は型推論（Javaのvarと同じ考え方）
        var windSpeed = 12.3;
        Console.WriteLine($"windSpeed (var) = {windSpeed}");
    }
}
