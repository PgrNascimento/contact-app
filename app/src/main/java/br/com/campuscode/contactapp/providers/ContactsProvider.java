package br.com.campuscode.contactapp.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class ContactsProvider extends ContentProvider {

    private ContactsDatabaseHelper mDBHelper;
    private static UriMatcher sUriMather;
    private static final int URI_CONTACT = 1;
    private static final int URI_CONTACT_ID = 2;

    static {
        sUriMather = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMather.addURI(Contact.AUTHORITY, "contact", URI_CONTACT);
        sUriMather.addURI(Contact.AUTHORITY, "contact/#", URI_CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new ContactsDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch(sUriMather.match(uri)) {
            case URI_CONTACT:
                qb.setTables(ContactModel.TABLE_NAME);
                break;
            case URI_CONTACT_ID:
                qb.setTables(ContactModel.TABLE_NAME);
                qb.appendWhere(ContactModel._ID + '+' + uri.getLastPathSegment());
                break;
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMather.match(uri)) {
            case URI_CONTACT:
                return ContactModel.CONTENT_TYPE;
            case URI_CONTACT_ID:
                return ContactModel.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch(sUriMather.match(uri)) {
            case URI_CONTACT:
                return insertContact(contentValues);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        switch(sUriMather.match(uri)) {
            case URI_CONTACT_ID:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                int count = db.delete(ContactModel.TABLE_NAME, ContactModel._ID + "=" + id + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""), whereArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private Uri insertContact(ContentValues values) {
        Uri resultiUri = null;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = db.insert(ContactModel.TABLE_NAME, null, values);

        if(id > 0) {
            resultiUri = ContentUris.withAppendedId(ContactModel.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(resultiUri, null);
        }

        return resultiUri;
    }

}
