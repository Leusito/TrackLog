package com.example.tracklog.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TrackLogDao_Impl implements TrackLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Training> __insertionAdapterOfTraining;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<Competition> __insertionAdapterOfCompetition;

  private final EntityDeletionOrUpdateAdapter<Training> __deletionAdapterOfTraining;

  private final EntityDeletionOrUpdateAdapter<Competition> __deletionAdapterOfCompetition;

  private final EntityDeletionOrUpdateAdapter<Training> __updateAdapterOfTraining;

  private final EntityDeletionOrUpdateAdapter<Competition> __updateAdapterOfCompetition;

  public TrackLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTraining = new EntityInsertionAdapter<Training>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `training_table` (`id`,`date`,`description`,`distanceMeters`,`times`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Training entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getDistanceMeters());
        final String _tmp = __converters.fromList(entity.getTimes());
        statement.bindString(5, _tmp);
        statement.bindString(6, entity.getNotes());
      }
    };
    this.__insertionAdapterOfCompetition = new EntityInsertionAdapter<Competition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `competition_table` (`id`,`date`,`name`,`event`,`result`,`position`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Competition entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getEvent());
        statement.bindString(5, entity.getResult());
        statement.bindLong(6, entity.getPosition());
      }
    };
    this.__deletionAdapterOfTraining = new EntityDeletionOrUpdateAdapter<Training>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `training_table` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Training entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfCompetition = new EntityDeletionOrUpdateAdapter<Competition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `competition_table` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Competition entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTraining = new EntityDeletionOrUpdateAdapter<Training>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `training_table` SET `id` = ?,`date` = ?,`description` = ?,`distanceMeters` = ?,`times` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Training entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getDistanceMeters());
        final String _tmp = __converters.fromList(entity.getTimes());
        statement.bindString(5, _tmp);
        statement.bindString(6, entity.getNotes());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__updateAdapterOfCompetition = new EntityDeletionOrUpdateAdapter<Competition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `competition_table` SET `id` = ?,`date` = ?,`name` = ?,`event` = ?,`result` = ?,`position` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Competition entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getEvent());
        statement.bindString(5, entity.getResult());
        statement.bindLong(6, entity.getPosition());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertTraining(final Training training,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTraining.insert(training);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCompetition(final Competition competition,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCompetition.insert(competition);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTraining(final Training training,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTraining.handle(training);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCompetition(final Competition competition,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCompetition.handle(competition);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTraining(final Training training,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTraining.handle(training);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCompetition(final Competition competition,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCompetition.handle(competition);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Training>> getAllTrainings() {
    final String _sql = "SELECT * FROM training_table ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"training_table"}, new Callable<List<Training>>() {
      @Override
      @NonNull
      public List<Training> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "times");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Training> _result = new ArrayList<Training>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Training _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getInt(_cursorIndexOfDistanceMeters);
            final List<String> _tmpTimes;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTimes);
            _tmpTimes = __converters.fromString(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Training(_tmpId,_tmpDate,_tmpDescription,_tmpDistanceMeters,_tmpTimes,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Training>> getTrainingsForDate(final long date) {
    final String _sql = "SELECT * FROM training_table WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"training_table"}, new Callable<List<Training>>() {
      @Override
      @NonNull
      public List<Training> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "times");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Training> _result = new ArrayList<Training>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Training _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getInt(_cursorIndexOfDistanceMeters);
            final List<String> _tmpTimes;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTimes);
            _tmpTimes = __converters.fromString(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Training(_tmpId,_tmpDate,_tmpDescription,_tmpDistanceMeters,_tmpTimes,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Competition>> getAllCompetitions() {
    final String _sql = "SELECT * FROM competition_table ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"competition_table"}, new Callable<List<Competition>>() {
      @Override
      @NonNull
      public List<Competition> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "event");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final List<Competition> _result = new ArrayList<Competition>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Competition _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpEvent;
            _tmpEvent = _cursor.getString(_cursorIndexOfEvent);
            final String _tmpResult;
            _tmpResult = _cursor.getString(_cursorIndexOfResult);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item = new Competition(_tmpId,_tmpDate,_tmpName,_tmpEvent,_tmpResult,_tmpPosition);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Competition>> getCompetitionsForDate(final long date) {
    final String _sql = "SELECT * FROM competition_table WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"competition_table"}, new Callable<List<Competition>>() {
      @Override
      @NonNull
      public List<Competition> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "event");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final List<Competition> _result = new ArrayList<Competition>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Competition _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpEvent;
            _tmpEvent = _cursor.getString(_cursorIndexOfEvent);
            final String _tmpResult;
            _tmpResult = _cursor.getString(_cursorIndexOfResult);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item = new Competition(_tmpId,_tmpDate,_tmpName,_tmpEvent,_tmpResult,_tmpPosition);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
