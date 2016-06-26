package ru.teaz.examplerssviewer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.teaz.examplerssviewer.di.HasComponent;

/**
 * Created by Teaz on 25.06.2016.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

}
