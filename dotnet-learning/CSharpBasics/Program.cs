// C#基礎学習用: 各レッスンを順番に実行します
using CSharpBasics.Lessons;

Console.WriteLine("C# 基礎学習 (wind-monitor-demo .NET移植 準備)");
Console.WriteLine();

Lesson01_Variables.Run();
Console.WriteLine();

Lesson02_Classes.Run();
Console.WriteLine();

Lesson03_Properties.Run();
Console.WriteLine();

Lesson04_Interfaces.Run();
Console.WriteLine();

Lesson05_Exceptions.Run();
Console.WriteLine();

Lesson06_Linq.Run();
Console.WriteLine();

await Lesson07_AsyncAwait.Run();
