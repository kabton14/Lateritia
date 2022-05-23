package com.example.lateritia.databinding;
import com.example.lateritia.R;
import com.example.lateritia.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentFuelLevelBindingLandImpl extends FragmentFuelLevelBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.enter_fuel_text, 1);
        sViewsWithIds.put(R.id.fuel_button_next, 2);
        sViewsWithIds.put(R.id.fuel_gauge_0, 3);
        sViewsWithIds.put(R.id.fuel_gauge_1, 4);
        sViewsWithIds.put(R.id.fuel_gauge_2, 5);
        sViewsWithIds.put(R.id.fuel_gauge_3, 6);
        sViewsWithIds.put(R.id.fuel_gauge_4, 7);
        sViewsWithIds.put(R.id.fuel_gauge_5, 8);
        sViewsWithIds.put(R.id.fuel_gauge_6, 9);
        sViewsWithIds.put(R.id.fuel_gauge_7, 10);
        sViewsWithIds.put(R.id.fuel_gauge_8, 11);
        sViewsWithIds.put(R.id.fuel_level_text_view, 12);
        sViewsWithIds.put(R.id.decrease_fuel_level, 13);
        sViewsWithIds.put(R.id.increase_fuel_level, 14);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentFuelLevelBindingLandImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private FragmentFuelLevelBindingLandImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[13]
            , (android.widget.TextView) bindings[1]
            , (android.widget.Button) bindings[2]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.ImageButton) bindings[14]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.operationsViewModel == variableId) {
            setOperationsViewModel((com.example.lateritia.fuelops.FuelOperationsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setOperationsViewModel(@Nullable com.example.lateritia.fuelops.FuelOperationsViewModel OperationsViewModel) {
        this.mOperationsViewModel = OperationsViewModel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): operationsViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}