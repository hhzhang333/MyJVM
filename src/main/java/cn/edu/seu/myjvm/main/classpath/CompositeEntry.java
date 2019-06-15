package cn.edu.seu.myjvm.main.classpath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by a on 2018/3/1.
 */
public class CompositeEntry  extends Entry{

    private ArrayList<Entry> compositeEntry;

    public static CompositeEntry newCompositeEntry(String pathList) {
        CompositeEntry compositeEntry = new CompositeEntry();
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        for (String path: pathList.split(File.pathSeparator)) {
            Entry entry = Entry.newEntry(path);
            arrayList.add(entry);
        }
        compositeEntry.setCompositeEntry(arrayList);
        return compositeEntry;
    }

    public BasicMatch readClass(String className) throws IOException {
        BasicMatch basicMatch = new BasicMatch();
        for (Entry entry: compositeEntry) {
            basicMatch = entry.readClass(className);
            if (basicMatch != null)
                break;
        }
        return basicMatch;
    }

    public ArrayList<Entry> getCompositeEntry() {
        return compositeEntry;
    }

    public void setCompositeEntry(ArrayList<Entry> compositeEntry) {
        this.compositeEntry = compositeEntry;
    }
}
