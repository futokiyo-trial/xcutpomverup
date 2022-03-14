# xcutpomverup
横断的にpom.xmlを更新するCLI

## ビルド方法

```
git clone https://github.com/futokiyo-trial/xcutpomverup.git

cd xcutpomverup

mvn clean package
```

## 使い方
xcutpomverup-config.jsonを用意し、次のようなコマンドに指定し、実行する。
```
java -jar target/xcutpomverup-cli.jar ＜xcutpomverup-config.json＞
```

