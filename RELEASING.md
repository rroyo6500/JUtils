## How to use JUtils in your project

1. **Add the repository to your `pom.xml`**:
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

2. **Configure your credentials**:
   Create or edit your `~/.m2/settings.xml` file with a [GitHub Personal Access Token](https://github.com/settings/tokens) (classic) with `read:packages` scope:
   ```xml
   <settings>
     <servers>
       <server>
         <id>github</id>
         <username>YOUR_GITHUB_USERNAME</username>
         <password>YOUR_PERSONAL_ACCESS_TOKEN</password>
       </server>
     </servers>
   </settings>
   ```

## Troubleshooting
If Maven cannot find the artifact after a version update, try forcing a refresh of the dependencies:
```bash
mvn clean install -U
```

---

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
