namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン6: LINQ
/// Javaとの対応: stream().filter().map() に近い考え方。
/// </summary>
public static class Lesson06_Linq
{
    public static void Run()
    {
        Console.WriteLine("=== レッスン6: LINQ ===");

        var readings = new List<double> { 5, 25, 15, 30 };

        var warnings = readings
            .Where(v => v > 20)
            .ToList();

        Console.WriteLine("20を超える値: " + string.Join(", ", warnings));

        var average = readings.Average();
        Console.WriteLine($"平均値: {average}");
    }
}
