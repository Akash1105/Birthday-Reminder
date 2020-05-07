package com.rishi.family.sqlitebackup;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.rishi.family.sqlite.DatabaseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class SqliteBackupManager {
    public static boolean importDB(Context context) {
        boolean acknowledgement = false;
        try {
//            File sd = Environment.getExternalStorageDirectory();
            File sd = new File(Environment.getExternalStorageDirectory(), "Birthday Reminder");
            if (sd.canWrite()) {
                File backupDB = context.getDatabasePath(DatabaseHandler.sDatabaseName);
                String backupDBPath = String.format("%s.bak", DatabaseHandler.sDatabaseName);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(context, "Restore successful", Toast.LENGTH_SHORT).show();
                acknowledgement = true;
            } else {
                Toast.makeText(context, "Restore failed", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Restore failed", Toast.LENGTH_SHORT).show();


        }

        return acknowledgement;
    }

    public static void exportDB(Context context) {
        try {
//            File sd = Environment.getExternalStorageDirectory();

            File sd = new File(Environment.getExternalStorageDirectory(), "Birthday Reminder");
            sd.mkdir();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String backupDBPath = String.format("%s.bak", DatabaseHandler.sDatabaseName);
                File currentDB = context.getDatabasePath(DatabaseHandler.sDatabaseName);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(context, "Data successfully backed up to local storage. You can restore it even after you reinstall the app", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Backup failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Backup failed", Toast.LENGTH_SHORT).show();


        }
    }
}
