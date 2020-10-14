## ASM 简单案例实践

### 1. 创建 Plugin 插件
#### 1.1 创建项目工程
首先我们在 Android Studio 创建一个 Java 库工程，然后在对应的 build.gradle 文件中添加 gradle 和 maven 依赖。
```groovy
apply plugin: 'java-library'
apply plugin: 'kotlin'
apply plugin: 'maven'

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation gradleApi() // 添加 gradleApi 依赖
}

// 用于发布本地插件
uploadArchives {
    repositories {
        mavenDeployer {
            //设置插件的GAV参数
            pom.groupId = 'com.andoter.plugin' //自定义 Plugin 所在的包名
            pom.artifactId = 'ASMPlugin'   // 依赖的 artifactId
            pom.version = '1.0' //版本号
            repository(url: uri('../repo'))  //文件发布到下面目录  ../是父目录
        }
    }
}
```
#### 1.2 创建 properties 声明
在项目工程目录：src/main/resources/META-INF.gradle-plugins 文件夹，然后在该文件夹下创建 properties 文件。
- properties 文件名称即插件的名称，比如 com.andoter.asm_plugin.properties，则在引入插件时为 apply plugin:'com.andoter.asm_plugin'，名称可以随意定制。
- properties 内容格式为：implementation-class= 自定义 Plugin 类的全路径包路径，比如 com.andoter.asm_plugin.AndoterPlugin。