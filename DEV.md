
TODOs:
- support default values
- add more tests https://github.com/swagger-api/swagger-parser/blob/master/modules/swagger-parser-v3/src/test/resources


```sh

./mill clean

./mill -i mill.scalalib.scalafmt/

./mill __.test

./mill __.publishLocal
```

```sh

# RELEASE
VERSION="0.6.0"
git commit --allow-empty -am "Release $VERSION"
git tag -a $VERSION -m "Release $VERSION"
git push --atomic origin main $VERSION

```
