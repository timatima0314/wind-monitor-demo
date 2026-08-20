namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン5: 例外処理
/// Javaとの対応: try/catch/finallyの構文はほぼ同じ。
/// </summary>
public static class Lesson05_Exceptions
{
    public static void Run()
    {
        Console.WriteLine("=== レッスン5: 例外処理 ===");

        try
        {
            var value = 10 / int.Parse("0");
            Console.WriteLine($"value = {value}");
        }
        catch (Exception ex)
        {
            Console.WriteLine("エラー: " + ex.Message);
        }
        finally
        {
            Console.WriteLine("終了処理");
        }
    }
}
