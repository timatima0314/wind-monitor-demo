namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン4: インターフェース
/// Javaとの対応: implements が : になるだけで考え方は同じ。
/// </summary>
public interface ISensor
{
    double ReadValue();
}

public class WindSensor : ISensor
{
    public double ReadValue() => 12.3;
}

public class TemperatureSensor : ISensor
{
    public double ReadValue() => 25.5;
}

public static class Lesson04_Interfaces
{
    public static void Run()
    {
        Console.WriteLine("=== レッスン4: インターフェース ===");

        ISensor[] sensors = { new WindSensor(), new TemperatureSensor() };

        foreach (var sensor in sensors)
        {
            Console.WriteLine($"{sensor.GetType().Name}: {sensor.ReadValue()}");
        }
    }
}
