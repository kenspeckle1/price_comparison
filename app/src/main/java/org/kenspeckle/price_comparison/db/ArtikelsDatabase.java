package org.kenspeckle.price_comparison.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import androidx.room.Room;

import org.kenspeckle.price_comparison.db.artikel.Artikel;
import org.kenspeckle.price_comparison.db.artikel.ArtikelDao;
import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTyp;
import org.kenspeckle.price_comparison.db.artikelTyp.ArtikelTypDao;
import org.kenspeckle.price_comparison.db.preis.Preis;
import org.kenspeckle.price_comparison.db.preis.PreisDao;

/**
 * Created by kenspeckle on 15/02/2018.
 */

@Database(entities = {Artikel.class, ArtikelTyp.class, Preis.class}, version = 2 ,exportSchema = false)
public abstract class ArtikelsDatabase extends RoomDatabase {

    private static ArtikelsDatabase INSTANCE;

    public abstract ArtikelTypDao artikelTypDao();

    public abstract ArtikelDao artikelDao();

    public abstract PreisDao preisDao();

    private static final Object sLock = new Object();

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE posts_db "
                    +"ADD COLUMN  TEXT");

        }
    };*/
    public static ArtikelsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ArtikelsDatabase.class, "artikels.db")
                        .allowMainThreadQueries()
//                        .addCallback(new Callback() {
//                            @Override
//                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                                super.onCreate(db);
//                                Executors.newSingleThreadExecutor().execute(
//                                        () -> getInstance(context).artikelDao().saveAll(POSTS));
//                            }
//                        })
                        .build();
            }
            return INSTANCE;
        }
    }
//
//    private final static List<Artikel> POSTS = Arrays.asList(
//            new Artikel("Knoppers", "Aldi", 1.39f, 0.0f),
//            new Artikel("Flammkuchen Wagner", "Aldi", 1.49f, 0.0f),
//            new Artikel("Listerine Doppelpack", "Aldi", 6.85f, 5.71f));

}
