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

### 2. 基本项目结构介绍
#### 2.1 Project 
Project 类代表一个项目，也是我们能够与 Gradle 交互的入口，通过 project 类几乎可以获得所有 Gradle 的属性。
Project 之间是相互连接的，通常在 build 构建初始化时，Gradle 会为每个项目工程创建一个 Project 对象。
通过 Project 对象可以获取当前的项目路径、task 任务管理、dependency 依赖管理和 extend 管理。
- File getRootDir()：获取根路径
- File getBuildDir()：获取 build 路径
- Project getParent()：获取父 project
- String getName()：获取项目的名称
- String getDescription()：获取项目描述
- Set<Project> getAllprojects()：获取包含此项目的项目集合
- Task task(String name)：创建一个 task 任务
- Project findProject(String path)：根据路径查找一个 Project
- Map<Project, Set<Task>> getAllTasks(boolean recursive)：获取所有任务
- ConfigurationContainer getConfigurations()：配置容器
- TaskContainer getTasks()：任务容器
- void beforeEvaluate(Action<? super Project> action)：在 project Evaluate 之前执行
- void afterEvaluate(Action<? super Project> action)：在 project Evaluate 之后执行
- Gradle getGradle()：获取 Gradle 对象
- DependencyHandler getDependencies()：dependency 容器管理，可以添加新的依赖
- ExtensionContainer getExtensions()：额外配置管理器

#### 2.2 Extension
通过 Project 类的 getExtensions 方法，我们可以获得一个 ExtensionContainer 对象，使用该对象可以完成：
- 通过 add 或 create 方法创建额外的配置项；
- 通过 findByType 可以查找对应的配置
比如在 Android Studio 项目中，我们为一个插件创建额外的配置项：
```kotlin
project.extensions.create("andoter", AndoterExtension, args)
```
这样我们在 build.gradle 文件中可以添加 andoter {} 类的配置。
对于 Android 项目，Android Studio 提供了三种扩展类型：
- AppExtension：Android Application 应用扩展插件
- LibraryExtension：Android 类库扩展，为 com.android.library 类库项目
- TestExtension：为 com.android.test 项目的扩展配置
- FeatureExtension：为 com.android.feature 项目的扩展配置
示例：
```groovy
AppExtension appExtension = project.extensions.findByType(AppExtension.class)
```

对于一个 Android 项目，如果任务在 Project 级别，获取的 Project 是整个项目的，如果在下面的 module 级别，则 project 对应的 module 级别，
通过 Project.getParent 可以获得父类级别。

**获取项目的依赖**
```groovy

```

#### 2.3 Gradle

主要是获取 Gradle 相关的信息，比如 Gradle 的路径、版本号。同时也可以添加编译监听 addBuildListener。

- PluginManager getPluginManager()：插件管理器，可用于添加、判断、查找插件
- 