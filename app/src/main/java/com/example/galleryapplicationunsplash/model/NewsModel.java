package com.example.galleryapplicationunsplash.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.galleryapplicationunsplash.repo.Repository;

import java.util.List;


public class NewsModel extends ViewModel {

    private LiveData<List<ImageSource>> mutableLiveData;
    private Repository repository = null;
    private LiveData<ErrorResponse> isError;
    private MediatorLiveData<ErrorResponse> mediatorLiveData = new MediatorLiveData<>();

    public void init(int page, int perpage){
        repository = Repository.getRepository();
        loadData(page, perpage);
    }

    public LiveData<List<ImageSource>> getData(){
        return mutableLiveData;
    }

    public void loadData(int page, int perpage){
        repository.getData(page, perpage);
        mutableLiveData = repository.getLiveData();
        isError = repository.getError();
    }

    public LiveData<ErrorResponse> getError(){
        mediatorLiveData.addSource(isError, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                if(errorResponse.getStatus() == ErrorResponse.STATUS.RESPONSE_FAIL.ordinal()){
                    errorResponse.setMessage("Response from server finished!!!");
                }else if(errorResponse.getStatus() == ErrorResponse.STATUS.FAILURE.ordinal()){
                    errorResponse.setMessage("Internet connection not stable!!!");
                }else{
                    errorResponse.setMessage("Please retry again!!!!");
                }
                mediatorLiveData.setValue(errorResponse);
            }
        });
        return mediatorLiveData;
    }
}
