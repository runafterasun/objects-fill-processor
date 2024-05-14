package ru.objectsfill.utils.reflection.vfs;


import java.io.IOException;
import java.util.jar.JarFile;

/** an implementation of {@link Vfs.Dir} for {@link java.util.zip.ZipFile} */
public class ZipDir implements Vfs.Dir {
    final java.util.zip.ZipFile jarFile;

    public ZipDir(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    public String getPath() {
        return jarFile != null ? jarFile.getName().replace("\\", "/") : "/NO-SUCH-DIRECTORY/";
    }

    public Iterable<Vfs.File> getFiles() {
        return () -> jarFile.stream()
                .filter(entry -> !entry.isDirectory())
                .map(entry -> (Vfs.File) new ZipFile(ZipDir.this, entry))
                .iterator();
    }

    public void close() {
        try { jarFile.close(); } catch (IOException ignored) {
        }
    }

    @Override
    public String toString() {
        return jarFile.getName();
    }
}
