package com.hucet.tyler.memo.sql;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.Context;
import android.widget.Toast;

import com.hucet.tyler.memo.BuildConfig;

import java.lang.reflect.Method;
import java.util.HashMap;

public class DebugDatabaseUtils {

    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
            } catch (Exception ignore) {

            }
        }
    }

//    public static void setCustomDatabaseFiles(Context context) {
//        if (BuildConfig.DEBUG) {
//            try {
//                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
//                Class[] argTypes = new Class[]{HashMap.class};
//                Method setCustomDatabaseFiles = debugDB.getMethod("setCustomDatabaseFiles", argTypes);
//                HashMap<String, Pair<File, String>> customDatabaseFiles = new HashMap<>();
//                // set your custom database files
//                customDatabaseFiles.put(ExtTestDBHelper.DATABASE_NAME,
//                        new Pair<>(new File(context.getFilesDir() + "/" + ExtTestDBHelper.DIR_NAME +
//                                "/" + ExtTestDBHelper.DATABASE_NAME), ""));
//                setCustomDatabaseFiles.invoke(null, customDatabaseFiles);
//            } catch (Exception ignore) {
//
//            }
//        }
//    }

    public static void setInMemoryRoomDatabases(SupportSQLiteDatabase... database) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Class[] argTypes = new Class[]{HashMap.class};
                HashMap<String, SupportSQLiteDatabase> inMemoryDatabases = new HashMap<>();
                // set your inMemory databases
                inMemoryDatabases.put("InMemoryOne.db", database[0]);
                Method setRoomInMemoryDatabase = debugDB.getMethod("setInMemoryRoomDatabases", argTypes);
                setRoomInMemoryDatabase.invoke(null, inMemoryDatabases);
            } catch (Exception ignore) {

            }
        }
    }


}
