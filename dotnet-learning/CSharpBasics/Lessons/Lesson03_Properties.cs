namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン3: プロパティ
/// Javaとの対応: getWindSpeed()/setWindSpeed()相当を、{ get; set; }として自動生成できる。
/// </summary>
public class ReadingWithProperty
{
    public double WindSpeed { get; set; }
    public double Temperature { get; set; }
}

public static class Lesson03_Properties
{
    public static void Run()
    {
        Console.WriteLine("=== レッスン3: プロパティ ===");

        var reading = new ReadingWithProperty();
        reading.WindSpeed = 12.3; // setter相当
        Console.WriteLine($"WindSpeed = {reading.WindSpeed}"); // getter相当
    }
}
