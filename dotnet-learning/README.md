# dotnet-learning

C#基礎学習用プロジェクトです。将来的に `wind-monitor-demo`（Java/Spring Boot）を
.NET（ASP.NET Core）へクローン移植する前段の基礎固めとして用意しています。

## 構成

```
CSharpBasics/
  Program.cs              各レッスンを順番に実行するエントリーポイント
  Lessons/
    Lesson01_Variables.cs     変数・型
    Lesson02_Classes.cs       クラス
    Lesson03_Properties.cs    プロパティ（Javaのgetter/setter相当）
    Lesson04_Interfaces.cs    インターフェース
    Lesson05_Exceptions.cs    例外処理
    Lesson06_Linq.cs          LINQ（Javaのstream相当）
    Lesson07_AsyncAwait.cs    async/await
```

## 実行方法（PC環境で.NET SDKインストール後）

```bash
cd dotnet-learning/CSharpBasics
dotnet run
```

各レッスンのコメントに、対応するJavaの書き方を記載しています。

## 次のステップ

1. 各レッスンを読んで、Javaとの対応を理解する
2. 自分でコードを書き換えて動作を確認する
3. 基礎に慣れたら、最小のASP.NET Core Web API作成に進む
4. 最終的に `wind-monitor-demo` の構成（MQTT受信 → DB保存 → REST API → Web監視画面）を
   ASP.NET Core + EF Core + MQTTnet で再現する
