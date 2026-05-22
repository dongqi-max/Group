package edu.cs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class EmbeddedTomcatServer {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = DEFAULT_PORT;

        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                port = Integer.parseInt(arg.substring("--port=".length()));
            }
        }

        File webAppDir = resolveWebAppDirectory();
        System.out.println("Starting embedded Tomcat using webapp directory: " + webAppDir.getAbsolutePath());

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        Context context = tomcat.addWebapp("", webAppDir.getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(context);

        File classesDir = resolveClassesDirectory();
        if (classesDir != null && classesDir.exists()) {
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", classesDir.getAbsolutePath(), "/"));
        } else {
            File jarFile = locateApplicationJar();
            resources.addJarResources(new JarResourceSet(resources, "/WEB-INF/classes", jarFile.getAbsolutePath(), "/"));
        }

        context.setResources(resources);

        tomcat.start();
        System.out.println("Application started at http://localhost:" + port + "/");
        tomcat.getServer().await();
    }

    private static File resolveWebAppDirectory() throws Exception {
        File sourceWebApp = new File("src/main/webapp");
        if (sourceWebApp.exists()) {
            return sourceWebApp;
        }

        URL webappResource = EmbeddedTomcatServer.class.getResource("/webapp");
        if (webappResource != null && "file".equals(webappResource.getProtocol())) {
            return new File(webappResource.toURI());
        }

        File jarFile = locateApplicationJar();
        return extractWebAppFromJar(jarFile);
    }

    private static File resolveClassesDirectory() {
        File targetClasses = new File("target/classes");
        if (targetClasses.exists()) {
            return targetClasses;
        }
        return null;
    }

    private static File locateApplicationJar() throws URISyntaxException {
        URI codeLocation = EmbeddedTomcatServer.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        File jarFile = new File(codeLocation);
        if (!jarFile.isFile()) {
            throw new IllegalStateException("Embedded server must be launched from a packaged JAR or a project with target/classes available.");
        }
        return jarFile;
    }

    private static File extractWebAppFromJar(File jarFile) throws IOException {
        File tempDir = Files.createTempDirectory("assignment-planner-webapp").toFile();
        tempDir.deleteOnExit();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!name.startsWith("webapp/")) {
                    continue;
                }

                File file = new File(tempDir, name.substring("webapp/".length()));
                if (entry.isDirectory()) {
                    if (!file.exists() && !file.mkdirs()) {
                        throw new IOException("Unable to create directory " + file.getAbsolutePath());
                    }
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null && !parent.exists() && !parent.mkdirs()) {
                    throw new IOException("Unable to create directory " + parent.getAbsolutePath());
                }

                try (InputStream input = jar.getInputStream(entry);
                     FileOutputStream output = new FileOutputStream(file)) {
                    input.transferTo(output);
                }
            }
        }

        return tempDir;
    }
}
