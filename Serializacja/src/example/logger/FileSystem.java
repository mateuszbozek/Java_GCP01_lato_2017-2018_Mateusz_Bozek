package example.logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class FileSystem {


    public static int TYPE_FILE = 1;

    public static int TYPE_DIR  = 2;

    public static int TYPE_ALL  = (TYPE_FILE | TYPE_DIR);

    private File      root;

    public FileSystem(File root) {
        if (root == null) {
            this.root = new File(System.getProperty("user.dir"));
        }
        else {
            root = root.getAbsoluteFile();

            if (root.isDirectory() || !root.exists()) {
                this.root = root;
            }
            else {
                this.root = root.getParentFile();
            }
        }
    }


    public Collection getFiles(boolean isRecursive) {
        return getFiles(isRecursive, TYPE_FILE, null);
    }


    public Collection getFiles(boolean isRecursive, String pattern) {
        return getFiles(isRecursive, TYPE_FILE, pattern);
    }

    public Collection getDirectories(boolean isRecursive) {
        return getFiles(isRecursive, TYPE_DIR, null);
    }

    public Collection getDirectories(boolean isRecursive, String pattern) {
        return getFiles(isRecursive, TYPE_DIR, pattern);
    }

    public Collection getFiles(boolean isRecursive, int type, String pattern) {
        return this.getFiles(null, isRecursive, type, pattern);
    }

    public Collection getFiles(Collection c, boolean isRecursive, int type,
                               String pattern) {
        return this.getFiles(c, root, isRecursive, type, pattern);
    }


    private Collection getFiles(Collection c, File rootDir,
                                boolean isRecursive, int type, String pattern) {
        File[] subfiles = rootDir.listFiles();
        if (subfiles == null || subfiles.length == 0) {
            return Collections.EMPTY_LIST;
        }
        else {
            if (c == null) {
                c = new ArrayList();
            }
            for (int i = 0; i < subfiles.length; i++) {
                if (subfiles[i].isDirectory()) {
                    if ((TYPE_DIR & type) == TYPE_DIR) {
                        if (accept(subfiles[i], pattern)) {
                            c.add(subfiles[i]);
                        }
                    }
                    if (isRecursive) {
                        getFiles(c, subfiles[i], isRecursive, type, pattern);
                    }
                }
                else {
                    if ((TYPE_FILE & type) == TYPE_FILE) {
                        if (accept(subfiles[i], pattern)) {
                            c.add(subfiles[i]);
                        }
                    }
                }
            }
            return c;
        }
    }


    private boolean accept(File f, String pattern) {
        if (pattern == null || f.getName().matches(pattern)) {
            return true;
        }
        else {
            return false;
        }
    }


    public boolean remove() {
        return remove(true);
    }


    public boolean remove(boolean isSelfRemoved) {
        return remove(root, isSelfRemoved, false);
    }


    private boolean remove(File rootDir, boolean isSelfRemoved, boolean isDeferAllowed) {
        File[] subfiles = rootDir.listFiles();
        if (subfiles != null) {
            for (int i=0; i<subfiles.length; i++) {
                if (subfiles[i].isDirectory()) {
                    if (!remove(subfiles[i], true, isDeferAllowed)) {
                        if (!isDeferAllowed) {
                            return false;
                        }
                    }
                }
                else {
                    if (!subfiles[i].delete()) {
                        if (isDeferAllowed) {
                            subfiles[i].deleteOnExit();
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        if (isSelfRemoved) {
            if (!rootDir.delete()) {
                if (isDeferAllowed) {
                    rootDir.deleteOnExit() ;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }


    public void purge() {
        purge(true);
    }


    public void purge(boolean isSelfRemoved) {
        remove(root, isSelfRemoved, true);
    }

    public File getRoot() {
        return root;
    }


    public boolean exists() {
        return root.exists();
    }


    public String toString() {
        return root.getAbsolutePath();
    }
}

