namespace CSharpBasics.Lessons;

/// <summary>
/// レッスン7: async/await
/// JSのasync/awaitとほぼ同じ書き方。裏側の動き方(スレッド)は異なる点に注意。
/// </summary>
public static class Lesson07_AsyncAwait
{
    public static async Task Run()
    {
        Console.WriteLine("=== レッスン7: async/await ===");

        string result = await GetDataAsync();
        Console.WriteLine(result);
    }

    private static async Task<string> GetDataAsync()
    {
        await Task.Delay(500); // 通信待ちを想定
        return "データ取得完了";
    }
}
