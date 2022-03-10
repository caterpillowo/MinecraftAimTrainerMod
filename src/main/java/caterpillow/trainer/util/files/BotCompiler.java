package caterpillow.trainer.util.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class BotCompiler {

    public static Class<?> getBotFromSource(String sourceCode) throws IOException, ClassNotFoundException {


        // create an empty source file
        File sourceFile = File.createTempFile("Bot", ".java");
        sourceFile.deleteOnExit();

        // generate the source code, using the source filename as the class name
        String classname = sourceFile.getName().split("\\.")[0];

        // fixes the class name
        sourceCode = sourceCode.replace("DontChangeThisName", classname);

//        String sourceCode = "import testpackage.Test;" +
//                "public class " + classname + "{ " +
//                "public void hello() { " +
//                "Test test = new Test();" +
//                "System.out.println(\"Hello world\");" +
//                "System.out.println(test.integer);" +
//                "}" +
//                "}";

//        System.out.println(sourceCode);


        // write the source code into the source file
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.close();

        // compile the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File parentDirectory = sourceFile.getParentFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        // load the compiled class
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{parentDirectory.toURI().toURL()});
        Class<?> botClass = classLoader.loadClass(classname);

        return botClass;

        // call a method on the loaded class
//            Method helloMethod = helloClass.getDeclaredMethod("hello");
//            helloMethod.invoke(helloClass.newInstance());
    }

}
