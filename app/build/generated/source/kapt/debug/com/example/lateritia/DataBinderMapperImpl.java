package com.example.lateritia;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.lateritia.databinding.FragmentFuelLevelBindingImpl;
import com.example.lateritia.databinding.FragmentFuelLevelBindingLandImpl;
import com.example.lateritia.databinding.FragmentHomeBindingImpl;
import com.example.lateritia.databinding.FragmentHomeBindingLandImpl;
import com.example.lateritia.databinding.FragmentOpBindingImpl;
import com.example.lateritia.databinding.FragmentOpBindingLandImpl;
import com.example.lateritia.databinding.FragmentPriceBindingImpl;
import com.example.lateritia.databinding.FragmentResultBindingImpl;
import com.example.lateritia.databinding.FragmentSettingsBindingImpl;
import com.example.lateritia.databinding.FragmentTopUpBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_FRAGMENTFUELLEVEL = 1;

  private static final int LAYOUT_FRAGMENTHOME = 2;

  private static final int LAYOUT_FRAGMENTOP = 3;

  private static final int LAYOUT_FRAGMENTPRICE = 4;

  private static final int LAYOUT_FRAGMENTRESULT = 5;

  private static final int LAYOUT_FRAGMENTSETTINGS = 6;

  private static final int LAYOUT_FRAGMENTTOPUP = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_fuel_level, LAYOUT_FRAGMENTFUELLEVEL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_op, LAYOUT_FRAGMENTOP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_price, LAYOUT_FRAGMENTPRICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_result, LAYOUT_FRAGMENTRESULT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_settings, LAYOUT_FRAGMENTSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.lateritia.R.layout.fragment_top_up, LAYOUT_FRAGMENTTOPUP);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_FRAGMENTFUELLEVEL: {
          if ("layout/fragment_fuel_level_0".equals(tag)) {
            return new FragmentFuelLevelBindingImpl(component, view);
          }
          if ("layout-land/fragment_fuel_level_0".equals(tag)) {
            return new FragmentFuelLevelBindingLandImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_fuel_level is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout-land/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingLandImpl(component, view);
          }
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTOP: {
          if ("layout/fragment_op_0".equals(tag)) {
            return new FragmentOpBindingImpl(component, view);
          }
          if ("layout-land/fragment_op_0".equals(tag)) {
            return new FragmentOpBindingLandImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_op is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPRICE: {
          if ("layout/fragment_price_0".equals(tag)) {
            return new FragmentPriceBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_price is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTRESULT: {
          if ("layout/fragment_result_0".equals(tag)) {
            return new FragmentResultBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_result is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSETTINGS: {
          if ("layout/fragment_settings_0".equals(tag)) {
            return new FragmentSettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTOPUP: {
          if ("layout/fragment_top_up_0".equals(tag)) {
            return new FragmentTopUpBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_top_up is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(4);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "homeViewModel");
      sKeys.put(2, "operationsViewModel");
      sKeys.put(3, "settingsViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(10);

    static {
      sKeys.put("layout/fragment_fuel_level_0", com.example.lateritia.R.layout.fragment_fuel_level);
      sKeys.put("layout-land/fragment_fuel_level_0", com.example.lateritia.R.layout.fragment_fuel_level);
      sKeys.put("layout-land/fragment_home_0", com.example.lateritia.R.layout.fragment_home);
      sKeys.put("layout/fragment_home_0", com.example.lateritia.R.layout.fragment_home);
      sKeys.put("layout/fragment_op_0", com.example.lateritia.R.layout.fragment_op);
      sKeys.put("layout-land/fragment_op_0", com.example.lateritia.R.layout.fragment_op);
      sKeys.put("layout/fragment_price_0", com.example.lateritia.R.layout.fragment_price);
      sKeys.put("layout/fragment_result_0", com.example.lateritia.R.layout.fragment_result);
      sKeys.put("layout/fragment_settings_0", com.example.lateritia.R.layout.fragment_settings);
      sKeys.put("layout/fragment_top_up_0", com.example.lateritia.R.layout.fragment_top_up);
    }
  }
}
