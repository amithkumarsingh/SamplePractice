package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.repository.AdSlotsRepository;
import com.vam.whitecoats.utils.GetAdSlotsApiResponse;

import java.util.Map;

public class GetAdSlotsViewModel extends ViewModel {

    private AdSlotsRepository repository = new AdSlotsRepository();
    public void setRequestData(int userId, int channelId, int feedId, Map<String, String> headers) {
        repository.initRequest(userId,channelId,feedId,headers);
    }

    public LiveData<GetAdSlotsApiResponse> getFeedAdSlots() {
        return repository.getFeedAdSlots();
    }
}
