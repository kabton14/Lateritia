package com.example.lateritia.databinding;
import com.example.lateritia.R;
import com.example.lateritia.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSettingsBindingImpl extends FragmentSettingsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.settings_text, 8);
        sViewsWithIds.put(R.id.make_input_layout, 9);
        sViewsWithIds.put(R.id.model_input_layout, 10);
        sViewsWithIds.put(R.id.vin_input_layout, 11);
        sViewsWithIds.put(R.id.registration_input_layout, 12);
        sViewsWithIds.put(R.id.divisions_input_layout, 13);
        sViewsWithIds.put(R.id.fuel_capacity_input_layout, 14);
        sViewsWithIds.put(R.id.fuel_reserve_input_layout, 15);
        sViewsWithIds.put(R.id.button_settings_save, 16);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSettingsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentSettingsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.Button) bindings[16]
            , (com.google.android.material.textfield.TextInputLayout) bindings[13]
            , (com.google.android.material.textfield.TextInputEditText) bindings[5]
            , (com.google.android.material.textfield.TextInputLayout) bindings[14]
            , (com.google.android.material.textfield.TextInputEditText) bindings[6]
            , (com.google.android.material.textfield.TextInputLayout) bindings[15]
            , (com.google.android.material.textfield.TextInputEditText) bindings[7]
            , (com.google.android.material.textfield.TextInputLayout) bindings[9]
            , (com.google.android.material.textfield.TextInputEditText) bindings[1]
            , (com.google.android.material.textfield.TextInputLayout) bindings[10]
            , (com.google.android.material.textfield.TextInputEditText) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[12]
            , (com.google.android.material.textfield.TextInputEditText) bindings[4]
            , (android.widget.TextView) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[11]
            , (com.google.android.material.textfield.TextInputEditText) bindings[3]
            );
        this.divisionsInputText.setTag(null);
        this.fuelCapacityInputText.setTag(null);
        this.fuelReserveInputText.setTag(null);
        this.makeInputText.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.modelInputText.setTag(null);
        this.registrationInputText.setTag(null);
        this.vinInputText.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.settingsViewModel == variableId) {
            setSettingsViewModel((com.example.lateritia.settings.SettingsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSettingsViewModel(@Nullable com.example.lateritia.settings.SettingsViewModel SettingsViewModel) {
        this.mSettingsViewModel = SettingsViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.settingsViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeSettingsViewModelVehicle((androidx.lifecycle.LiveData<com.example.lateritia.database.Vehicle>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeSettingsViewModelVehicle(androidx.lifecycle.LiveData<com.example.lateritia.database.Vehicle> SettingsViewModelVehicle, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        double settingsViewModelVehicleReserveCapacity = 0.0;
        com.example.lateritia.settings.SettingsViewModel settingsViewModel = mSettingsViewModel;
        java.lang.String settingsViewModelVehicleLicenceToString = null;
        java.lang.String doubleToStringSettingsViewModelVehicleReserveCapacity = null;
        java.lang.String settingsViewModelVehicleVin = null;
        com.example.lateritia.database.Vehicle settingsViewModelVehicleGetValue = null;
        int settingsViewModelVehicleDivisions = 0;
        java.lang.String settingsViewModelVehicleModel = null;
        androidx.lifecycle.LiveData<com.example.lateritia.database.Vehicle> settingsViewModelVehicle = null;
        java.lang.String settingsViewModelVehicleModelToString = null;
        java.lang.String settingsViewModelVehicleLicence = null;
        java.lang.String settingsViewModelVehicleMake = null;
        java.lang.String doubleToStringSettingsViewModelVehicleFuelCapacity = null;
        java.lang.String settingsViewModelVehicleMakeToString = null;
        java.lang.String integerToStringSettingsViewModelVehicleDivisions = null;
        double settingsViewModelVehicleFuelCapacity = 0.0;

        if ((dirtyFlags & 0x7L) != 0) {



                if (settingsViewModel != null) {
                    // read settingsViewModel.vehicle
                    settingsViewModelVehicle = settingsViewModel.getVehicle();
                }
                updateLiveDataRegistration(0, settingsViewModelVehicle);


                if (settingsViewModelVehicle != null) {
                    // read settingsViewModel.vehicle.getValue()
                    settingsViewModelVehicleGetValue = settingsViewModelVehicle.getValue();
                }


                if (settingsViewModelVehicleGetValue != null) {
                    // read settingsViewModel.vehicle.getValue().reserveCapacity
                    settingsViewModelVehicleReserveCapacity = settingsViewModelVehicleGetValue.getReserveCapacity();
                    // read settingsViewModel.vehicle.getValue().vin
                    settingsViewModelVehicleVin = settingsViewModelVehicleGetValue.getVin();
                    // read settingsViewModel.vehicle.getValue().divisions
                    settingsViewModelVehicleDivisions = settingsViewModelVehicleGetValue.getDivisions();
                    // read settingsViewModel.vehicle.getValue().model
                    settingsViewModelVehicleModel = settingsViewModelVehicleGetValue.getModel();
                    // read settingsViewModel.vehicle.getValue().licence
                    settingsViewModelVehicleLicence = settingsViewModelVehicleGetValue.getLicence();
                    // read settingsViewModel.vehicle.getValue().make
                    settingsViewModelVehicleMake = settingsViewModelVehicleGetValue.getMake();
                    // read settingsViewModel.vehicle.getValue().fuelCapacity
                    settingsViewModelVehicleFuelCapacity = settingsViewModelVehicleGetValue.getFuelCapacity();
                }


                // read Double.toString(settingsViewModel.vehicle.getValue().reserveCapacity)
                doubleToStringSettingsViewModelVehicleReserveCapacity = java.lang.Double.toString(settingsViewModelVehicleReserveCapacity);
                // read Integer.toString(settingsViewModel.vehicle.getValue().divisions)
                integerToStringSettingsViewModelVehicleDivisions = java.lang.Integer.toString(settingsViewModelVehicleDivisions);
                // read Double.toString(settingsViewModel.vehicle.getValue().fuelCapacity)
                doubleToStringSettingsViewModelVehicleFuelCapacity = java.lang.Double.toString(settingsViewModelVehicleFuelCapacity);
                if (settingsViewModelVehicleModel != null) {
                    // read settingsViewModel.vehicle.getValue().model.toString()
                    settingsViewModelVehicleModelToString = settingsViewModelVehicleModel.toString();
                }
                if (settingsViewModelVehicleLicence != null) {
                    // read settingsViewModel.vehicle.getValue().licence.toString()
                    settingsViewModelVehicleLicenceToString = settingsViewModelVehicleLicence.toString();
                }
                if (settingsViewModelVehicleMake != null) {
                    // read settingsViewModel.vehicle.getValue().make.toString()
                    settingsViewModelVehicleMakeToString = settingsViewModelVehicleMake.toString();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.divisionsInputText, integerToStringSettingsViewModelVehicleDivisions);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.fuelCapacityInputText, doubleToStringSettingsViewModelVehicleFuelCapacity);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.fuelReserveInputText, doubleToStringSettingsViewModelVehicleReserveCapacity);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.makeInputText, settingsViewModelVehicleMakeToString);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.modelInputText, settingsViewModelVehicleModelToString);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.registrationInputText, settingsViewModelVehicleLicenceToString);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.vinInputText, settingsViewModelVehicleVin);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): settingsViewModel.vehicle
        flag 1 (0x2L): settingsViewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}