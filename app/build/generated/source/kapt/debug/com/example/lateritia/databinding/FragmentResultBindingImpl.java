package com.example.lateritia.databinding;
import com.example.lateritia.R;
import com.example.lateritia.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentResultBindingImpl extends FragmentResultBinding implements com.example.lateritia.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.result_text, 4);
        sViewsWithIds.put(R.id.result_text_layout, 5);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentResultBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private FragmentResultBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[5]
            , (com.google.android.material.textfield.TextInputEditText) bindings[1]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.resultButton.setTag(null);
        this.resultTextInfo.setTag(null);
        this.resultTextMessage.setTag(null);
        setRootTag(root);
        // listeners
        mCallback7 = new com.example.lateritia.generated.callback.OnClickListener(this, 1);
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
        int operationsViewModelCalculatedFillPercentage = 0;
        double operationsViewModelCalculatedFuelCost = 0.0;
        com.example.lateritia.fuelops.FuelOperationsViewModel operationsViewModel = mOperationsViewModel;
        java.lang.String stringFormatJavaLangString2fOperationsViewModelCalculatedFuelCost = null;
        int operationsViewModelCalculatedBars = 0;
        java.lang.String resultTextInfoAndroidStringFuelInfoOperationsViewModelCalculatedFillPercentageOperationsViewModelCalculatedBars = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (operationsViewModel != null) {
                    // read operationsViewModel.calculatedFillPercentage
                    operationsViewModelCalculatedFillPercentage = operationsViewModel.getCalculatedFillPercentage();
                    // read operationsViewModel.calculatedFuelCost
                    operationsViewModelCalculatedFuelCost = operationsViewModel.getCalculatedFuelCost();
                    // read operationsViewModel.calculatedBars
                    operationsViewModelCalculatedBars = operationsViewModel.getCalculatedBars();
                }


                // read String.format("%.2f", operationsViewModel.calculatedFuelCost)
                stringFormatJavaLangString2fOperationsViewModelCalculatedFuelCost = java.lang.String.format("%.2f", operationsViewModelCalculatedFuelCost);
                // read @android:string/fuel_info
                resultTextInfoAndroidStringFuelInfoOperationsViewModelCalculatedFillPercentageOperationsViewModelCalculatedBars = resultTextInfo.getResources().getString(R.string.fuel_info, operationsViewModelCalculatedFillPercentage, operationsViewModelCalculatedBars);
        }
        // batch finished
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            this.resultButton.setOnClickListener(mCallback7);
        }
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.resultTextInfo, resultTextInfoAndroidStringFuelInfoOperationsViewModelCalculatedFillPercentageOperationsViewModelCalculatedBars);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.resultTextMessage, stringFormatJavaLangString2fOperationsViewModelCalculatedFuelCost);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // operationsViewModel
        com.example.lateritia.fuelops.FuelOperationsViewModel operationsViewModel = mOperationsViewModel;
        // operationsViewModel != null
        boolean operationsViewModelJavaLangObjectNull = false;



        operationsViewModelJavaLangObjectNull = (operationsViewModel) != (null);
        if (operationsViewModelJavaLangObjectNull) {


            operationsViewModel.navigateToHome();
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