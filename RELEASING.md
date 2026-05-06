# Publishing JUtils

JUtils is published to GitHub Packages as:

```xml
<dependency>
    <groupId>rroyo.jutils</groupId>
    <artifactId>jutils</artifactId>
    <version>VERSION</version>
</dependency>
```

## Option 1: Publish from GitHub

1. Open the repository on GitHub.
2. Go to `Actions`.
3. Open `Publish Maven Package`.
4. Click `Run workflow`.
5. Enter the version to publish, for example `1.0.0` or `1.1.0-SNAPSHOT`.

The workflow will build, test and deploy the package to GitHub Packages.

## Option 2: Publish with a tag

Use a release tag when you want a stable version:

```bash
git tag v1.0.0
git push origin v1.0.0
```

The workflow removes the leading `v` and publishes version `1.0.0`.

## How consumers use it

Consumers need the GitHub Packages repository in their `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/rroyo6500/JUtils</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

Then they can add the dependency:

```xml
<dependency>
    <groupId>rroyo.jutils</groupId>
    <artifactId>jutils</artifactId>
    <version>1.0.0</version>
</dependency>
```

If GitHub asks for authentication, the consumer needs a GitHub personal access token with `read:packages` in `~/.m2/settings.xml`.
