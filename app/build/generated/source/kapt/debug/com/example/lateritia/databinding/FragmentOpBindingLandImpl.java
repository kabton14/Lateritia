package com.example.lateritia.databinding;
import com.example.lateritia.R;
import com.example.lateritia.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentOpBindingLandImpl extends FragmentOpBinding implements com.example.lateritia.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.enter_operation_text, 3);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentOpBindingLandImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private FragmentOpBindingLandImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.ImageButton) bindings[1]
            , (android.widget.ImageButton) bindings[2]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.optionImageButtonFill.setTag(null);
        this.optionImageButtonSpecific.setTag(null);
        setRootTag(root);
        // listeners
        mCallback3 = new com.example.lateritia.generated.callback.OnClickListener(this, 1);
        mCallback4 = new com.example.lateritia.generated.callback.OnClickListener(this, 2);
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
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.operationsViewModel);
        super.requestRebind();
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
        com.example.lateritia.fuelops.FuelOperationsViewModel operationsViewModel = mOperationsViewModel;
        // batch finished
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            this.optionImageButtonFill.setOnClickListener(mCallback3);
            this.optionImageButtonSpecific.setOnClickListener(mCallback4);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // operationsViewModel
                com.example.lateritia.fuelops.FuelOperationsViewModel operationsViewModel = mOperationsViewModel;
                // operationsViewModel != null
                boolean operationsViewModelJavaLangObjectNull = false;



                operationsViewModelJavaLangObjectNull = (operationsViewModel) != (null);
                if (operationsViewModelJavaLangObjectNull) {


                    operationsViewModel.onTopUpOptionClicked();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // operationsViewModel
                com.example.lateritia.fuelops.FuelOperationsViewModel operationsViewModel = mOperationsViewModel;
                // operationsViewModel != null
                boolean operationsViewModelJavaLangObjectNull = false;



                operationsViewModelJavaLangObjectNull = (operationsViewModel) != (null);
                if (operationsViewModelJavaLangObjectNull) {


                    operationsViewModel.onFillOptionClicked();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): operationsViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}