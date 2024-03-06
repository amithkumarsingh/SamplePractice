package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;

import com.vam.whitecoats.core.models.WebViewUrlModel;

public class WebViewViewModel extends ViewModel {
    private WebViewUrlModel webViewModel;
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();


    public WebViewViewModel() {
    }

    public String webViewUrl() {
        return webViewModel.getUrl();
    }

    public void setWebViewUrlModel(WebViewUrlModel webViewUrlModel) {
        this.webViewModel = webViewUrlModel;
    }

    public void setIsLoaderVisible(boolean visible){
        isLoaderVisible.set(visible);
    }

    public void displayLoader(boolean isDisableLoader){
        setIsLoaderVisible(isDisableLoader);
    }

}
