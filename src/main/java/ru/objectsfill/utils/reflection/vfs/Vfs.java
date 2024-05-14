package ru.objectsfill.utils.reflection.vfs;


import ru.objectsfill.utils.reflection.ReflectionsException;
import ru.objectsfill.utils.reflection.util.ClasspathHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * a simple virtual file system bridge
 * <p>use the {@link Vfs#fromURL(URL)} to get a {@link Dir},
 * then use {@link Dir#getFiles()} to iterate over the {@link File}
 * for example:
 * {@link Vfs#fromURL(URL)} uses static {@link DefaultUrlTypes} to resolve URLs.
 * It contains VfsTypes for handling for common resources such as local jar file, local directory, jar url, jar input stream and more.
 * <p>It can be plugged in with other {@link UrlType} using {@link Vfs#addDefaultURLTypes(UrlType)} or {@link Vfs#setDefaultURLTypes(List)}.
 * <p>for example:
 * <pre>
 *      Vfs.addDefaultURLTypes(new Vfs.UrlType() {
 *          public boolean matches(URL url)         {
 *              return url.getProtocol().equals("http");
 *          }
 *          public Vfs.Dir createDir(final URL url) {
 *              return new HttpDir(url); //implement this type... (check out a naive implementation on VfsTest)
 *          }
 *      });
 *
 *      Vfs.Dir dir = Vfs.fromURL(new URL("http://mirrors.ibiblio.org/pub/mirrors/maven2/org/slf4j/slf4j-api/1.5.6/slf4j-api-1.5.6.jar"));
 * </pre>
 * <p>use {@link Vfs#findFiles(Collection, Predicate)} to get an
 * iteration of files matching given name predicate over given list of urls
 */
public abstract class Vfs {
    private static List<UrlType> defaultUrlTypes = new ArrayList<>(Arrays.asList(DefaultUrlTypes.values()));

    /** an abstract vfs dir */
    public interface Dir extends AutoCloseable {
        String getPath();
        Iterable<File> getFiles();
        default void close() {}
    }

    /** an abstract vfs file */
    public interface File {
        String getName();
        String getRelativePath();
        InputStream openInputStream() throws IOException;
    }

    /** a matcher and factory for a url */
    public interface UrlType {
        boolean matches(URL url) throws Exception;
        Dir createDir(URL url) throws Exception;
    }

    /** the default url types that will be used when issuing {@link Vfs#fromURL(URL)} */
    public static List<UrlType> getDefaultUrlTypes() {
        return defaultUrlTypes;
    }

    /** sets the static default url types. can be used to statically plug in urlTypes */
    public static void setDefaultURLTypes(final List<UrlType> urlTypes) {
        defaultUrlTypes = urlTypes;
    }

    /** add a static default url types to the beginning of the default url types list. can be used to statically plug in urlTypes */
    public static void addDefaultURLTypes(UrlType urlType) {
        defaultUrlTypes.add(0, urlType);
    }

    /** tries to create a Dir from the given url, using the defaultUrlTypes */
    public static Dir fromURL(final URL url) {
        return fromURL(url, defaultUrlTypes);
    }

    /** tries to create a Dir from the given url, using the given urlTypes*/
    public static Dir fromURL(final URL url, final List<UrlType> urlTypes) {
        for (UrlType type : urlTypes) {
            try {
                if (type.matches(url)) {
                    Dir dir = type.createDir(url);
                    if (dir != null) return dir;
                }
            } catch (Throwable ignored) {
            }
        }

        throw new ReflectionsException("could not create Vfs.Dir from url, no matching UrlType was found [" + url.toExternalForm() + "]\n" +
                "either use fromURL(final URL url, final List<UrlType> urlTypes) or " +
                "use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) " +
                "with your specialized UrlType.");
    }

    /** tries to create a Dir from the given url, using the given urlTypes*/
    public static Dir fromURL(final URL url, final UrlType... urlTypes) {
        return fromURL(url, Arrays.asList(urlTypes));
    }

    /** return an iterable of all {@link File} in given urls, starting with given packagePrefix and matching nameFilter */
    public static Iterable<File> findFiles(final Collection<URL> inUrls, final String packagePrefix, final Predicate<String> nameFilter) {
        Predicate<File> fileNamePredicate = file -> {
            String path = file.toString().replace('\\','/');
            if (path.contains(packagePrefix)) {
                String filename = path.substring(path.indexOf(packagePrefix) + packagePrefix.length());
                return !filename.isEmpty() && nameFilter.test(filename.substring(1));
            } else {
                return false;
            }
        };
        return findFiles(inUrls, fileNamePredicate);
    }

    /** return an iterable of all {@link File} in given urls, matching filePredicate */
    public static Iterable<File> findFiles(final Collection<URL> urls, final Predicate<File> filePredicate) {
        return () -> urls.stream()
                .flatMap(url -> {
                    try {
                        return StreamSupport.stream(fromURL(url).getFiles().spliterator(), false);
                    } catch (Throwable e) {
                        return Stream.of();
                    }
                }).filter(filePredicate).iterator();
    }

    /**try to get {@link java.io.File} from url*/
    public static java.io.File getFile(URL url) {
        java.io.File file;
        String path;

        try {
            path = url.toURI().getSchemeSpecificPart();
            if ((file = new java.io.File(path)).exists()) return file;
        } catch (URISyntaxException ignored) {
        }

        try {
            path = URLDecoder.decode(url.getPath(), "UTF-8");
            if (path.contains(".jar!")) path = path.substring(0, path.lastIndexOf(".jar!") + ".jar".length());
            if ((file = new java.io.File(path)).exists()) return file;

        } catch (UnsupportedEncodingException ignored) {
        }

        try {
            path = url.toExternalForm();
            if (path.startsWith("jar:")) path = path.substring("jar:".length());
            if (path.startsWith("wsjar:")) path = path.substring("wsjar:".length());
            if (path.startsWith("file:")) path = path.substring("file:".length());
            if (path.contains(".jar!")) path = path.substring(0, path.indexOf(".jar!") + ".jar".length());
            if (path.contains(".war!")) path = path.substring(0, path.indexOf(".war!") + ".war".length());
            if ((file = new java.io.File(path)).exists()) return file;

            path = path.replace("%20", " ");
            if ((file = new java.io.File(path)).exists()) return file;

        } catch (Exception ignored) {
        }

        return null;
    }

    private static boolean hasJarFileInPath(URL url) {
        return url.toExternalForm().matches(".*\\.jar(!.*|$)");
    }

    private static boolean hasInnerJarFileInPath(URL url) {
        return url.toExternalForm().matches(".+\\.jar!/.+");
    }

    /** default url types used by {@link Vfs#fromURL(URL)}
     *
     * <p>jarFile - creates a {@link ZipDir} over jar file
     * <p>jarUrl - creates a {@link ZipDir} over a jar url, using Java's {@link JarURLConnection}
     * <p>directory - creates a {@link SystemDir} over a file system directory
     * <p>jboss vfs - for protocols vfs, using jboss vfs (should be provided in classpath)
     * <p>bundle - for bundle protocol, using eclipse FileLocator (should be provided in classpath)
     * <p>jarInputStream - creates a {@link JarInputDir} over jar files (contains ".jar!/" in it's name), using Java's JarInputStream
     * */
    public enum DefaultUrlTypes implements UrlType {
        jarFile {
            public boolean matches(URL url) {
                return url.getProtocol().equals("file") && hasJarFileInPath(url);
            }

            public Dir createDir(final URL url) throws Exception {
                return new ZipDir(new JarFile(getFile(url)));
            }
        },

        jarUrl {
            public boolean matches(URL url) {
                return ("jar".equals(url.getProtocol()) || "zip".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol())) && !hasInnerJarFileInPath(url);
            }

            public Dir createDir(URL url) throws Exception {
                try {
                    URLConnection urlConnection = url.openConnection();
                    if (urlConnection instanceof JarURLConnection) {
                        urlConnection.setUseCaches(false);
                        return new ZipDir(((JarURLConnection) urlConnection).getJarFile());
                    }
                } catch (Throwable e) { /*fallback*/ }
                java.io.File file = getFile(url);
                if (file != null) {
                    return new ZipDir(new JarFile(file));
                }
                return null;
            }
        },

        directory {
            public boolean matches(URL url) {
                if (url.getProtocol().equals("file") && !hasJarFileInPath(url)) {
                    java.io.File file = getFile(url);
                    return file != null && file.isDirectory();
                } else return false;
            }

            public Dir createDir(final URL url) {
                return new SystemDir(getFile(url));
            }
        },

        bundle {
            public boolean matches(URL url) {
                return url.getProtocol().startsWith("bundle");
            }

            public Dir createDir(URL url) throws Exception {
                return fromURL((URL) ClasspathHelper.contextClassLoader().
                        loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke(null, url));
            }
        },

        jarInputStream {
            public boolean matches(URL url) {
                return url.toExternalForm().contains(".jar");
            }

            public Dir createDir(final URL url) {
                return new JarInputDir(url);
            }
        }
    }
}
