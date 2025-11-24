package com.example.tracklog.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
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

  private final SharedSQLiteStatement __preparedStmtOfDeleteTrainingByDate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCompetitionByDate;

  public TrackLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTraining = new EntityInsertionAdapter<Training>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `training_table` (`id`,`date`,`description`,`distances`,`notes`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Training entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __converters.toTrainingDistanceList(entity.getDistances());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getNotes());
      }
    };
    this.__insertionAdapterOfCompetition = new EntityInsertionAdapter<Competition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `competition_table` (`id`,`date`,`name`,`location`,`events`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Competition entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getLocation());
        final String _tmp = __converters.toCompetitionEventList(entity.getEvents());
        statement.bindString(5, _tmp);
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
        return "UPDATE OR ABORT `training_table` SET `id` = ?,`date` = ?,`description` = ?,`distances` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Training entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __converters.toTrainingDistanceList(entity.getDistances());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getNotes());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__updateAdapterOfCompetition = new EntityDeletionOrUpdateAdapter<Competition>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `competition_table` SET `id` = ?,`date` = ?,`name` = ?,`location` = ?,`events` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Competition entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getLocation());
        final String _tmp = __converters.toCompetitionEventList(entity.getEvents());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteTrainingByDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM training_table WHERE date = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCompetitionByDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM competition_table WHERE date = ?";
        return _query;
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
  public Object deleteTrainingByDate(final long date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTrainingByDate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, date);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTrainingByDate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCompetitionByDate(final long date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCompetitionByDate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, date);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteCompetitionByDate.release(_stmt);
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
          final int _cursorIndexOfDistances = CursorUtil.getColumnIndexOrThrow(_cursor, "distances");
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
            final List<TrainingDistance> _tmpDistances;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDistances);
            _tmpDistances = __converters.fromTrainingDistanceList(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Training(_tmpId,_tmpDate,_tmpDescription,_tmpDistances,_tmpNotes);
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
          final int _cursorIndexOfDistances = CursorUtil.getColumnIndexOrThrow(_cursor, "distances");
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
            final List<TrainingDistance> _tmpDistances;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDistances);
            _tmpDistances = __converters.fromTrainingDistanceList(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Training(_tmpId,_tmpDate,_tmpDescription,_tmpDistances,_tmpNotes);
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
  public Object getTrainingByDate(final long date,
      final Continuation<? super Training> $completion) {
    final String _sql = "SELECT * FROM training_table WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Training>() {
      @Override
      @Nullable
      public Training call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDistances = CursorUtil.getColumnIndexOrThrow(_cursor, "distances");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final Training _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<TrainingDistance> _tmpDistances;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDistances);
            _tmpDistances = __converters.fromTrainingDistanceList(_tmp);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new Training(_tmpId,_tmpDate,_tmpDescription,_tmpDistances,_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
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
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfEvents = CursorUtil.getColumnIndexOrThrow(_cursor, "events");
          final List<Competition> _result = new ArrayList<Competition>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Competition _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final List<CompetitionEvent> _tmpEvents;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEvents);
            _tmpEvents = __converters.fromCompetitionEventList(_tmp);
            _item = new Competition(_tmpId,_tmpDate,_tmpName,_tmpLocation,_tmpEvents);
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
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfEvents = CursorUtil.getColumnIndexOrThrow(_cursor, "events");
          final List<Competition> _result = new ArrayList<Competition>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Competition _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final List<CompetitionEvent> _tmpEvents;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEvents);
            _tmpEvents = __converters.fromCompetitionEventList(_tmp);
            _item = new Competition(_tmpId,_tmpDate,_tmpName,_tmpLocation,_tmpEvents);
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
  public Object getCompetitionByDate(final long date,
      final Continuation<? super Competition> $completion) {
    final String _sql = "SELECT * FROM competition_table WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Competition>() {
      @Override
      @Nullable
      public Competition call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfEvents = CursorUtil.getColumnIndexOrThrow(_cursor, "events");
          final Competition _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final List<CompetitionEvent> _tmpEvents;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEvents);
            _tmpEvents = __converters.fromCompetitionEventList(_tmp);
            _result = new Competition(_tmpId,_tmpDate,_tmpName,_tmpLocation,_tmpEvents);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
