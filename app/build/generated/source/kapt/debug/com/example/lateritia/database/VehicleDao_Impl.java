package com.example.lateritia.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.lifecycle.LiveData;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class VehicleDao_Impl implements VehicleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Vehicle> __insertionAdapterOfVehicle;

  private final EntityDeletionOrUpdateAdapter<Vehicle> __deletionAdapterOfVehicle;

  private final EntityDeletionOrUpdateAdapter<Vehicle> __updateAdapterOfVehicle;

  public VehicleDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVehicle = new EntityInsertionAdapter<Vehicle>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `vehicles` (`id`,`make`,`model`,`vin`,`licence`,`divisions`,`fuel_capacity`,`reserve_capacity`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vehicle value) {
        stmt.bindLong(1, value.getId());
        if (value.getMake() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMake());
        }
        if (value.getModel() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getModel());
        }
        if (value.getVin() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVin());
        }
        if (value.getLicence() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLicence());
        }
        stmt.bindLong(6, value.getDivisions());
        stmt.bindDouble(7, value.getFuelCapacity());
        stmt.bindDouble(8, value.getReserveCapacity());
      }
    };
    this.__deletionAdapterOfVehicle = new EntityDeletionOrUpdateAdapter<Vehicle>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `vehicles` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vehicle value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfVehicle = new EntityDeletionOrUpdateAdapter<Vehicle>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `vehicles` SET `id` = ?,`make` = ?,`model` = ?,`vin` = ?,`licence` = ?,`divisions` = ?,`fuel_capacity` = ?,`reserve_capacity` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vehicle value) {
        stmt.bindLong(1, value.getId());
        if (value.getMake() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMake());
        }
        if (value.getModel() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getModel());
        }
        if (value.getVin() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVin());
        }
        if (value.getLicence() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLicence());
        }
        stmt.bindLong(6, value.getDivisions());
        stmt.bindDouble(7, value.getFuelCapacity());
        stmt.bindDouble(8, value.getReserveCapacity());
        stmt.bindLong(9, value.getId());
      }
    };
  }

  @Override
  public Object insert(final Vehicle vehicle, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVehicle.insert(vehicle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object delete(final Vehicle[] vehicles, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVehicle.handleMultiple(vehicles);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object update(final Vehicle vehicle, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVehicle.handle(vehicle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object loadVehicle(final long id, final Continuation<? super Vehicle> continuation) {
    final String _sql = "select * from vehicles where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Vehicle>() {
      @Override
      public Vehicle call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMake = CursorUtil.getColumnIndexOrThrow(_cursor, "make");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfVin = CursorUtil.getColumnIndexOrThrow(_cursor, "vin");
          final int _cursorIndexOfLicence = CursorUtil.getColumnIndexOrThrow(_cursor, "licence");
          final int _cursorIndexOfDivisions = CursorUtil.getColumnIndexOrThrow(_cursor, "divisions");
          final int _cursorIndexOfFuelCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "fuel_capacity");
          final int _cursorIndexOfReserveCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "reserve_capacity");
          final Vehicle _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMake;
            if (_cursor.isNull(_cursorIndexOfMake)) {
              _tmpMake = null;
            } else {
              _tmpMake = _cursor.getString(_cursorIndexOfMake);
            }
            final String _tmpModel;
            if (_cursor.isNull(_cursorIndexOfModel)) {
              _tmpModel = null;
            } else {
              _tmpModel = _cursor.getString(_cursorIndexOfModel);
            }
            final String _tmpVin;
            if (_cursor.isNull(_cursorIndexOfVin)) {
              _tmpVin = null;
            } else {
              _tmpVin = _cursor.getString(_cursorIndexOfVin);
            }
            final String _tmpLicence;
            if (_cursor.isNull(_cursorIndexOfLicence)) {
              _tmpLicence = null;
            } else {
              _tmpLicence = _cursor.getString(_cursorIndexOfLicence);
            }
            final int _tmpDivisions;
            _tmpDivisions = _cursor.getInt(_cursorIndexOfDivisions);
            final double _tmpFuelCapacity;
            _tmpFuelCapacity = _cursor.getDouble(_cursorIndexOfFuelCapacity);
            final double _tmpReserveCapacity;
            _tmpReserveCapacity = _cursor.getDouble(_cursorIndexOfReserveCapacity);
            _result = new Vehicle(_tmpId,_tmpMake,_tmpModel,_tmpVin,_tmpLicence,_tmpDivisions,_tmpFuelCapacity,_tmpReserveCapacity);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Vehicle>> loadAllVehicles() {
    final String _sql = "select * from vehicles ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"vehicles"}, false, new Callable<List<Vehicle>>() {
      @Override
      public List<Vehicle> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMake = CursorUtil.getColumnIndexOrThrow(_cursor, "make");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfVin = CursorUtil.getColumnIndexOrThrow(_cursor, "vin");
          final int _cursorIndexOfLicence = CursorUtil.getColumnIndexOrThrow(_cursor, "licence");
          final int _cursorIndexOfDivisions = CursorUtil.getColumnIndexOrThrow(_cursor, "divisions");
          final int _cursorIndexOfFuelCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "fuel_capacity");
          final int _cursorIndexOfReserveCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "reserve_capacity");
          final List<Vehicle> _result = new ArrayList<Vehicle>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Vehicle _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpMake;
            if (_cursor.isNull(_cursorIndexOfMake)) {
              _tmpMake = null;
            } else {
              _tmpMake = _cursor.getString(_cursorIndexOfMake);
            }
            final String _tmpModel;
            if (_cursor.isNull(_cursorIndexOfModel)) {
              _tmpModel = null;
            } else {
              _tmpModel = _cursor.getString(_cursorIndexOfModel);
            }
            final String _tmpVin;
            if (_cursor.isNull(_cursorIndexOfVin)) {
              _tmpVin = null;
            } else {
              _tmpVin = _cursor.getString(_cursorIndexOfVin);
            }
            final String _tmpLicence;
            if (_cursor.isNull(_cursorIndexOfLicence)) {
              _tmpLicence = null;
            } else {
              _tmpLicence = _cursor.getString(_cursorIndexOfLicence);
            }
            final int _tmpDivisions;
            _tmpDivisions = _cursor.getInt(_cursorIndexOfDivisions);
            final double _tmpFuelCapacity;
            _tmpFuelCapacity = _cursor.getDouble(_cursorIndexOfFuelCapacity);
            final double _tmpReserveCapacity;
            _tmpReserveCapacity = _cursor.getDouble(_cursorIndexOfReserveCapacity);
            _item = new Vehicle(_tmpId,_tmpMake,_tmpModel,_tmpVin,_tmpLicence,_tmpDivisions,_tmpFuelCapacity,_tmpReserveCapacity);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
